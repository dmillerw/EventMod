package dmillerw.event.data.trigger;

import com.google.gson.JsonObject;
import dmillerw.event.data.lore.Lore;
import dmillerw.event.data.lore.LoreRegistry;

/**
 * @author dmillerw
 */
public abstract class Trigger {

    private String loreIdent; // Set upon creation

    public final Lore getLore() {
        return LoreRegistry.getLore(loreIdent);
    }

    public abstract void acceptData(JsonObject object);
}