package tconstruct;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tconstruct.achievements.TAchievements;
import tconstruct.client.TControls;
import tconstruct.common.TContent;
import tconstruct.common.TProxyCommon;
import tconstruct.common.TRecipes;
import tconstruct.common.TRepo;
import tconstruct.library.crafting.Detailing;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.util.EnvironmentChecks;
import tconstruct.util.TCraftingHandler;
import tconstruct.util.TEventHandler;
import tconstruct.util.TEventHandlerAchievement;
import tconstruct.util.config.DimensionBlacklist;
import tconstruct.util.config.PHConstruct;
import tconstruct.util.landmine.behavior.Behavior;
import tconstruct.util.landmine.behavior.stackCombo.SpecialStackHandler;
import tconstruct.util.player.TPlayerHandler;
import tconstruct.worldgen.SlimeIslandGen;
import tconstruct.worldgen.TBaseWorldGenerator;
import tconstruct.worldgen.TerrainGenEventHandler;
import tconstruct.worldgen.village.ComponentSmeltery;
import tconstruct.worldgen.village.ComponentToolWorkshop;
import tconstruct.worldgen.village.TVillageTrades;
import tconstruct.worldgen.village.VillageSmelteryHandler;
import tconstruct.worldgen.village.VillageToolStationHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * TConstruct, the tool mod. Craft your tools with style, then modify until the
 * original is gone!
 * 
 * @author mDiyo
 */

@Mod(modid = "TConstruct", name = "TConstruct", version = "${version}",
        dependencies = "required-after:Forge@[9.11,);after:ForgeMultipart;after:MineFactoryReloaded;after:NotEnoughItems;after:Waila;after:ThermalExpansion")
public class TConstruct
{
    /** The value of one ingot in millibuckets */
    public static final int ingotLiquidValue = 144;
    public static final int oreLiquidValue = ingotLiquidValue * 2;
    public static final int blockLiquidValue = ingotLiquidValue * 9;
    public static final int chunkLiquidValue = ingotLiquidValue / 2;
    public static final int nuggetLiquidValue = ingotLiquidValue / 9;

    public static final int liquidUpdateAmount = 6;

    // Shared mod logger
    public static final Logger logger = LogManager.getLogger("TConstruct");

    /* Instance of this mod, used for grabbing prototype fields */
    @Instance("TConstruct")
    public static TConstruct instance;
    /* Proxies for sides, used for graphics processing */
    @SidedProxy(clientSide = "tconstruct.client.TProxyClient", serverSide = "tconstruct.common.TProxyCommon")
    public static TProxyCommon proxy;

    // Module loader

    // The packet pipeline

    public TConstruct()
    {

        //logger.setParent(FMLCommonHandler.instance().getFMLLogger());
        if (Loader.isModLoaded("Natura"))
        {
            logger.info("Natura, what are we going to do tomorrow night?");
            LogManager.getLogger("Natura").info("TConstruct, we're going to take over the world!");
        }
        else
        {
            logger.info("Preparing to take over the world");
        }

        EnvironmentChecks.verifyEnvironmentSanity();
        MinecraftForge.EVENT_BUS.register(events = new TEventHandler());
    }

    @EventHandler
    public void preInit (FMLPreInitializationEvent event)
    {

        

    }

    @EventHandler
    public void init (FMLInitializationEvent event)
    {
       

    }

    @EventHandler
    public void postInit (FMLPostInitializationEvent evt)
    {
      

    }

    public static LiquidCasting getTableCasting ()
    {
        return tableCasting;
    }

    public static LiquidCasting getBasinCasting ()
    {
        return basinCasting;
    }

    public static Detailing getChiselDetailing ()
    {
        return chiselDetailing;
    }

    public static TContent content;
    public static TRecipes recipes;
    public static TEventHandler events;
    public static TPlayerHandler playerTracker;
    public static LiquidCasting tableCasting;
    public static LiquidCasting basinCasting;
    public static Detailing chiselDetailing;
}
