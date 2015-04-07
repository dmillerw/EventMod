package dmillerw.event.cinematic.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

/**
 * @author dmillerw
 */
public class ClientEventHandler {

    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent event) {
        if (ClientTickHandler.isCinematicPlaying()) {
            if (event.type != RenderGameOverlayEvent.ElementType.CHAT) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        if (ClientTickHandler.isCinematicPlaying())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent event) {
        if (ClientTickHandler.isCinematicPlaying()) {
//            if (event.entityPlayer.getGameProfile().getId() == Minecraft.getMinecraft().thePlayer.getGameProfile().getId())
                event.setCanceled(true);
        }
    }
}
