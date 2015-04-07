package dmillerw.event.cinematic.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.event.cinematic.data.Cinematic;
import dmillerw.event.cinematic.client.entity.EntityCamera;

/**
 * @author dmillerw
 */
public class ClientTickHandler {

    public static int totalTickTime = 0;
    public static Cinematic.Builder currentBuildingCinematic;

    private static Cinematic currentCinematic;

    public static void startCinematic(Cinematic cinematic) {
        currentCinematic = cinematic;
        currentCinematic.reset();
        EntityCamera.createCamera();
    }

    public static void stopCinematic() {
        currentCinematic = null;
        EntityCamera.destroyCamera();
    }

    public static boolean isCinematicPlaying() {
        return currentCinematic != null;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        totalTickTime++;

        if (currentCinematic != null) {
            currentCinematic.tick();
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        if (currentCinematic != null) {
            currentCinematic.tick(event.renderTickTime);
        }
    }
}
