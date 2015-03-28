package dmillerw.event.data.trigger.impl;

import dmillerw.event.data.trigger.EventTrigger;
import net.minecraftforge.event.entity.item.ItemTossEvent;

/**
 * @author dmillerw
 */
public class TriggerItemDropped extends EventTrigger<ItemTossEvent> {

    public TriggerItemDropped() {
        super("ITEM_DROP", ItemTossEvent.class);
    }

    @Override
    public void onEvent(ItemTossEvent event) {

    }
}
