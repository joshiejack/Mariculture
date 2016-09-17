package joshie.mariculture.modules.fishery;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import joshie.mariculture.modules.fishery.item.ItemFishingRodMC;
import joshie.mariculture.modules.fishery.loot.*;
import joshie.mariculture.modules.fishery.utils.GuiHandler;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The Fishery module is about the fish, and catching them,
 *  gutting them and processing them */
@MCLoader
public class Fishery {
    public static final ItemFishingRodMC FISHING_ROD = new ItemFishingRodMC().register("rod");

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
    public static void preInitClient() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFishHookMC.class, RenderFish:: new);
    }
}
