package joshie.mariculture.modules.fishery;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.fishing.rod.*;
import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import joshie.mariculture.modules.fishery.item.ItemFishingRodMC;
import joshie.mariculture.modules.fishery.loot.*;
import joshie.mariculture.modules.fishery.render.FishingRodLoader;
import joshie.mariculture.modules.fishery.utils.FishingRodHelper;
import joshie.mariculture.modules.fishery.utils.GuiHandler;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraftforge.client.model.ModelLoaderRegistry.registerLoader;

/** The Fishery module is about the fish, and catching them,
 *  gutting them and processing them */
@MCLoader
public class Fishery {
    public static final ItemFishingRodMC FISHING_ROD = new ItemFishingRodMC().register("fishing_rod");
    public static final FishingPole POLE_WOOD = FishingPole.create("wood", 64, 25);
    public static final FishingPole POLE_REED = FishingPole.create("reed", 96, 30);
    public static final FishingPole POLE_POLISHED_WOOD = FishingPole.create("polished_wood", 256, 50);
    public static final FishingReel REEL_WOOD = FishingReel.create("wood");
    public static final FishingReel REEL_IRON = FishingReel.create("iron");
    public static final FishingString STRING_STRING = FishingString.create("string", 1.0F);
    public static final FishingString STRING_REINFORCED = FishingString.create("reinforced", 1.2F);
    public static final FishingString STRING_TENSILE = FishingString.create("tensile", 1.5F);
    public static final FishingHook HOOK_WOOD = FishingHook.create("wood");
    public static final FishingHook HOOK_IRON = FishingHook.create("iron");
    public static final FishingHook HOOK_FLY = FishingHook.create("fly");

    public static void preInit() {
        MCTab.getFishery().setStack(new ItemStack(FISHING_ROD));
        NetworkRegistry.INSTANCE.registerGuiHandler(Mariculture.instance, new GuiHandler()); //Register the gui handler here, as the bait bag will be the only gui
        EntityRegistry.registerModEntity(EntityFishHookMC.class, "hook", 0, Mariculture.instance, 64, 5, true);
        EntityRegistry.instance().lookupModSpawn(EntityFishHookMC.class, false).setCustomSpawning(null, true);

        //Register the custom loot conditions for mariculture
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
        MCTab.getFishery().setStack(FishingRodHelper.build(POLE_POLISHED_WOOD, REEL_IRON, STRING_STRING, HOOK_IRON));
    }
}
