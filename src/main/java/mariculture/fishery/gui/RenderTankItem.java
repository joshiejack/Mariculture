package mariculture.fishery.gui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import mariculture.fishery.Fishery;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

public class RenderTankItem extends RenderItem {
    @Override
    public void renderItemOverlayIntoGUI(FontRenderer renderer, TextureManager tm, ItemStack stack, int x, int y, String string) {
        if (stack != null && stack.getItem() == Fishery.fishy) {

            String value = string;
            if (stack != null) {
                value = "" + stack.stackSize;
                if (stack.stackSize >= 1000) {
                    double d = stack.stackSize / 1000D;
                    DecimalFormat df = new DecimalFormat("#");
                    df.setRoundingMode(RoundingMode.DOWN);
                    value = "" + df.format(d) + "k";
                }
            }

            super.renderItemOverlayIntoGUI(renderer, tm, stack, x, y, value);
        } else super.renderItemOverlayIntoGUI(renderer, tm, stack, x, y, string);
    }

    @Override
    public void renderItemIntoGUI(FontRenderer p_77015_1_, TextureManager p_77015_2_, ItemStack p_77015_3_, int p_77015_4_, int p_77015_5_, boolean renderEffect) {
        super.renderItemIntoGUI(p_77015_1_, p_77015_2_, p_77015_3_, p_77015_4_, p_77015_5_, renderEffect);
    }
}
