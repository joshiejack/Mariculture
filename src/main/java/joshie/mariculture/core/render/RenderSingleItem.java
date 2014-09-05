package joshie.mariculture.core.render;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.events.MaricultureEvents;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.diving.render.ModelAirPump;
import joshie.maritech.model.ModelRotor;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderSingleItem implements IItemRenderer {
    private static final float scale = (float) (1.0 / 20.0);
    private static final ResourceLocation AIR_PUMP = new ResourceLocation("mariculture", "textures/blocks/air_pump_texture.png");
    private static final ResourceLocation TURBINE = new ResourceLocation("mariculture", "textures/blocks/turbine_texture.png");
    private static final ResourceLocation TURBINE_GAS = new ResourceLocation("mariculture", "textures/blocks/turbine_gas_texture.png");
    private static final ResourceLocation TURBINE_HAND = new ResourceLocation("mariculture", "textures/blocks/turbine_hand_texture.png");
    private static final ResourceLocation COPPER = new ResourceLocation(Mariculture.modid, "textures/blocks/copperRotor.png");
    private static final ResourceLocation ALUMINUM = new ResourceLocation(Mariculture.modid, "textures/blocks/aluminumRotor.png");
    private static final ResourceLocation TITANIUM = new ResourceLocation(Mariculture.modid, "textures/blocks/titaniumRotor.png");

    private final ModelAirPump pump = new ModelAirPump();
    private final ModelRotor rotor = new ModelRotor();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (item.getItem() == Item.getItemFromBlock(Core.renderedMachines)) {
            switch (item.getItemDamage()) {
                case MachineRenderedMeta.GEYSER:
                case MachineRenderedMeta.ANVIL:
                case MachineRenderedMeta.INGOT_CASTER:
                case MachineRenderedMeta.BLOCK_CASTER:
                case MachineRenderedMeta.NUGGET_CASTER:
                case MachineRenderedMeta.AUTO_HAMMER:
                case MachineRenderedMeta.FISH_FEEDER:
                    return false;
                default:
                    return true;
            }
        }

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    private void bindTexture(ResourceLocation resource) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItem() == Item.getItemFromBlock(Core.renderedMachines) && item.getItemDamage() == MachineRenderedMeta.AIR_PUMP) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(AIR_PUMP);
            pump.renderInventory(type);
        }

        MaricultureEvents.render(item, type);
    }
}
