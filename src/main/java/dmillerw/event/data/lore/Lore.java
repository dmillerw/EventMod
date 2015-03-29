package dmillerw.event.data.lore;

import dmillerw.event.EventMod;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * @author dmillerw
 */
public class Lore {

    public static Lore fromContainer(LoreContainer loreContainer) {
        File text = new File(EventMod.textFolder, loreContainer.textPath);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (String line : Files.readAllLines(text.toPath(), Charset.defaultCharset())) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Lore("", loreContainer.audioPath, loreContainer.repeat);
        }
        return new Lore(stringBuilder.toString(), loreContainer.audioPath, loreContainer.repeat);
    }

    public final String text;
    public final String audioPath;

    public final boolean repeat;

    public Lore(String text, String audioPath, boolean repeat) {
        this.text = text;
        this.audioPath = audioPath;
        this.repeat = repeat;
    }
}
