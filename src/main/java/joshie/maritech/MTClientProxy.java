package joshie.maritech;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.render.RenderSpecialHandler;
import joshie.mariculture.factory.render.RenderCustomItem;
import joshie.maritech.entity.EntityFLUDDSquirt;
import joshie.maritech.entity.EntitySpeedBoat;
import joshie.maritech.extensions.modules.ExtensionFactory;
import joshie.maritech.extensions.modules.ExtensionTransport;
import joshie.maritech.handlers.RenderEvents;
import joshie.maritech.lib.MTRenderIds;
import joshie.maritech.model.ModelFLUDD;
import joshie.maritech.render.RenderFLUDDItem;
import joshie.maritech.render.RenderFLUDDSquirt;
import joshie.maritech.render.RenderSpeedBoat;
import joshie.maritech.render.RenderSpeedBoatItem;
import joshie.maritech.tile.TileFLUDDStand;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MTClientProxy extends MTCommonProxy {
    private static final ResourceLocation FLUDD = new ResourceLocation(Mariculture.modid, "textures/blocks/fludd_texture.png");

    @Override
    public void setupClient() {
        MinecraftForge.EVENT_BUS.register(new RenderEvents());

        if (Modules.isActive(Modules.diving)) {
            MTRenderIds.SCUBA = RenderingRegistry.addNewArmourRendererPrefix("scuba");
        }

        if (Modules.isActive(Modules.factory)) {
            MTRenderIds.FLUDD = RenderingRegistry.addNewArmourRendererPrefix("fludd");
            RenderingRegistry.registerEntityRenderingHandler(EntityFLUDDSquirt.class, new RenderFLUDDSquirt());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ExtensionFactory.customRFBlock), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(ExtensionFactory.fludd, new RenderFLUDDItem());
            ClientRegistry.bindTileEntitySpecialRenderer(TileFLUDDStand.class, new RenderSpecialHandler(new ModelFLUDD(), FLUDD));
        }

        if (Modules.isActive(Modules.transport)) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySpeedBoat.class, new RenderSpeedBoat());
            MinecraftForgeClient.registerItemRenderer(ExtensionTransport.speedBoat, new RenderSpeedBoatItem());
        }
    }
}
