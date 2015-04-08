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

    private static Cinematic currentlyPlaying;
    private static Cinematic currentlyDisplayed;

    public static void startCinematic(Cinematic cinematic) {
        currentlyPlaying = cinematic;
        currentlyPlaying.reset();
        EntityCamera.createCamera();
    }

    public static void stopCinematic() {
        currentlyPlaying = null;
        EntityCamera.destroyCamera();
    }

    public static void displayCinematic(Cinematic cinematic) {
        currentlyDisplayed = cinematic;
    }

    public static void clearDisplayedCinematic() {
        currentlyDisplayed = null;
    }

    public static Cinematic getDisplayedCinematic() {
        return currentlyDisplayed;
    }

    public static Cinematic getPlayingCinematic() {
        return currentlyPlaying;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        totalTickTime++;

        if (currentlyPlaying != null) {
            currentlyPlaying.tick();
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        if (currentlyPlaying != null) {
            currentlyPlaying.tick(event.renderTickTime);
        }
    }
}
