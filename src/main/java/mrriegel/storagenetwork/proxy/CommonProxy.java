package mrriegel.storagenetwork.proxy;
import mrriegel.storagenetwork.ConfigHandler;
import mrriegel.storagenetwork.CraftingRecipes;
import mrriegel.storagenetwork.GuiHandler;
import mrriegel.storagenetwork.ModBlocks;
import mrriegel.storagenetwork.ModItems;
import mrriegel.storagenetwork.StorageNetwork;
import mrriegel.storagenetwork.helper.Util;
import mrriegel.storagenetwork.network.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
  public void preInit(FMLPreInitializationEvent event) {
    ConfigHandler.refreshConfig(event.getSuggestedConfigurationFile());
    PacketHandler.init();
  }
  public void init(FMLInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(StorageNetwork.instance, new GuiHandler());
    // MinecraftForge.EVENT_BUS.register(this);
  }
  public void postInit(FMLPostInitializationEvent event) {
    Util.init();
  }
}
