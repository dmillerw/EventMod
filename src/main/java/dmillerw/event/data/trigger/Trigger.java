package dmillerw.event.data.trigger;

import com.google.gson.JsonObject;
import dmillerw.event.data.lore.Lore;
import dmillerw.event.data.lore.LoreRegistry;
import dmillerw.event.data.lore.LoreStatusTracker;
import dmillerw.event.network.PacketHandler;
import dmillerw.event.network.packet.S01PlayLoreAudio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author dmillerw
 */
public abstract class Trigger {

    private String loreIdent; // Set upon creation

    public final void setLoreIdent(String loreIdent) {
        this.loreIdent = loreIdent;
    }

    public final void startPlayingLore(EntityPlayer entityPlayer) {
        if (LoreStatusTracker.getStatusTracker(entityPlayer).hasBeenRead(loreIdent))
            return;

        Lore lore = LoreRegistry.getLore(loreIdent);

        if (!lore.repeat) {
            LoreStatusTracker.getStatusTracker(entityPlayer).markAsRead(loreIdent);
        }

        PacketHandler.INSTANCE.sendTo(new S01PlayLoreAudio(loreIdent), (EntityPlayerMP) entityPlayer);
    }

    public final void markAsRead(EntityPlayer entityPlayer) {
        LoreStatusTracker.getStatusTracker(entityPlayer).markAsRead(loreIdent);
    }

    public abstract void acceptData(JsonObject object);
}