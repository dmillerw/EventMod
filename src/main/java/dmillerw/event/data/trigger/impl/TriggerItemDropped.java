package dmillerw.event.data.trigger.impl;

import com.google.gson.JsonObject;
import dmillerw.event.data.trigger.EventTrigger;
import net.minecraftforge.event.entity.item.ItemTossEvent;

/**
 * @author dmillerw
 */
public class TriggerItemDropped extends EventTrigger<ItemTossEvent> {

    public TriggerItemDropped() {
        super(ItemTossEvent.class);
    }

    @Override
    public void onEvent(ItemTossEvent event) {

    }

    @Override
    public void acceptData(JsonObject object) {

    }
}
