package dmillerw.event.data.trigger;

/**
 * @author dmillerw
 */
public abstract class EventTrigger<E> extends Trigger {

    public final Class<E> clazz;

    public EventTrigger(String tag, Class<E> clazz) {
        super(tag);
        this.clazz = clazz;
    }

    public abstract void onEvent(E event);
}