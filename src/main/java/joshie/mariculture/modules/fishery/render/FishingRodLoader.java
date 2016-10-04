package joshie.mariculture.modules.fishery.render;

import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.api.fishing.rod.*;
import joshie.mariculture.core.util.annotation.MCEvents;
import joshie.mariculture.modules.fishery.Fishery;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

@MCEvents(value = Side.CLIENT, modules = "fishery")
public class FishingRodLoader implements ICustomModelLoader {
    public static final FishingRodLoader INSTANCE = new FishingRodLoader();

    private void registerSprites(TextureMap map, FishingComponent component, String suffix) {
        map.registerSprite(new ResourceLocation(component.getResource().getResourceDomain(), "items/fishing/" + component.getPrefix() + "_" + component.getResource().getResourcePath() + suffix));
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            ItemStack held = player.getHeldItemMainhand();
            if (held != null && held.getItem() == Fishery.FISHING_ROD) {
                FishingRod rod = MaricultureAPI.fishing.getFishingRodFromStack(held);
                if (rod != null) rod.setCastStatus(player.fishEntity != null);
            }
        }
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        TextureMap map = event.getMap();
        for (FishingPole pole: FishingPole.POLES.values()) registerSprites(map, pole, "");
        for (FishingReel reel: FishingReel.REELS.values()) registerSprites(map, reel, "");
        for (FishingString string: FishingString.STRINGS.values()) registerSprites(map, string, "_cast");
        for (FishingString string: FishingString.STRINGS.values()) registerSprites(map, string, "_uncast");
        for (FishingHook hook: FishingHook.HOOKS.values()) registerSprites(map, hook, "");
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals("mariculture") && modelLocation.getResourcePath().equals("fishing_rod");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        return ModelFishingRod.MODEL;
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager){}
}
