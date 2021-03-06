package mrriegel.storagenetwork.request;
import java.util.List;
import javax.annotation.Nullable;
import mrriegel.storagenetwork.AbstractBlockConnectable;
import mrriegel.storagenetwork.CreativeTab;
import mrriegel.storagenetwork.GuiHandler;
import mrriegel.storagenetwork.IConnectable;
import mrriegel.storagenetwork.StorageNetwork;
import mrriegel.storagenetwork.helper.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRequest extends AbstractBlockConnectable {
  public static final PropertyDirection FACING = PropertyDirection.create("facing");
  public BlockRequest() {
    super(Material.IRON);
    this.setHardness(3.0F);
    this.setCreativeTab(CreativeTab.tab);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    this.setRegistryName("request");
    this.setUnlocalizedName(getRegistryName().toString());
  }
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileRequest();
  }
  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }
  @Override
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing enumfacing = EnumFacing.getFront(meta);
    if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH;
    }
    return this.getDefaultState().withProperty(FACING, enumfacing);
  }
  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getIndex();
  }
  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, new IProperty[] { FACING });
  }
  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    IBlockState s = this.getDefaultState().withProperty(FACING, facing.getOpposite());
    return s;
  }
  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    //	   ItemStack heldItem = playerIn.getHeldItem(hand);
    if (!(worldIn.getTileEntity(pos) instanceof IConnectable))
      return false;
    IConnectable tile = (IConnectable) worldIn.getTileEntity(pos);
    if (!worldIn.isRemote && tile.getMaster() != null) {
      playerIn.openGui(StorageNetwork.instance, GuiHandler.REQUEST, worldIn, pos.getX(), pos.getY(), pos.getZ());
      return true;
    }
    return true;
  }
  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileEntity tileentity = worldIn.getTileEntity(pos);
    if (tileentity instanceof TileRequest) {
      TileRequest tile = (TileRequest) tileentity;
      for (int i = 0; i < 9; i++) {
        Util.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.matrix.get(i));
      }
    }
    super.breakBlock(worldIn, pos, state);
  }
  public static class Item extends ItemBlock {
    public Item(Block block) {
      super(block);
    }
    @Override
    public void addInformation(ItemStack stack,  @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
      super.addInformation(stack, playerIn, tooltip, advanced);
      tooltip.add(I18n.format("tooltip.storagenetwork.request"));
    }
  }
}
