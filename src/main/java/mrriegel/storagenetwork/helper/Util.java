package mrriegel.storagenetwork.helper;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.text.WordUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import net.minecraftforge.oredict.OreDictionary;

public class Util {
  private static final Map<String, String> modNamesForIds = new HashMap<String, String>();
  public static void init() {
    Map<String, ModContainer> modMap = Loader.instance().getIndexedModList();
    for (Map.Entry<String, ModContainer> modEntry : modMap.entrySet()) {
      String lowercaseId = modEntry.getKey().toLowerCase(Locale.ENGLISH);
      String modName = modEntry.getValue().getName();
      modNamesForIds.put(lowercaseId, modName);
    }
  }
  @Nonnull
  public static String getModNameForItem(@Nonnull Impl object) {
    RegistryNamespaced registry = object instanceof Item ? Item.REGISTRY : object instanceof Block ? Block.REGISTRY : null;
    ResourceLocation itemResourceLocation = (ResourceLocation) registry.getNameForObject(object);
    String modId = itemResourceLocation.getResourceDomain();
    String lowercaseModId = modId.toLowerCase(Locale.ENGLISH);
    String modName = modNamesForIds.get(lowercaseModId);
    if (modName == null) {
      modName = WordUtils.capitalize(modId);
      modNamesForIds.put(lowercaseModId, modName);
    }
    // ModContainer m =
    // Loader.instance().getIndexedModList().get(item.getRegistryName().getResourceDomain());
    // if (m == null)
    // return "Minecraft";
    // else
    // return m.getName();
    return modName;
  }
  public static boolean equalOreDict(ItemStack a, ItemStack b) {
    int[] ar = OreDictionary.getOreIDs(a);
    int[] br = OreDictionary.getOreIDs(b);
    for (int i = 0; i < ar.length; i++)
      for (int j = 0; j < br.length; j++)
        if (ar[i] == br[j])
          return true;
    return false;
  }
  public static <E> boolean contains(List<E> list, E e, Comparator<? super E> c) {
    for (E a : list)
      if (c.compare(a, e) == 0)
        return true;
    return false;
  }
  public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
    if (stack == null || worldIn.isRemote)
      return;
    Random RANDOM = worldIn.rand;
    float f = RANDOM.nextFloat() * 0.8F + 0.1F;
    float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
    float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;
    while (stack.getCount() > 0) {
      int i = RANDOM.nextInt(21) + 10;
      if (i > stack.getCount()) {
        i = stack.getCount();
      }
      stack.shrink(i);
      EntityItem entityitem = new EntityItem(worldIn, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), i, stack.getMetadata()));
      if (stack.hasTagCompound()) {
        entityitem.getItem().setTagCompound(stack.getTagCompound().copy());
      }
      float f3 = 0.05F;
      entityitem.motionX = RANDOM.nextGaussian() * f3;
      entityitem.motionY = RANDOM.nextGaussian() * f3 + 0.20000000298023224D;
      entityitem.motionZ = RANDOM.nextGaussian() * f3;
      worldIn.spawnEntity(entityitem);
    }
  }
  public static List<BlockPos> getSides(BlockPos pos) {
    List<BlockPos> lis = Lists.newArrayList();
    for (EnumFacing face : EnumFacing.values())
      lis.add(pos.offset(face));
    return lis;
  }
  public static void updateTile(World world, BlockPos pos) {
    if (world == null || world.isRemote || world.getTileEntity(pos) == null || !world.getChunkFromBlockCoords(pos).isLoaded())
      return;
    WorldServer w = (WorldServer) world;
    for (EntityPlayer p : w.playerEntities) {
      if (p.getPosition().getDistance(pos.getX(), pos.getY(), pos.getZ()) < 32) {
        try {
          ((EntityPlayerMP) p).connection.sendPacket(world.getTileEntity(pos).getUpdatePacket());
          world.markChunkDirty(pos, world.getTileEntity(pos));
        }
        catch (Error e) {
          System.out.println(e.getMessage());
          e.printStackTrace();
        }
      }
    }
  }
}
