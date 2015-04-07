package dmillerw.event.cinematic;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class Point {

    public static Point fromPlayer() {
        final EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;

        //TODO Adjust yaw/pitch values
        return new Point(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, entityPlayer.rotationYaw, entityPlayer.rotationPitch);
    }

    public final double x;
    public final double y;
    public final double z;

    public final float yaw;
    public final float pitch;

    public Point(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + ", z: " + z + ", yaw: " + yaw + ", pitch: " + pitch + "}";
    }

    public static class Serializer implements JsonSerializer<Point> {

        @Override
        public JsonElement serialize(Point src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(new JsonPrimitive(src.x));
            jsonArray.add(new JsonPrimitive(src.y));
            jsonArray.add(new JsonPrimitive(src.z));
            jsonArray.add(new JsonPrimitive(src.yaw));
            jsonArray.add(new JsonPrimitive(src.pitch));
            return jsonArray;
        }
    }

    public static class Deserializer implements JsonDeserializer<Point> {

        @Override
        public Point deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonArray()) {
                return null; // Error out safely
            }

            JsonArray jsonArray = json.getAsJsonArray();
            return new Point(jsonArray.get(0).getAsDouble(), jsonArray.get(1).getAsDouble(), jsonArray.get(2).getAsDouble(), jsonArray.get(3).getAsFloat(), jsonArray.get(4).getAsFloat());
        }
    }
}
