package dmillerw.event.cinematic.client;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.event.cinematic.Cinematic;
import dmillerw.event.cinematic.CinematicBuilder;

import java.util.Map;

/**
 * @author dmillerw
 */
public class ClientTickHandler {

    public static int totalTickTime = 0;
    public static CinematicBuilder currentBuildingCinematic;

    public static Map<String, Cinematic> cinematicMap = Maps.newHashMap();

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
}
