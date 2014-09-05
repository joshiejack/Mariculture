package joshie.maritech;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineRenderedMultiMeta;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.render.RenderHandler;
import joshie.mariculture.core.render.RenderSpecialHandler;
import joshie.mariculture.factory.Factory;
import joshie.mariculture.factory.render.RenderCustomItem;
import joshie.maritech.entity.EntityFLUDDSquirt;
import joshie.maritech.entity.EntitySpeedBoat;
import joshie.maritech.extensions.modules.ExtensionFactory;
import joshie.maritech.extensions.modules.ExtensionTransport;
import joshie.maritech.handlers.RenderEvents;
import joshie.maritech.lib.MTRenderIds;
import joshie.maritech.model.ModelFLUDD;
import joshie.maritech.model.ModelRotor;
import joshie.maritech.render.RenderCompressorBase;
import joshie.maritech.render.RenderCompressorTop;
import joshie.maritech.render.RenderFLUDDItem;
import joshie.maritech.render.RenderFLUDDSquirt;
import joshie.maritech.render.RenderPressureVessel;
import joshie.maritech.render.RenderSpeedBoat;
import joshie.maritech.render.RenderSpeedBoatItem;
import joshie.maritech.tile.TileFLUDDStand;
import joshie.maritech.tile.TileRotorAluminum;
import joshie.maritech.tile.TileRotorCopper;
import joshie.maritech.tile.TileRotorTitanium;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MTClientProxy extends MTCommonProxy {
    private static final ResourceLocation FLUDD = new ResourceLocation(Mariculture.modid, "textures/blocks/fludd_texture.png");
    private static final ResourceLocation COPPER = new ResourceLocation(Mariculture.modid, "textures/blocks/copperRotor.png");
    private static final ResourceLocation ALUMINUM = new ResourceLocation(Mariculture.modid, "textures/blocks/aluminumRotor.png");
    private static final ResourceLocation TITANIUM = new ResourceLocation(Mariculture.modid, "textures/blocks/titaniumRotor.png");

    @Override
    public void setupClient() {
        MinecraftForge.EVENT_BUS.register(new RenderEvents());

        if (Modules.isActive(Modules.diving)) {
            MTRenderIds.SCUBA = RenderingRegistry.addNewArmourRendererPrefix("scuba");
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_BASE, RenderCompressorBase.class);
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_TOP, RenderCompressorTop.class);
        }

        if (Modules.isActive(Modules.factory)) {
            MTRenderIds.FLUDD = RenderingRegistry.addNewArmourRendererPrefix("fludd");
            RenderingRegistry.registerEntityRenderingHandler(EntityFLUDDSquirt.class, new RenderFLUDDSquirt());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Factory.customRFBlock), new RenderCustomItem());
            MinecraftForgeClient.registerItemRenderer(ExtensionFactory.fludd, new RenderFLUDDItem());
            ClientRegistry.bindTileEntitySpecialRenderer(TileFLUDDStand.class, new RenderSpecialHandler(new ModelFLUDD(), FLUDD));
            ClientRegistry.bindTileEntitySpecialRenderer(TileRotorCopper.class, new RenderSpecialHandler(new ModelRotor(), COPPER));
            ClientRegistry.bindTileEntitySpecialRenderer(TileRotorAluminum.class, new RenderSpecialHandler(new ModelRotor(), ALUMINUM));
            ClientRegistry.bindTileEntitySpecialRenderer(TileRotorTitanium.class, new RenderSpecialHandler(new ModelRotor(), TITANIUM));
            RenderHandler.register(Core.renderedMachinesMulti, MachineRenderedMultiMeta.PRESSURE_VESSEL, RenderPressureVessel.class);
        }

        if (Modules.isActive(Modules.transport)) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySpeedBoat.class, new RenderSpeedBoat());
            MinecraftForgeClient.registerItemRenderer(ExtensionTransport.speedBoat, new RenderSpeedBoatItem());
        }
    }
}
