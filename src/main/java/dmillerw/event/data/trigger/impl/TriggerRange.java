package dmillerw.event.data.trigger.impl;

import dmillerw.event.data.trigger.PassiveTrigger;

/**
 * @author dmillerw
 */
public class TriggerRange extends PassiveTrigger {

    protected TriggerRange() {
        super("RANGE", Type.PLAYER);
    }

    @Override
    public void update(Object... data) {

    }
}
