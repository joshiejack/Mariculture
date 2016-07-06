package joshie.mariculture.modules;

import com.google.common.collect.Lists;
import joshie.mariculture.Mariculture;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.helpers.ConfigHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import static joshie.mariculture.Mariculture.root;
import static joshie.mariculture.lib.MaricultureInfo.MODNAME;

public abstract class ModuleManager {
	public static HashMap<String, Class<?>> enabled = new HashMap<>();
	private static HashSet<String> enabledList;

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
				String dependencies = data.get("modules") != null ? (String) data.get("modules") : "";
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

				//If the module is enabled
				if (isModuleEnabled(new Configuration(new File(root, type.toLowerCase() + ".cfg")), type, name, isEnabledByDefault)) {
					enabled.add(name);
					Class<?> asmClass = Class.forName(asmData.getClassName());
					moduleData.put(name, Triple.of(asmClass, type, dependencies));
				}
			} catch (Exception e) { e.printStackTrace(); }
		}

		for (String module: enabled) {
			Triple<Class<?>, String, String> data = moduleData.get(module);
			String dependencyList = data.getRight();
			List<String> dependencies = Lists.newArrayList(dependencyList.split(","));
			if (dependencyList.equals("") || enabled.containsAll(dependencies)) {
				try {
					ModuleManager.enabled.put(module, data.getLeft());
                    Mariculture.logger.log(Level.INFO, "Enabling the " + WordUtils.capitalize(module) + " " + data.getMiddle().replace("s", "") + "!");
					try {
						Configuration config = new Configuration(new File(root, module + ".cfg"));
						try {
							config.load();
							ConfigHelper.setConfig(config);
							data.getLeft().getMethod("configure").invoke(null);
						}  finally {
							config.save();
						}

						//Save on things
						ConfigHelper.setConfig(null); //Remove the reference to the config
						ConfigHelper.setCategory(null);
					} catch (Exception e) {}
				} catch (Exception e) {}
			}
		}

		registerEventHandlers(dataTable);
	}

	private static void registerEventHandlers(@Nonnull ASMDataTable dataTable) {
		String annotationClassName = EventAPIContainer.class.getCanonicalName();
		Set<ASMData> asmDatas = new HashSet<>(dataTable.getAll(annotationClassName));
		asmLoop:
		for (ASMDataTable.ASMData asmData : asmDatas) {
			Map<String, Object> data = asmData.getAnnotationInfo();
			String modules = (String) data.get("modules");
            boolean events = data.containsKey("events") && (Boolean) data.get("events");
			String[] moduleList = modules.replace(" ", "").split(",");
			for (String module: moduleList) {
				if (!enabled.containsKey(module)) continue asmLoop;
			}

			try {
				Class clazz = Class.forName(asmData.getClassName());
                Object instance = clazz.newInstance();
				Class[] interfaces = clazz.getInterfaces();
				if (interfaces != null && interfaces.length > 0) {
					for (Class inter: interfaces) {
						for (Field f: MaricultureAPI.class.getFields()) {
                            if (f.getType().equals(inter)) {
                                f.set(null, instance);
                            }
                        }
					}
				}


                if (events) MinecraftForge.EVENT_BUS.register(instance);
			} catch (Exception e) { e.printStackTrace(); }
		}
	}

	public static void clear() {
		enabledList = new HashSet<>(enabled.keySet()); //Create the smaller keyset for enabled modules
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

	//Mostly used Externally
	public static boolean isModuleEnabled(String name) {
		if (enabledList != null) return enabledList.contains(name);
		else if (enabled != null) return enabled.containsKey(name);
		else return false;
	}
}
