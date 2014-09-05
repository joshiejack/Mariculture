package joshie.maritech.render;

import joshie.lib.helpers.ClientHelper;
import joshie.maritech.lib.MTModInfo;
import joshie.maritech.model.ModelFLUDD;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderFLUDDItem implements IItemRenderer {
    private static final ResourceLocation FLUDD = new ResourceLocation(MTModInfo.MODPATH, "textures/blocks/fludd_texture.png");
    private final ModelFLUDD fludd = new ModelFLUDD();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        ClientHelper.bindTexture(FLUDD);
        fludd.renderInventory(type);
    }
}
