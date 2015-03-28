package dmillerw.event.data.lore;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dmillerw.event.data.trigger.Trigger;
import dmillerw.event.data.trigger.TriggerRegistry;
import dmillerw.event.data.trigger.json.TriggerDeserializer;

import java.io.File;
import java.util.Map;

/**
 * @author dmillerw
 */
public class LoreRegistry {

    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Trigger.class, new TriggerDeserializer());
        gson = gsonBuilder.create();
    }

    private static Map<String, Lore> loreMap = Maps.newHashMap();

    public static Lore getLore(String ident) {
        return loreMap.get(ident);
    }

    public static void loadFile(File file) {
//        try {
            Lore lore = gson.fromJson("" +
                    "{\n" +
                    "        \"title\": \"Title\",\n" +
                    "        \"lore\": \"lore1.txt\",\n" +
                    "        \"sound\": \"sound1.txt\",\n" +
                    "        \n" +
                    "        \"trigger\": {\n" +
                    "                \"type\": \"RANGE\",\n" +
                    "                \"coordinate\": [160, 5, -1689, 165, 10, -1679],\n" +
                    "                \"range\": 5\n" +
                    "        }\n" +
                    "}", Lore.class);
//            Lore lore = gson.fromJson(new FileReader(file), Lore.class);

            Trigger trigger = lore.trigger;
            trigger.loreIdent = lore.getIdent();
            TriggerRegistry.registerTrigger(trigger);

            lore.trigger = null;

            loreMap.put(lore.getIdent(), lore);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }
}
