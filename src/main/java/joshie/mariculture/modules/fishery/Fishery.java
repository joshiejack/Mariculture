package joshie.mariculture.modules.fishery;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import joshie.mariculture.modules.fishery.handlers.GuiHandler;
import joshie.mariculture.modules.fishery.item.ItemFishingRodMC;
import joshie.mariculture.modules.fishery.loot.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The Fishery module is about the fish, and catching them,
 *  gutting them and processing them */
@Module(name = "fishery")
public class Fishery {
    public static final ItemFishingRodMC FISHING_ROD = new ItemFishingRodMC().register("rod");

    //Create the fishing api
    public static void configure() {
        MaricultureAPI.fishing = new FishingAPI();
    }

    public static void preInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Mariculture.instance, new GuiHandler()); //Register the gui handler here, as the bait bag will be the only gui
        EntityRegistry.registerModEntity(EntityFishHookMC.class, "Hook", 0, Mariculture.instance, 150, 3, true);

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
        RenderingRegistry.registerEntityRenderingHandler(EntityFishHookMC.class, new IRenderFactory<EntityFishHookMC>() {
            @Override
            public Render<? super EntityFishHookMC> createRenderFor(RenderManager manager) {
                return new RenderFish(manager);
            }
        });
    }
}
