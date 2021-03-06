package mrriegel.storagenetwork.request;
import mrriegel.storagenetwork.RigelNetworkGuiRequest;
import mrriegel.storagenetwork.StorageNetwork;
import mrriegel.storagenetwork.request.TileRequest.EnumSortType;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiRequest extends RigelNetworkGuiRequest {
  TileRequest tile;
  public GuiRequest(Container inventorySlotsIn) {
    super(inventorySlotsIn);
    tile = ((ContainerRequest) inventorySlots).tile;
    texture = new ResourceLocation(StorageNetwork.MODID + ":textures/gui/request.png");
  }
  @Override
  public int getLines() {
    return 4;
  }
  @Override
  public int getColumns() {
    return 9;
  }
  @Override
  public boolean getDownwards() {
    return tile.downwards;
  }
  @Override
  public void setDownwards(boolean d) {
    tile.downwards = d;
  }
  @Override
  public EnumSortType getSort() {
    return tile.sort;
  }
  @Override
  public void setSort(EnumSortType s) {
    tile.sort = s;
  }
  @Override
  public BlockPos getPos() {
    return tile.getPos();
  }
  @Override
  protected int getDim() {
    return tile.getWorld().provider.getDimension();
  }
  @Override
  protected boolean inField(int mouseX, int mouseY) {
    return mouseX > (guiLeft + 7) && mouseX < (guiLeft + xSize - 7) && mouseY > (guiTop + 7) && mouseY < (guiTop + 90);
  }
  @Override
  protected boolean inSearchbar(int mouseX, int mouseY) {
    return isPointInRegion(81, 96, 85, fontRenderer.FONT_HEIGHT, mouseX, mouseY);
  }
  @Override
  protected boolean inX(int mouseX, int mouseY) {
    return isPointInRegion(63, 110, 7, 7, mouseX, mouseY);
  }
}
