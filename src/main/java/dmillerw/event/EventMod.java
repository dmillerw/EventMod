package dmillerw.event;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.event.common.network.PacketHandler;
import dmillerw.event.common.proxy.CommonProxy;

import java.io.File;

/**
 * @author dmillerw
 */
@Mod(modid = EventMod.ID, name = EventMod.NAME, version = EventMod.VERSION)
public class EventMod {

    public static final String ID = "EventMod";
    public static final String NAME = "EventMod";
    public static final String VERSION = "%MOD_VERSION%";

    @Mod.Instance(EventMod.ID)
    public static EventMod instance;

    @SidedProxy(serverSide = "dmillerw.event.common.proxy.CommonProxy", clientSide = "dmillerw.event.common.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static File rootFolder;

    // Lore
    public static File loreFolder;
    public static File textFolder;
    public static File audioFolder;

    // Cinematic
    public static File cinematicFolder;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        rootFolder = newDir(new File(event.getModConfigurationDirectory(), "EventMod"));
        loreFolder = newDir(new File(rootFolder, "lore"));
        textFolder = newDir(new File(loreFolder, "text"));
        audioFolder = newDir(new File(loreFolder, "audio"));
        cinematicFolder = newDir(new File(rootFolder, "cinematic"));

        PacketHandler.initialize();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    private File newDir(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}
