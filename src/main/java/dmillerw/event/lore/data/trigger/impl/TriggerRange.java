package dmillerw.event.lore.data.trigger.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dmillerw.event.lore.data.trigger.PassiveTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class TriggerRange extends PassiveTrigger {

    public Vec3 coordinate;
    public float range;

    public TriggerRange() {
        super(Type.PLAYER);
    }

    @Override
    public void acceptData(JsonObject object) {
        JsonArray array = object.getAsJsonArray("coordinate");
        coordinate = Vec3.createVectorHelper(array.get(0).getAsDouble(), array.get(1).getAsDouble(), array.get(2).getAsDouble());
        range = object.get("range").getAsFloat();
    }

    @Override
    public void update(Object data) {
        EntityPlayer player = (EntityPlayer) data;
        double dist = coordinate.distanceTo(player.getPosition(1F));
        if (dist <= range) {
            startPlayingLore(player);
        }
    }
}
