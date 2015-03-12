package maritech;

import static maritech.lib.MTModInfo.AFTER;
import static maritech.lib.MTModInfo.JAVAPATH;
import static maritech.lib.MTModInfo.MODID;
import static maritech.lib.MTModInfo.MODNAME;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.BlockMachine;
import mariculture.core.blocks.BlockMachineMulti;
import mariculture.core.blocks.BlockRenderedMachine;
import mariculture.core.blocks.BlockRenderedMachineMulti;
import mariculture.core.blocks.items.ItemBlockMachine;
import mariculture.core.blocks.items.ItemBlockMachineMulti;
import mariculture.core.blocks.items.ItemBlockRenderedMachine;
import mariculture.core.blocks.items.ItemBlockRenderedMachineMulti;
import mariculture.core.items.ItemCrafting;
import mariculture.core.lib.Modules;
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
import maritech.plugins.BloodMagic;
import maritech.plugins.Enchiridion;
import net.minecraftforge.common.MinecraftForge;
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
        MaricultureHandlers.HIGH_TECH_ENABLED = true;
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
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        MinecraftForge.EVENT_BUS.register(new ItemEvents());
        ItemEvents.register(ItemCrafting.class, new ExtensionCrafting());
        BlockEvents.register(BlockMachine.class, ItemBlockMachine.class, new ExtensionMachine());
        BlockEvents.register(BlockMachineMulti.class, ItemBlockMachineMulti.class, new ExtensionMachineMulti());
        BlockEvents.register(BlockRenderedMachine.class, ItemBlockRenderedMachine.class, new ExtensionRenderedMachine());
        BlockEvents.register(BlockRenderedMachineMulti.class, ItemBlockRenderedMachineMulti.class, new ExtensionRenderedMachineMulti());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
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
            BloodMagic.init();
        }
        
        if (Loader.isModLoaded("Enchiridion")) {
            Enchiridion.init();
        }
    }
}