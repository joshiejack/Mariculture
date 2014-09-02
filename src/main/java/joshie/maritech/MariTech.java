package joshie.maritech;

import static joshie.maritech.lib.ModInfo.MODID;
import static joshie.maritech.lib.ModInfo.MODNAME;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MODID, name = MODNAME, dependencies = "required-after:Mariculture@[1.2.4,);")
public class MariTech {
    @Instance(MODID)
    public static MariTech instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        
    }
}