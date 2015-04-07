package dmillerw.event.cinematic.client;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import dmillerw.event.EventMod;
import dmillerw.event.cinematic.Cinematic;
import dmillerw.event.lib.JsonLib;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author dmillerw
 */
public class CinematicLoader {

    private static Map<String, Cinematic> cinematicMap = Maps.newHashMap();

    public static Cinematic get(String name) {
        return cinematicMap.get(name);
    }

    public static void save(Cinematic cinematic) {
        cinematicMap.put(cinematic.name, cinematic);

        Thread thread = new SaveFile(cinematic);
        thread.run();
    }

    public static void loadFile(File file) {
        if (file == null)
            return;

        try {
            Cinematic cinematic = JsonLib.gson().fromJson(new FileReader(file), Cinematic.class);

            //TODO Sanity checks

            save(cinematic);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static class SaveFile extends Thread {

        private final Cinematic cinematic;

        public SaveFile(Cinematic cinematic) {
            this.cinematic = cinematic;
        }

        @Override
        public void run() {
            File file = new File(EventMod.cinematicFolder, cinematic.name + ".json");
            try {
                Files.write(JsonLib.gson().toJson(cinematic), file, Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
