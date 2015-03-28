package dmillerw.event.data.trigger;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.eventhandler.Event;
import dmillerw.event.data.trigger.impl.TriggerRange;

import java.util.Map;
import java.util.Set;

/**
 * @author dmillerw
 */
public class TriggerRegistry {

    private static HashBiMap<String, Class<? extends Trigger>> triggerClassMap = HashBiMap.create();

    static {
        triggerClassMap.put("RANGE", TriggerRange.class);
    }

    public static Trigger newInstance(String type) {
        try {
            return triggerClassMap.get(type).newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Map<String, Set<Trigger>> registeredTriggers = Maps.newHashMap();

    private static Set<Trigger> getSet(String name) {
        Set<Trigger> set = registeredTriggers.get(name);
        if (set == null) {
            set = Sets.newHashSet();
        }
        return set;
    }

    public static void registerTrigger(Trigger trigger) {
        final String tag = triggerClassMap.inverse().get(trigger.getClass());
        Set<Trigger> set = getSet(tag);
        set.add(trigger);
        registeredTriggers.put(tag, set);
    }

    public static void fireTrigger(String name, PassiveTrigger.Type type, Object ... data) {
        for (Set<Trigger> set : registeredTriggers.values()) {
            for (Trigger trigger : set) {
                if (trigger instanceof PassiveTrigger && ((PassiveTrigger) trigger).type == type) {
                    ((PassiveTrigger) trigger).update(data);
                }
            }
        }
    }

    public static void fireTrigger(String name, Event event) {
        for (Set<Trigger> set : registeredTriggers.values()) {
            for (Trigger trigger : set) {
                if (trigger instanceof EventTrigger<?> && ((EventTrigger) trigger).clazz.isAssignableFrom(event.getClass())) {
                    ((EventTrigger) trigger).onEvent(event);
                }
            }
        }
    }
}
