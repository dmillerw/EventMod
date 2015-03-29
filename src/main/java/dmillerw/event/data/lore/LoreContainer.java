package dmillerw.event.data.lore;

import com.google.gson.annotations.SerializedName;
import dmillerw.event.data.trigger.Trigger;

/**
 * @author dmillerw
 */
public class LoreContainer {

    public String title;

    @SerializedName("lore")
    public String textPath;
    @SerializedName("sound")
    public String audioPath;

    public boolean repeat;

    public Trigger trigger; // Never actually used, just here to link the lore to the trigger

    public String getIdent() {
        return title.toLowerCase().replace(" ", "_");
    }

    @Override
    public int hashCode() {
        return getIdent().hashCode();
    }
}
