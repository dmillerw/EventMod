package dmillerw.event;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.event.data.lore.LoreRegistry;

import java.io.File;

/**
 * @author dmillerw
 */
@Mod(modid = "EventMod", name = "EventMod", version = "%MOD_VERSION%")
public class EventMod {

    public static File rootFolder;
    public static File textFolder;
    public static File audioFolder;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        rootFolder = new File(event.getModConfigurationDirectory(), "EventMod");
        textFolder = new File(rootFolder, "text");
        audioFolder = new File(rootFolder, "audio");

        LoreRegistry.loadFile(null);
    }
}
