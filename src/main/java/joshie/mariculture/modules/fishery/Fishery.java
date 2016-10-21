package joshie.mariculture.modules.fishery;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.api.fishing.FishingTrait;
import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.modules.fishery.block.BlockFishOil;
import joshie.mariculture.modules.fishery.block.BlockFishery;
import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import joshie.mariculture.modules.fishery.item.ItemFishingRodMC;
import joshie.mariculture.modules.fishery.loot.*;
import joshie.mariculture.modules.fishery.render.FishingRodLoader;
import joshie.mariculture.modules.fishery.rod.RodHook;
import joshie.mariculture.modules.fishery.rod.RodPole;
import joshie.mariculture.modules.fishery.rod.RodReel;
import joshie.mariculture.modules.fishery.rod.RodString;
import joshie.mariculture.modules.fishery.traits.TraitLuck;
import joshie.mariculture.modules.fishery.traits.TraitResistance;
import joshie.mariculture.modules.fishery.traits.TraitSpeed;
import joshie.mariculture.modules.fishery.traits.TraitWood;
import joshie.mariculture.modules.fishery.utils.FishingRodHelper;
import joshie.mariculture.modules.fishery.utils.GuiHandler;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.api.fishing.Fishing.Size.*;
import static joshie.mariculture.core.lib.MaricultureInfo.MODID;
import static joshie.mariculture.modules.fishery.rod.RodHook.HOOKS;
import static joshie.mariculture.modules.fishery.rod.RodPole.POLES;
import static joshie.mariculture.modules.fishery.rod.RodReel.REELS;
import static joshie.mariculture.modules.fishery.rod.RodString.STRINGS;
import static net.minecraftforge.client.model.ModelLoaderRegistry.registerLoader;

/** The Fishery module is about the fish, and catching them,
 *  gutting them and processing them */
@MCLoader
public class Fishery {
    public static final Fluid FISH_OIL = registerFluid("fish_oil");
    public static final ItemFishingRodMC FISHING_ROD = new ItemFishingRodMC().register("fishing_rod");
    public static final BlockFishery FISHERY_BLOCK = new BlockFishery().register("fishing_block");
    public static final BlockFishOil FISH_OIL_BLOCK = new BlockFishOil(FISH_OIL).register("fish_oil");
    public static final FishingTrait XP_BONUS = new TraitWood();
    public static final FishingTrait LUCK_BONUS = new TraitLuck();
    public static final FishingTrait SPEED_BONUS = new TraitSpeed();
    public static final FishingTrait RESISTANCE = new TraitResistance();

    @SuppressWarnings("ConstantConditions")
    public static void preInit() {
        MCTab.getFishery().setStack(new ItemStack(FISHING_ROD));
        NetworkRegistry.INSTANCE.registerGuiHandler(Mariculture.instance, new GuiHandler()); //Register the gui handler here, as the bait bag will be the only gui
        EntityRegistry.registerModEntity(EntityFishHookMC.class, "hook", 0, Mariculture.instance, 64, 5, true);
        EntityRegistry.instance().lookupModSpawn(EntityFishHookMC.class, false).setCustomSpawning(null, true);

        MaricultureAPI.fishing.createPole("wood", 64, 3).setTraits(XP_BONUS);
        MaricultureAPI.fishing.createPole("reed", 96, 2).setTraits(LUCK_BONUS);
        MaricultureAPI.fishing.createPole("polished_wood", 256, 5).setTraits(RESISTANCE);
        MaricultureAPI.fishing.createReel("wood", 1F, 0F, 0.15D).setTraits(XP_BONUS);
        MaricultureAPI.fishing.createReel("iron", 1.1F, 0.5F, 0.25D).setTraits(RESISTANCE);
        MaricultureAPI.fishing.createString("string", 1.0F).setTraits(SPEED_BONUS);
        MaricultureAPI.fishing.createString("reinforced", 1.2F).setTraits(LUCK_BONUS);
        MaricultureAPI.fishing.createString("tensile", 1.5F).setTraits(RESISTANCE);
        MaricultureAPI.fishing.createHook("wood", 1, TINY, SMALL).setTraits(XP_BONUS);
        MaricultureAPI.fishing.createHook("iron", 2, HUGE, GIGANTIC).setTraits(RESISTANCE);
        MaricultureAPI.fishing.createHook("fly", 2, MEDIUM, LARGE).setTraits(SPEED_BONUS);

        //Register the custom loot conditions for mariculture
        LootConditionManager.registerCondition(new FishSize.Serializer());
        LootConditionManager.registerCondition(new RodStrength.Serializer());
        LootConditionManager.registerCondition(new InBiomeType.Serializer());
        LootConditionManager.registerCondition(new SalinityType.Serializer());
        LootConditionManager.registerCondition(new WorldHeight.Serializer());
        LootConditionManager.registerCondition(new WorldTime.Serializer());
        LootConditionManager.registerCondition(new WorldDimension.Serializer());
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient() throws Exception {
        RenderingRegistry.registerEntityRenderingHandler(EntityFishHookMC.class, RenderFish:: new);
        registerLoader(FishingRodLoader.INSTANCE);
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {
        RodPole polishedWoodPole = POLES.get(new ResourceLocation(MODID, "polished_wood"));
        RodReel ironReel = REELS.get(new ResourceLocation(MODID, "iron"));
        RodString stringString = STRINGS.get(new ResourceLocation(MODID, "string"));
        RodHook ironHook = HOOKS.get(new ResourceLocation(MODID, "iron"));
        MCTab.getFishery().setStack(FishingRodHelper.build(polishedWoodPole, ironReel, stringString, ironHook));
    }

    private static Fluid registerFluid(String name) {
        Fluid fluid = new Fluid(name, new ResourceLocation(MODID, "blocks/" + name + "_still"), new ResourceLocation(MODID, "blocks/" + name + "_flow"));
        FluidRegistry.registerFluid(fluid);
        return fluid;
    }
}
