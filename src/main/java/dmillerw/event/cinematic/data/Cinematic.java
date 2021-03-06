package dmillerw.event.cinematic.data;

import com.google.common.collect.Lists;
import com.google.gson.*;
import dmillerw.event.cinematic.client.entity.EntityCamera;
import dmillerw.event.cinematic.client.handler.ClientTickHandler;
import dmillerw.event.common.MathFX;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * @author dmillerw
 */
public class Cinematic {

    public String name;

    public LinkedList<Point> points;
    public int speed; // Ticks between points. likely a high value
    public int average; // Ticks between points. likely a high value

    public boolean loop = false;
    public boolean hidePlayer = false;

    private int currentTickTime = 0;
    private int currentPointIndex;

    private Point currentPoint;
    private Point nextPoint;

    public Cinematic(String name, Point[] points, int speed) {
        LinkedList<Point> list = Lists.newLinkedList();
        for (Point p : points) {
            list.add(p);
        }

        if (list.size() < 2) {
            throw new IllegalArgumentException("Cinematic must two or more points!");
        }

        this.name = name;
        this.points = list;
        this.speed = speed;
        this.average = (int)Math.floor((float)speed / (float)list.size());

        reset();
    }

    public Cinematic(String name, LinkedList<Point> points, int speed) {
        if (points == null || points.size() < 2) {
            throw new IllegalArgumentException("Cinematic must two or more points!");
        }

        this.name = name;
        this.points = points;
        this.speed = speed;
        this.average = (int)Math.floor((float)speed / (float)points.size());

        reset();
    }

    public Cinematic loop() {
        this.loop = true;
        return this;
    }

    public Cinematic hidePlayer() {
        this.hidePlayer = true;
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

        if (currentTickTime >= average) {
            currentPointIndex++;
            currentTickTime = 0;

            if (currentPointIndex >= points.size() - 1) {
                if (loop) {
                    currentPointIndex = -1;
                    currentPoint = nextPoint;
                    nextPoint = points.get(0);
                } else {
                    ClientTickHandler.stopCinematic();
                }
            } else {
                currentPoint = points.get(currentPointIndex);
                nextPoint = points.get(currentPointIndex + 1);
            }
        }
    }

    public void tick(float renderTickTime) {
        final EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;

        // Consider moving this to a method with an EntityCamera param
        final float lerp = (float)(currentTickTime + renderTickTime) / (float)average;

        double lerpX = MathFX.lerpD(currentPoint.x, nextPoint.x, lerp);
        double lerpY = MathFX.lerpD(currentPoint.y, nextPoint.y, lerp);
        double lerpZ = MathFX.lerpD(currentPoint.z, nextPoint.z, lerp);

        float lerpPitch = MathFX.lerpF(currentPoint.pitch, nextPoint.pitch, lerp);
        float lerpYaw = MathFX.lerpF(currentPoint.yaw, nextPoint.yaw, lerp);

        EntityCamera.moveCamera(lerpX, lerpY - entityPlayer.height + entityPlayer.getDefaultEyeHeight() * 2, lerpZ, lerpPitch, lerpYaw);
    }

    @Override
    public String toString() {
        return "{points: " + points.toString() + ", speed: " + speed + ", loop: " + loop + "}";
    }

    public static class Serializer implements JsonSerializer<Cinematic> {

        @Override
        public JsonElement serialize(Cinematic src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.add("name", new JsonPrimitive(src.name));
            jsonObject.add("speed", new JsonPrimitive(src.speed));
            jsonObject.add("loop", new JsonPrimitive(src.loop));
            jsonObject.add("hide_player", new JsonPrimitive(src.hidePlayer));

            jsonObject.add("points", context.serialize(src.points));

            return jsonObject;
        }
    }

    public static class Deserializer implements JsonDeserializer<Cinematic> {

        @Override
        public Cinematic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            final String name = jsonObject.get("name").getAsString();
            final int speed = jsonObject.get("speed").getAsInt();
            final boolean loop = jsonObject.get("loop").getAsBoolean();
            final boolean hidePlayer = jsonObject.get("hide_player").getAsBoolean();

            final Point[] points = context.deserialize(jsonObject.get("points"), Point[].class);

            Cinematic cinematic = new Cinematic(name, points, speed);
            if (loop) {
                cinematic.loop();
            }
            if (hidePlayer) {
                cinematic.hidePlayer();
            }

            return cinematic;
        }
    }

    public static class Builder {

        public String name;
        public LinkedList<Point> points;
        public int speed = 0;
        public boolean loop = false;
        public boolean hidePlayer = false;

        public Builder() {
            this.points = Lists.newLinkedList();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder loop() {
            this.loop = true;
            return this;
        }

        public Builder hidePlayer() {
            this.hidePlayer = true;
            return this;
        }

        public Builder addPoint(Point point) {
            this.points.add(point);
            return this;
        }

        public Cinematic build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalStateException("Cinematic name cannot be null!");
            }

            if (speed <= 0) {
                throw new IllegalStateException("Cinematic speed must be greater than zero!");
            }

            if (points.size() < 2) {
                throw new IllegalStateException("Cinematic must have at least two points!");
            }

            Cinematic cinematic = new Cinematic(name, points, speed);
            if (loop) {
                cinematic.loop();
            }
            return cinematic;
        }
    }
}
