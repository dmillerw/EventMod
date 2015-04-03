package dmillerw.event.cinematic;

import com.google.common.collect.Lists;
import dmillerw.event.cinematic.client.ClientTickHandler;
import dmillerw.event.cinematic.client.EntityCamera;
import dmillerw.event.lib.MathFX;

import java.util.LinkedList;

/**
 * @author dmillerw
 */
public class Cinematic {

    private LinkedList<Point> points;
    private int speed; // Ticks between points. likely a high value

    private boolean loop = false;

    private int currentTickTime = 0;
    private int currentPointIndex;

    private Point currentPoint;
    private Point nextPoint;

    public Cinematic(Point[] points, int speed) {
        LinkedList<Point> list = Lists.newLinkedList();
        for (Point p : points) {
            list.add(p);
        }

        if (list == null || list.size() < 2) {
            throw new IllegalArgumentException("Cinematic must two or more points!");
        }

        this.points = list;
        this.speed = speed;

        reset();
    }

    public Cinematic(LinkedList<Point> points, int speed) {

    }

    public Cinematic loop() {
        this.loop = true;
        return this;
    }

    public void reset() {
        currentTickTime = 0;
        currentPointIndex = 0;
        currentPoint = points.get(0);
        nextPoint = points.get(1);
    }

    public void tick() {
        currentTickTime++;

        if (currentTickTime >= speed) {
            currentPointIndex++;
            currentTickTime = 0;

            if (currentPointIndex >= points.size() - 1) {
                if (loop) {
                    currentPointIndex = -1;
                    currentPoint = nextPoint;
                    nextPoint = points.get(0);
                } else {
                    ClientTickHandler.stopCinematic();
                    return;
                }
            } else {
                currentPoint = points.get(currentPointIndex);
                nextPoint = points.get(currentPointIndex + 1);
            }
        }

        // Consider moving this to a method with an EntityCamera param
        final float lerp = MathFX.twoIntToFloat(currentTickTime, speed);

        float lerpX = MathFX.lerp(currentPoint.x, nextPoint.x, lerp);
        float lerpY = MathFX.lerp(currentPoint.y, nextPoint.y, lerp);
        float lerpZ = MathFX.lerp(currentPoint.z, nextPoint.z, lerp);

        float lerpPitch = MathFX.lerp(currentPoint.pitch, nextPoint.pitch, lerp);
        float lerpYaw = MathFX.lerp(currentPoint.yaw, nextPoint.yaw, lerp);

        EntityCamera.moveCamera(lerpX, lerpY, lerpZ, lerpPitch, lerpYaw);
    }
}
