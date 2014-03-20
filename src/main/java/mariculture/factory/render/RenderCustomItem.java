package mariculture.factory.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderCustomItem implements IItemRenderer {
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
		//TODO: REDO Custom Block Item Rendering
		/*
		if(item.hasTagCompound()) {
			if(item.stackTagCompound.getIntArray("BlockIDs").length != 6) {
				return;
			} else {
				for(int i = 0; i <= 5; i++) {
					if(Blocks.blocksList[item.stackTagCompound.getIntArray("BlockIDs")[i]] == null) {
						return;
					}
				}
			}
		}
		
		RenderBlocksCustom renderBlocks = new RenderBlocksCustom();
		switch (type) {
		case ENTITY:
			renderBlocks.renderBlockAsItem(Blocks.blocksList[item.itemID], item.getItemDamage(), 1.0F, item);
			renderBlocks.clearOverrideBlockTexture();
			break;
		case EQUIPPED:
			GL11.glPushMatrix();
			GL11.glTranslatef(0.75F, 0.0F, 0.0F);
			renderBlocks.renderBlockAsItem(Blocks.blocksList[item.itemID], item.getItemDamage(), 1.0F, item);
			GL11.glPopMatrix();
			renderBlocks.clearOverrideBlockTexture();
			break;
		case EQUIPPED_FIRST_PERSON:
			renderBlocks.renderBlockAsItem(Blocks.blocksList[item.itemID], item.getItemDamage(), 1.0F, item);
			renderBlocks.clearOverrideBlockTexture();
			break;
		case INVENTORY:
			renderBlocks.renderBlockAsItem(Blocks.blocksList[item.itemID], item.getItemDamage(), 1.0F, item);
			renderBlocks.clearOverrideBlockTexture();
			break;
		default:
			break;
		} */
	}
}