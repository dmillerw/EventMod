package dmillerw.event.cinematic;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author dmillerw
 */
public class Point {

    public static Point fromPlayer() {
        final EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
        return new Point(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, entityPlayer.rotationYaw, entityPlayer.rotationPitch);
    }

    public final double x;
    public final double y;
    public final double z;

    public final float pitch;
    public final float yaw;

    public Point(double x, double y, double z, float pitch, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + ", z: " + z + ", pitch: " + pitch + ", yaw: " + yaw + "}";
    }
}
