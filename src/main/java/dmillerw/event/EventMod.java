package dmillerw.event;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.event.cinematic.client.ClientTickHandler;
import dmillerw.event.cinematic.command.CommandPlay;
import dmillerw.event.cinematic.command.CommandStop;
import dmillerw.event.lore.client.SoundHandler;
import dmillerw.event.lore.data.lore.LoreRegistry;
import dmillerw.event.event.EventHandler;
import dmillerw.event.lib.ExtensionFilter;
import dmillerw.event.lore.network.PacketHandler;
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
    public static File textFolder;
    public static File audioFolder;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        rootFolder = newDir(new File(event.getModConfigurationDirectory(), "EventMod"));
        textFolder = newDir(new File(rootFolder, "text"));
        audioFolder = newDir(new File(rootFolder, "audio"));

        for (File file : rootFolder.listFiles(ExtensionFilter.JSON)) {
            LoreRegistry.loadFile(file);
        }

        FMLCommonHandler.instance().bus().register(new ClientTickHandler());

        ClientCommandHandler.instance.registerCommand(new CommandPlay());
        ClientCommandHandler.instance.registerCommand(new CommandStop());

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
