package joshie.mariculture.factory.render;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderCustomItem implements IItemRenderer {
    private static final RenderBlocksCustom renderBlocks = new RenderBlocksCustom();

    @Override
    public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
        switch (type) {
            case ENTITY:
                return true;
            case EQUIPPED:
                return true;
            case EQUIPPED_FIRST_PERSON:
                return true;
            case INVENTORY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY:
                renderBlocks.renderBlockAsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage(), 1.0F, item);
                renderBlocks.clearOverrideBlockTexture();
                break;
            case EQUIPPED:
                GL11.glPushMatrix();
                GL11.glTranslatef(0.75F, 0.0F, 0.0F);
                GL11.glScalef(0.85F, 0.85F, 0.85F);
                renderBlocks.renderBlockAsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage(), 1.0F, item);
                GL11.glPopMatrix();
                renderBlocks.clearOverrideBlockTexture();
                break;
            case EQUIPPED_FIRST_PERSON:
                renderBlocks.renderBlockAsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage(), 1.0F, item);
                renderBlocks.clearOverrideBlockTexture();
                break;
            case INVENTORY:
                renderBlocks.renderBlockAsItem(Block.getBlockFromItem(item.getItem()), item.getItemDamage(), 1.0F, item);
                renderBlocks.clearOverrideBlockTexture();
                break;
            default:
                break;
        }
    }
}