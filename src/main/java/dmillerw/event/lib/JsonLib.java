package dmillerw.event.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dmillerw.event.cinematic.Cinematic;
import dmillerw.event.lore.data.trigger.Trigger;

/**
 * @author dmillerw
 */
public class JsonLib {

    private static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();

            // Deserializer
            gsonBuilder.registerTypeAdapter(Trigger.class, new Trigger.Deserializer());
            // Serializer

            gsonBuilder.setPrettyPrinting();

            gson = gsonBuilder.create();
        }
        return gson;
    }
}
