package joshie.maritech;

import static joshie.maritech.lib.MTModInfo.JAVAPATH;
import static joshie.maritech.lib.MTModInfo.MODID;
import static joshie.maritech.lib.MTModInfo.MODNAME;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.network.PacketHandler;
import joshie.maritech.extensions.blocks.ExtensionMachine;
import joshie.maritech.extensions.config.ExtensionGeneralStuff;
import joshie.maritech.extensions.config.ExtensionMachines;
import joshie.maritech.extensions.modules.ExtensionFactory;
import joshie.maritech.extensions.modules.ExtensionFishery;
import joshie.maritech.extensions.modules.ExtensionTransport;
import joshie.maritech.extensions.modules.ExtensionWorldPlus;
import joshie.maritech.handlers.BlockEvents;
import joshie.maritech.handlers.ConfigEvents;
import joshie.maritech.handlers.RegistryEvents;
import joshie.maritech.network.PacketFLUDD;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = MODID, name = MODNAME/*, dependencies = "required-after:Mariculture@[1.2.4,);"*/)
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
        RegistryEvents.register(new ExtensionFactory());
        RegistryEvents.register(new ExtensionFishery());
        RegistryEvents.register(new ExtensionTransport());
        RegistryEvents.register(new ExtensionWorldPlus());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MaricultureHandlers.HIGH_TECH_ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        BlockEvents.blocks.put(Core.machines, new ExtensionMachine());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PacketHandler.registerPacket(PacketFLUDD.class, Side.CLIENT);
        PacketHandler.registerPacket(PacketFLUDD.class, Side.SERVER);
        proxy.setupClient();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        return;
    }
}