package mariculture.plugins.tconstruct;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import tconstruct.library.tools.ToolCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TiCEvents {
	@SubscribeEvent
	public void onBreaking(BreakSpeed event) {
		Block block = event.block;
		EntityPlayer player = event.entityPlayer;
		if(player.getCurrentEquippedItem() != null) {
			if (player.isInsideOfMaterial(Material.water)) {
				if(player.getCurrentEquippedItem().getItem() instanceof ToolCore) {
					ToolCore toolCore = (ToolCore) player.getCurrentEquippedItem().getItem();
					if(toolCore.canHarvestBlock(event.block, player.getCurrentEquippedItem())) {						
						ItemStack tool = player.getCurrentEquippedItem();
						if(tool.hasTagCompound()) {
							NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
							 if (tags.hasKey("Pearls")) {
								float speed = tags.getIntArray("Pearls")[0] / 50;
								event.newSpeed = event.originalSpeed + speed;
							}
						}
					}
				}
			}
		}
	}
}
