package joshie.mariculture.modules.vanilla;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import joshie.mariculture.modules.vanilla.asm.ASMFishHook;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

public class VanillaConfig {
    @SerializedName("Enable ASM")
    private HashMap<String, Boolean> asmEnabled = new HashMap<>();

    static boolean isEnabled(Class clazz) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        File file = new File("config//" + MODID + "//asm.json");
        VanillaConfig config = null;
        if (!file.exists()) writeConfig(gson, file, new VanillaConfig().setDefaults());
        else config = getExistingConfig(gson, file);

        if (config.asmEnabled.get(clazz.getSimpleName()) == null) {
            config.asmEnabled.put(clazz.getSimpleName(), true);
            writeConfig(gson, file, config);
        }

        return config.asmEnabled.get(clazz.getSimpleName());
    }

    private VanillaConfig setDefaults() {
        asmEnabled.put(ASMFishHook.class.getSimpleName(), true);
        return this;
    }

    private static void writeConfig(Gson gson, File file, VanillaConfig config) {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(gson.toJson(config));
            writer.close(); //Write the default json to file
        } catch (Exception ignored) {}
    }

    private static VanillaConfig getExistingConfig(Gson gson, File file) {
        try {
            return gson.fromJson(FileUtils.readFileToString(file), VanillaConfig.class);
        } catch (Exception e) { return new VanillaConfig().setDefaults(); }
    }
}
