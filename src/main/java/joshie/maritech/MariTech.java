package joshie.maritech;

import static joshie.maritech.lib.MTModInfo.AFTER;
import static joshie.maritech.lib.MTModInfo.JAVAPATH;
import static joshie.maritech.lib.MTModInfo.MODID;
import static joshie.maritech.lib.MTModInfo.MODNAME;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.util.MCTranslate;
import joshie.maritech.extensions.blocks.ExtensionMachine;
import joshie.maritech.extensions.blocks.ExtensionMachineMulti;
import joshie.maritech.extensions.blocks.ExtensionRenderedMachine;
import joshie.maritech.extensions.blocks.ExtensionRenderedMachineMulti;
import joshie.maritech.extensions.config.ExtensionGeneralStuff;
import joshie.maritech.extensions.config.ExtensionMachines;
import joshie.maritech.extensions.items.ExtensionCrafting;
import joshie.maritech.extensions.modules.ExtensionCore;
import joshie.maritech.extensions.modules.ExtensionDiving;
import joshie.maritech.extensions.modules.ExtensionFactory;
import joshie.maritech.extensions.modules.ExtensionFishery;
import joshie.maritech.extensions.modules.ExtensionTransport;
import joshie.maritech.extensions.modules.ExtensionWorldPlus;
import joshie.maritech.handlers.BlockEvents;
import joshie.maritech.handlers.ConfigEvents;
import joshie.maritech.handlers.FLUDDEvents;
import joshie.maritech.handlers.GuiEvents;
import joshie.maritech.handlers.ItemEvents;
import joshie.maritech.handlers.RegistryEvents;
import joshie.maritech.plugins.RitualOfTheBloodRiver;
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