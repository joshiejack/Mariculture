package joshie.mariculture.core.render;

import joshie.mariculture.api.events.MaricultureEvents;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.diving.render.ModelAirPump;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderSingleItem implements IItemRenderer {
    private static final ResourceLocation AIR_PUMP = new ResourceLocation("mariculture", "textures/blocks/air_pump_texture.png");
    private final ModelAirPump pump = new ModelAirPump();

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
