package mrriegel.storagenetwork.render;
import mrriegel.storagenetwork.StorageNetwork;
import mrriegel.storagenetwork.cable.BlockCable;
import mrriegel.storagenetwork.cable.TileCable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class CableRenderer extends TileEntitySpecialRenderer<TileCable> {
  ModelCable model;
  private final ResourceLocation link = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/link.png");
  private final ResourceLocation ex = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/ex.png");
  private final ResourceLocation im = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/im.png");
  private final ResourceLocation storage = new ResourceLocation(StorageNetwork.MODID + ":textures/tile/storage.png");
  public CableRenderer() {
    model = new ModelCable();
  }
  //renderTileEntityAt
  @Override
  public void render(TileCable te, double x, double y, double z, float partialTicks, int destroyStage
      , float partial) {
    // if(true)return;
    // boolean show = Minecraft.getMinecraft().player.inventory.getCurrentItem() != null && Block.getBlockFromItem(Minecraft.getMinecraft().player.inventory.getCurrentItem().getItem()) instanceof BlockKabel;
    if (te == null || te.getKind() == null || !(te.getWorld().getBlockState(te.getPos()).getBlock() instanceof BlockCable)) { return; }
    
    GlStateManager.pushMatrix();
    GlStateManager.enableRescaleNormal();
    GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
    switch (te.getKind()) {
      case kabel:
        Minecraft.getMinecraft().renderEngine.bindTexture(link);
      break;
      case exKabel:
        Minecraft.getMinecraft().renderEngine.bindTexture(ex);
      break;
      case imKabel:
        Minecraft.getMinecraft().renderEngine.bindTexture(im);
      break;
      case storageKabel:
        Minecraft.getMinecraft().renderEngine.bindTexture(storage);
      break;
      default:
      break;
    }
    GlStateManager.pushMatrix();
    GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.pushAttrib();
    RenderHelper.disableStandardItemLighting();
    model.render(te);
    RenderHelper.enableStandardItemLighting();
    GlStateManager.popAttrib();
    GlStateManager.popMatrix();
    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
  }
}
