package dmillerw.event.common.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.event.cinematic.client.command.CommandCinematic;
import dmillerw.event.cinematic.client.handler.ClientEventHandler;
import dmillerw.event.cinematic.client.handler.ClientTickHandler;
import dmillerw.event.lore.client.SoundHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

        ClientCommandHandler.instance.registerCommand(new CommandCinematic());

        MinecraftForge.EVENT_BUS.register(SoundHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(SoundHandler.INSTANCE);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
