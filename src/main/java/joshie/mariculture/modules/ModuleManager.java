package joshie.mariculture.modules;

import com.google.common.collect.Lists;
import joshie.mariculture.Mariculture;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.core.helpers.ConfigHelper;
import joshie.mariculture.core.helpers.MCReflectionHelper;
import joshie.mariculture.core.util.annotation.MCApiImpl;
import joshie.mariculture.core.util.annotation.MCEvents;
import joshie.mariculture.core.util.annotation.MCLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.discovery.asm.ModAnnotation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

import static joshie.mariculture.core.lib.MaricultureInfo.MODNAME;

public abstract class ModuleManager {
	public static HashMap<String, Class<?>> enabled = new HashMap<>();

    public static void loadModules(@Nonnull ASMDataTable table, boolean isClient) {
        HashMap<String, Triple<String, String, String>> moduleData = new HashMap<>();
        Set<String> enabled = new  HashSet<>();
        initModules(table, enabled, moduleData);
        initAPI(table, enabled);
        initClasses(enabled, moduleData);
        registerEventHandlers(table, isClient);
    }

    private static void initModules(@Nonnull ASMDataTable table, Set<String> enabled, HashMap<String, Triple<String, String, String>> moduleData) {
        //Load in all the data for each module
        String annotationClassName = MCLoader.class.getCanonicalName();
        Set<ASMData> asmDatas = new HashSet<>(table.getAll(annotationClassName));

        asmLoop:
        for (ASMDataTable.ASMData asmData : asmDatas) {
            try {
                Map<String, Object> data = asmData.getAnnotationInfo();
                String name = WordUtils.uncapitalize(asmData.getClassName().substring(asmData.getClassName().lastIndexOf('.') + 1).trim());
                boolean isEnabledByDefault = !asmData.getClassName().contains("mariculture.modules.hardcore") && !asmData.getClassName().contains("mariculture.modules.debug");
                name = asmData.getClassName().contains("mariculture.modules.hardcore") ? "hardcore-" + name : name;
                String dependencies = data.get("modules") != null ? (String) data.get("modules") : "";
                String type = "modules";

                //Mod Check
                String mods = data.get("mods") != null ? (String)data.get("mods") : "";
                if (mods != null && !mods.equals("")) {
                    String[] modList = mods.replace(" ", "").split(",");
                    for (String mod: modList) {
                        if (!Loader.isModLoaded(mod)) continue asmLoop;
                    }

                    //If we have any mod requirements, we're a plugin
                    type = "plugins";
                }

                //If the module is enabled
                if (isModuleEnabled(type, name, isEnabledByDefault)) {
                    enabled.add(name);
                    moduleData.put(name, Triple.of(asmData.getClassName(), type, dependencies));
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private static void initAPI(@Nonnull ASMDataTable table, Set<String> enabled) {
        Set<ASMData> datas = new HashSet<>(table.getAll(MCApiImpl.class.getCanonicalName()));
        for (ASMDataTable.ASMData data : datas) {
            try {
                Map<String, Object> theData = data.getAnnotationInfo();
                String modules = theData.get("value") != null ? (String) theData.get("value") : "";
                List<String> dependencies = Lists.newArrayList(modules.replace(" ", "").split(","));
                if (modules.equals("") || enabled.containsAll(dependencies)) {
                    //Load in the api, after we've checked if the required module is loaded
                    Class clazz = Class.forName(data.getClassName());
                    Object instance = clazz.getField("INSTANCE").get(null);
                    Class[] interfaces = clazz.getInterfaces();
                    if (interfaces != null && interfaces.length > 0) {
                        for (Class inter : interfaces) {
                            for (Field f : MaricultureAPI.class.getFields()) {
                                if (f.getType().equals(inter)) {
                                    MCReflectionHelper.setFinalStatic(instance, f);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private static void initClasses(Set<String> enabled, HashMap<String, Triple<String, String, String>> moduleData) {
        for (String module: enabled) {
            Triple<String, String, String> data = moduleData.get(module);
            String dependencyList = data.getRight().replace(" ", "");
            List<String> dependencies = Lists.newArrayList(dependencyList.split(","));
            if (dependencyList.equals("") || enabled.containsAll(dependencies)) {
                try {
                    ModuleManager.enabled.put(module, Class.forName(data.getLeft()));
                    Mariculture.logger.log(Level.INFO, "Enabling the "
                            + StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(WordUtils.capitalize(module)), ' ')
                            + " " + data.getMiddle().replace("s", "") + "!");
                } catch (ClassNotFoundException ex) { ex.printStackTrace(); }
            }
        }
    }

	private static void registerEventHandlers(@Nonnull ASMDataTable asmDataTable, boolean isClient) {
        String annotationClassName = MCEvents.class.getCanonicalName();
        Set<ASMData> asmDatas = new HashSet<>(asmDataTable.getAll(annotationClassName));
        for (ASMDataTable.ASMData asmData : asmDatas) {
            try {
                Map<String, Object> data = asmData.getAnnotationInfo();
                String side = data.get("value") != null ? ReflectionHelper.getPrivateValue(ModAnnotation.EnumHolder.class, (ModAnnotation.EnumHolder) data.get("value"), "value") : "";
                String modules = data.get("modules") != null ? (String) data.get("modules") : "";
                List<String> dependencies = Lists.newArrayList(modules.replace(" ", "").split(","));
                if (modules.equals("") || enabled.keySet().containsAll(dependencies)) {
                    if ((side.equals("CLIENT") && isClient) || side.equals("")) {
                        Class clazz = Class.forName(asmData.getClassName());
                        Method register = MCReflectionHelper.getMethod(clazz, "register");
                        if (register == null || ((Boolean) register.invoke(null))) {
                            Field INSTANCE = MCReflectionHelper.getField(clazz, "INSTANCE");
                            if (INSTANCE == null) MinecraftForge.EVENT_BUS.register(clazz.newInstance());
                            else MinecraftForge.EVENT_BUS.register(INSTANCE.get(null));
                        }
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException ex) { ex.printStackTrace(); }
        }
	}

    public static void loadConfigs() {
        for (Entry<String, Class<?>> entry: enabled.entrySet()){
            try {
                Method configure = MCReflectionHelper.getMethod(entry.getValue(), "configure");
                if (configure != null) {
                    Configuration config = ConfigHelper.getConfig();
                    ConfigHelper.setCategory(entry.getKey());
                    try {
                        config.load();
                        configure.invoke(null);
                    } finally {
                        config.save();
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException ex) { /*Ignore errors! */ }
        }
    }

	private static boolean isModuleEnabled(String type, String name, boolean isEnabledByDefault) {
		Configuration config = ConfigHelper.getConfig();
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
		return enabled.containsKey(name);
	}
}
