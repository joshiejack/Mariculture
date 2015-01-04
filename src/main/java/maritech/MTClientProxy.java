package maritech;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.Modules;
import mariculture.core.render.RenderHandler;
import mariculture.core.render.RenderSpecialHandler;
import mariculture.factory.Factory;
import mariculture.factory.render.RenderCustomItem;
import maritech.entity.EntityFLUDDSquirt;
import maritech.entity.EntitySpeedBoat;
import maritech.extensions.blocks.icons.ExtensionIconMachine;
import maritech.extensions.blocks.icons.ExtensionIconMachineMulti;
import maritech.extensions.blocks.icons.ExtensionIconRenderedMachine;
import maritech.extensions.modules.ExtensionFactory;
import maritech.extensions.modules.ExtensionTransport;
import maritech.handlers.IconEvents;
import maritech.handlers.RenderEvents;
import maritech.lib.MTRenderIds;
import maritech.model.ModelFLUDD;
import maritech.model.ModelRotor;
import maritech.render.RenderCompressorBase;
import maritech.render.RenderCompressorTop;
import maritech.render.RenderFLUDDItem;
import maritech.render.RenderFLUDDSquirt;
import maritech.render.RenderPressureVessel;
import maritech.render.RenderSpeedBoat;
import maritech.render.RenderSpeedBoatItem;
import maritech.tile.TileFLUDDStand;
import maritech.tile.TileRotorAluminum;
import maritech.tile.TileRotorCopper;
import maritech.tile.TileRotorTitanium;
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
        MinecraftForge.EVENT_BUS.register(new IconEvents());
        MinecraftForge.EVENT_BUS.register(new RenderEvents());
        IconEvents.register(Core.machines, new ExtensionIconMachine());
        IconEvents.register(Core.machinesMulti, new ExtensionIconMachineMulti());
        IconEvents.register(Core.renderedMachines, new ExtensionIconRenderedMachine());

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
