package joshie.mariculture.modules;

import com.google.common.collect.Lists;
import joshie.mariculture.Mariculture;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static joshie.mariculture.Mariculture.root;
import static joshie.mariculture.lib.MaricultureInfo.MODNAME;

public abstract class ModuleManager {
	public static HashMap<String, Class<?>> enabled = new HashMap<>();

	public static void init(@Nonnull ASMDataTable dataTable) {
		//Load in the module data from the tale
		String annotationClassName = Module.class.getCanonicalName();
		Set<ASMData> asmDatas = new HashSet<>(dataTable.getAll(annotationClassName));
		Set<String> enabled = new  HashSet<>();
		HashMap<String, Triple<Class<?>, String, String>> moduleData = new HashMap<>();

		asmLoop:
		for (ASMDataTable.ASMData asmData : asmDatas) {
			try {
				//Class<?> asmClass = Class.forName(asmData.getClassName());
				Map<String, Object> data = asmData.getAnnotationInfo();
				String name = (String) data.get("name");
				boolean isEnabledByDefault = data.get("disableByDefault") != null ? !(Boolean) data.get("disableByDefault") : true;
				String dependencies = data.get("modules") != null ? (String) data.get("modules") : "core";
				String type = "Modules";

				//Mod Check
				String mods = data.get("mods") != null ? (String)data.get("mods") : "";
				if (mods != null && !mods.equals("")) {
					String[] modList = mods.replace(" ", "").split(",");
					for (String mod: modList) {
						if (!Loader.isModLoaded(mod)) continue asmLoop;
					}

					//If we have any mod requirements, we're a plugin
					type = "Plugins";
				}

				//If the
				if (name.equals("core") || isModuleEnabled(new Configuration(new File(root, type.toLowerCase() + ".cfg")), type, name, isEnabledByDefault)) {
					enabled.add(name);
					Class<?> asmClass = Class.forName(asmData.getClassName());
					moduleData.put(name, Triple.of(asmClass, type, dependencies));
				}
			} catch (Exception e) { e.printStackTrace(); }
		}

		for (String module: enabled) {
			Triple<Class<?>, String, String> data = moduleData.get(module);
			if (enabled.containsAll(Lists.newArrayList(data.getRight().split(",")))) {
				try {
					ModuleManager.enabled.put(module, data.getLeft());
                    Mariculture.logger.log(Level.INFO, "Enabling the " + WordUtils.capitalize(module) + " " + data.getMiddle().replace("s", "") + "!");
					try {
						Configuration config = new Configuration(new File(root, data.getMiddle().replace("s", "").toLowerCase() + ".cfg"));
						try {
							config.load();
							data.getLeft().getMethod("loadConfig", Configuration.class).invoke(null, config);
						}  finally {
							config.save();
						}
					} catch (Exception e) {}
				} catch (Exception e) {}
			}
		}
	}

	public static void clear() {
		enabled = null; //Remove the map from memory, as the information is no longer needed
	}

	private static boolean isModuleEnabled(Configuration config, String type, String name, boolean isEnabledByDefault) {
		try {
			config.load();
			return config.get(type, name, isEnabledByDefault).getBoolean(isEnabledByDefault);
		} catch (Exception e) {
			Mariculture.logger.log(Level.ERROR, MODNAME + " failed to load it's " + type.toLowerCase() + " config");
			e.printStackTrace();
		} finally {
			config.save();
		}

		return false;
	}
}
