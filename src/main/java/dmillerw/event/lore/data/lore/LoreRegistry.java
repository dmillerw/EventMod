package dmillerw.event.lore.data.lore;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import dmillerw.event.EventMod;
import dmillerw.event.lore.data.trigger.Trigger;
import dmillerw.event.lore.data.trigger.TriggerRegistry;
import dmillerw.event.lore.data.trigger.json.TriggerDeserializer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        if (file == null)
            return;

        try {
            boolean errored = false;
            LoreContainer loreContainer = gson.fromJson(new FileReader(file), LoreContainer.class);

            // Sanity checks
            File text = new File(EventMod.textFolder, loreContainer.textPath);
            if (!text.exists() || !text.isFile()) {
                error(file, "Specified text file couldn't be found (" + loreContainer.textPath + ")");
                errored = true;
            }

            if (FMLCommonHandler.instance().getSide().isClient()) {
                File audio = new File(EventMod.audioFolder, loreContainer.audioPath);
                if (!audio.exists() || !audio.isFile()) {
                    errored = true;
                    error(file, "Specified audio file couldn't be found (" + loreContainer.audioPath + ")");
                }
            }

            if (errored) {
                return;
            }

            Trigger trigger = loreContainer.trigger;
            trigger.setLoreIdent(loreContainer.getIdent());
            TriggerRegistry.registerTrigger(trigger);

            loreMap.put(loreContainer.getIdent(), Lore.fromContainer(loreContainer));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void error(File file, String msg) {
        FMLLog.warning("[EventMod]: Failed to load file '" + file.getName() + "'. " + msg);
    }
}
