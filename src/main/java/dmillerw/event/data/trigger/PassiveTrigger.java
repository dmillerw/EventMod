package dmillerw.event.data.trigger;

/**
 * Passive triggers are simply called upon once per tick, depending on their type
 * @author dmillerw
 */
public abstract class PassiveTrigger extends Trigger {

    public final Type type;

    protected PassiveTrigger(Type type) {
        this.type = type;
    }

    public abstract void update(Object ... data);

    public static enum Type {
        PLAYER,
        WORLD
    }
}
