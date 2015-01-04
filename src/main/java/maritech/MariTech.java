package maritech;

import static maritech.lib.MTModInfo.AFTER;
import static maritech.lib.MTModInfo.JAVAPATH;
import static maritech.lib.MTModInfo.MODID;
import static maritech.lib.MTModInfo.MODNAME;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.lib.Modules;
import mariculture.core.util.MCTranslate;
import maritech.extensions.blocks.ExtensionMachine;
import maritech.extensions.blocks.ExtensionMachineMulti;
import maritech.extensions.blocks.ExtensionRenderedMachine;
import maritech.extensions.blocks.ExtensionRenderedMachineMulti;
import maritech.extensions.config.ExtensionGeneralStuff;
import maritech.extensions.config.ExtensionMachines;
import maritech.extensions.items.ExtensionCrafting;
import maritech.extensions.modules.ExtensionCore;
import maritech.extensions.modules.ExtensionDiving;
import maritech.extensions.modules.ExtensionFactory;
import maritech.extensions.modules.ExtensionFishery;
import maritech.extensions.modules.ExtensionTransport;
import maritech.extensions.modules.ExtensionWorldPlus;
import maritech.handlers.BlockEvents;
import maritech.handlers.ConfigEvents;
import maritech.handlers.FLUDDEvents;
import maritech.handlers.GuiEvents;
import maritech.handlers.ItemEvents;
import maritech.handlers.RegistryEvents;
import maritech.plugins.RitualOfTheBloodRiver;
import net.minecraftforge.common.MinecraftForge;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MODID, name = MODNAME, dependencies = AFTER)
public class MariTech {
    @SidedProxy(clientSide = JAVAPATH + "MTClientProxy", serverSide = JAVAPATH + "MTCommonProxy")
    public static MTCommonProxy proxy;

    @Instance(MODID)
    public static MariTech instance;

    @EventHandler
    public void onConstructing(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(new ConfigEvents());
        MinecraftForge.EVENT_BUS.register(new RegistryEvents());
        ConfigEvents.register(new ExtensionGeneralStuff());
        ConfigEvents.register(new ExtensionMachines());
        RegistryEvents.register(new ExtensionCore());
        RegistryEvents.register(new ExtensionDiving());
        RegistryEvents.register(new ExtensionFactory());
        RegistryEvents.register(new ExtensionFishery());
        RegistryEvents.register(new ExtensionTransport());
        RegistryEvents.register(new ExtensionWorldPlus());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MaricultureHandlers.HIGH_TECH_ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        MinecraftForge.EVENT_BUS.register(new ItemEvents());
        BlockEvents.register(Core.machines, new ExtensionMachine());
        BlockEvents.register(Core.machinesMulti, new ExtensionMachineMulti());
        BlockEvents.register(Core.renderedMachines, new ExtensionRenderedMachine());
        BlockEvents.register(Core.renderedMachinesMulti, new ExtensionRenderedMachineMulti());
        ItemEvents.register(Core.crafting, new ExtensionCrafting());

        if (Modules.isActive(Modules.factory)) {
            MinecraftForge.EVENT_BUS.register(new FLUDDEvents());
            FMLCommonHandler.instance().bus().register(new FLUDDEvents());
        }

        MinecraftForge.EVENT_BUS.register(new GuiEvents());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.setupClient();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("AWWayofTime")) {
            try {
                Rituals.registerRitual("MARIBLOODRIVER", 1, 50000, new RitualOfTheBloodRiver(), MCTranslate.translate("ritual"));
            } catch (Exception e) {}
        }
    }
}