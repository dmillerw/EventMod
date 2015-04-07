package dmillerw.event.lore.handler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import dmillerw.event.lore.data.lore.LoreStatusTracker;
import dmillerw.event.lore.data.trigger.PassiveTrigger;
import dmillerw.event.lore.data.trigger.TriggerRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

/**
 * @author dmillerw
 */
public class EventHandler {

    public static final EventHandler INSTANCE = new EventHandler();

    // Properties handling
    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            if (event.entity instanceof EntityPlayer) {
                LoreStatusTracker.attachStatusTracker((EntityPlayer) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        TriggerRegistry.fireTrigger(event);
    }

    // Tick Events
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        if (event.side == Side.CLIENT)
            return;

        TriggerRegistry.fireTrigger(PassiveTrigger.Type.PLAYER, event.player);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        if (event.side == Side.CLIENT)
            return;

        TriggerRegistry.fireTrigger(PassiveTrigger.Type.WORLD, event.world);
    }
}
