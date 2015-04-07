package dmillerw.event.lore.data.trigger;

import com.google.gson.*;
import dmillerw.event.lore.data.lore.Lore;
import dmillerw.event.lore.data.lore.LoreRegistry;
import dmillerw.event.lore.data.lore.LoreStatusTracker;
import dmillerw.event.lore.network.PacketHandler;
import dmillerw.event.lore.network.packet.S01PlayLoreAudio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public abstract class Trigger {

    public static class Deserializer implements JsonDeserializer<Trigger> {

        @Override
        public Trigger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Trigger trigger = TriggerRegistry.newInstance(object.get("type").getAsString());

            if (trigger == null) {
                // Error
            } else {
                trigger.acceptData(object);
            }

            return trigger;
        }
    }

    private String loreIdent; // Set upon creation

    public final void setLoreIdent(String loreIdent) {
        this.loreIdent = loreIdent;
    }

    public final void startPlayingLore(EntityPlayer entityPlayer) {
        if (hasBeenRead(entityPlayer))
            return;

        Lore lore = LoreRegistry.getLore(loreIdent);

        if (!lore.repeat)
            markAsRead(entityPlayer);

        PacketHandler.INSTANCE.sendTo(new S01PlayLoreAudio(loreIdent), (EntityPlayerMP) entityPlayer);
    }

    public final boolean hasBeenRead(EntityPlayer entityPlayer) {
        return LoreStatusTracker.getStatusTracker(entityPlayer).hasBeenRead(loreIdent);
    }

    public final void markAsRead(EntityPlayer entityPlayer) {
        LoreStatusTracker.getStatusTracker(entityPlayer).markAsRead(loreIdent);
    }

    public abstract void acceptData(JsonObject object);
}