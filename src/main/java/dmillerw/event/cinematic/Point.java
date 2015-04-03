package dmillerw.event.cinematic;

/**
 * @author dmillerw
 */
public class Point {

    public final int x;
    public final int y;
    public final int z;

    public final float pitch;
    public final float yaw;

    public Point(int x, int y, int z, float pitch, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }
}
