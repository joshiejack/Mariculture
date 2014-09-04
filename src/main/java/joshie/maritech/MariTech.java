package joshie.maritech;

import static joshie.maritech.lib.MTModInfo.MODID;
import static joshie.maritech.lib.MTModInfo.MODNAME;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.Core;
import joshie.maritech.extensions.ExtensionMachine;
import joshie.maritech.handlers.BlockEvents;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MODID, name = MODNAME/*, dependencies = "required-after:Mariculture@[1.2.4,);"*/)
public class MariTech {
    @Instance(MODID)
    public static MariTech instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MaricultureHandlers.HIGH_TECH_ENABLED = true;
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        BlockEvents.blocks.put(Core.machines, new ExtensionMachine());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}