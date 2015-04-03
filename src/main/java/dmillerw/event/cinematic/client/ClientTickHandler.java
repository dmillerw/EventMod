package dmillerw.event.cinematic.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.event.cinematic.Cinematic;
import dmillerw.event.cinematic.Point;

/**
 * @author dmillerw
 */
public class ClientTickHandler {

    public static int totalTickTime = 0;

    private static Point pointA = new Point(10, 10, 10, 0, 0);
    private static Point pointB = new Point(10, 10, 20, 0, 30);
    private static Point pointC = new Point(20, 10, 20, 0, 60);
    private static Point pointD = new Point(20, 10, 10, 0, 90);

    public static Cinematic cinematic = new Cinematic(new Point[] {pointA, pointB, pointC, pointD}, 50).loop();

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
