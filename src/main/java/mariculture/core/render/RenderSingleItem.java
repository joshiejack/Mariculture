package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.Modules;
import mariculture.diving.render.ModelAirPump;
import mariculture.factory.Factory;
import mariculture.factory.render.ModelFLUDD;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderSingleItem implements IItemRenderer {
    private static final float scale = (float) (1.0 / 20.0);
    private static final ResourceLocation AIR_PUMP = new ResourceLocation("mariculture", "textures/blocks/air_pump_texture.png");
    private static final ResourceLocation FLUDD = new ResourceLocation("mariculture", "textures/blocks/fludd_texture.png");
    private static final ResourceLocation TURBINE = new ResourceLocation("mariculture", "textures/blocks/turbine_texture.png");
    private static final ResourceLocation TURBINE_GAS = new ResourceLocation("mariculture", "textures/blocks/turbine_gas_texture.png");
    private static final ResourceLocation TURBINE_HAND = new ResourceLocation("mariculture", "textures/blocks/turbine_hand_texture.png");

    private final ModelAirPump pump = new ModelAirPump();
    private final ModelFLUDD fludd = new ModelFLUDD();

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

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItem() == Item.getItemFromBlock(Core.renderedMachines) && item.getItemDamage() == MachineRenderedMeta.AIR_PUMP) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(AIR_PUMP);
            pump.renderInventory(type);
        }

        if (Modules.isActive(Modules.factory)) {
            if (item.getItem() == Item.getItemFromBlock(Core.renderedMachines) && item.getItemDamage() == MachineRenderedMeta.FLUDD_STAND || item.getItem() == Factory.fludd) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(FLUDD);
                fludd.renderInventory(type);
            }
        }
    }
}
