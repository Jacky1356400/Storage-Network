package mrriegel.storagenetwork;
import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class ConfigHandler {
  public static Configuration config;
  public static boolean smallFont, jeiLoaded;
  public static final int rangeWirelessAccessor = 64;
  public static long refreshTicks = 200;
  public static void refreshConfig(File file) {
    config = new Configuration(file);
    config.load();
    smallFont = config.get(Configuration.CATEGORY_CLIENT, "smallFont", true).getBoolean();
    refreshTicks = config.get(Configuration.CATEGORY_GENERAL, "AutoRefreshTicks", 200).getInt();
    jeiLoaded = Loader.isModLoaded("jei");
    if (config.hasChanged()) {
      config.save();
    }
  }
}
