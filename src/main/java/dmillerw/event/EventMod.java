package dmillerw.event;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.event.cinematic.client.CinematicLoader;
import dmillerw.event.cinematic.client.handler.ClientEventHandler;
import dmillerw.event.cinematic.client.handler.ClientTickHandler;
import dmillerw.event.cinematic.client.command.CommandCinematic;
import dmillerw.event.lore.client.SoundHandler;
import dmillerw.event.lore.data.lore.LoreRegistry;
import dmillerw.event.lore.handler.EventHandler;
import dmillerw.event.common.ExtensionFilter;
import dmillerw.event.common.network.PacketHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

/**
 * @author dmillerw
 */
@Mod(modid = EventMod.ID, name = EventMod.NAME, version = EventMod.VERSION)
public class EventMod {

    public static final String ID = "EventMod";
    public static final String NAME = "EventMod";
    public static final String VERSION = "%MOD_VERSION%";

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

        for (File file : loreFolder.listFiles(ExtensionFilter.JSON)) {
            LoreRegistry.loadFile(file);
        }

        for (File file : cinematicFolder.listFiles(ExtensionFilter.JSON)) {
            CinematicLoader.loadFile(file);
        }

        //TODO CLEANUP THIS SHIT
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

        ClientCommandHandler.instance.registerCommand(new CommandCinematic());

        MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(EventHandler.INSTANCE);

        MinecraftForge.EVENT_BUS.register(SoundHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(SoundHandler.INSTANCE);

        PacketHandler.initialize();
    }

    private File newDir(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}
