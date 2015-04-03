package dmillerw.event.lore.data.trigger;

/**
 * @author dmillerw
 */
public abstract class EventTrigger<E> extends Trigger {

    public final Class<E> clazz;

    public EventTrigger(Class<E> clazz) {
        this.clazz = clazz;
    }

    public abstract void onEvent(E event);
}