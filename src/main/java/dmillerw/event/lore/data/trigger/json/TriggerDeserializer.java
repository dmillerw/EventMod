package dmillerw.event.lore.data.trigger.json;

import com.google.gson.*;
import dmillerw.event.lore.data.trigger.Trigger;
import dmillerw.event.lore.data.trigger.TriggerRegistry;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class TriggerDeserializer implements JsonDeserializer<Trigger> {

    @Override
    public Trigger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Trigger trigger = TriggerRegistry.newInstance(object.get("type").getAsString());

        if (trigger == null) {
            // Error
        } else {
            trigger.acceptData(object);
        }

        return trigger;
    }
}
