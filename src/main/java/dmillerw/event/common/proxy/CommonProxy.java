package dmillerw.event.common.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.event.EventMod;
import dmillerw.event.cinematic.client.CinematicLoader;
import dmillerw.event.common.ExtensionFilter;
import dmillerw.event.lore.data.lore.LoreRegistry;
import dmillerw.event.lore.handler.EventHandler;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        for (File file : EventMod.loreFolder.listFiles(ExtensionFilter.JSON)) {
            LoreRegistry.loadFile(file);
        }

        for (File file : EventMod.cinematicFolder.listFiles(ExtensionFilter.JSON)) {
            CinematicLoader.loadFile(file);
        }

        MinecraftForge.EVENT_BUS.register(EventHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(EventHandler.INSTANCE);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
