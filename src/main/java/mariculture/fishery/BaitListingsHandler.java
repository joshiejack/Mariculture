package mariculture.fishery;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.core.util.Text;
import mariculture.fishery.FishingRodHandler.FishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

//Adds the information about the baits rods can use, if they are registered
public class BaitListingsHandler {
	@SubscribeEvent
	public void addToolTip(ItemTooltipEvent event) {
		ItemStack stack = event.itemStack;
		List list = event.toolTip;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			EnumRodQuality quality = Fishing.rodHandler.getRodQuality(stack);
			if(quality != null) {
				list.add(Text.INDIGO + StatCollector.translateToLocal("mariculture.string.bait"));
				
				ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) FishingRodHandler.getCanUseList().clone();
				for (FishingRod loot : lootTmp) {
					if (loot.enumQuality == quality) {
						list.add(loot.itemStack.getItem().getItemStackDisplayName(loot.itemStack));
					}
				}
			}
		} else if(Fishing.rodHandler.getRodQuality(stack) != null) {
			list.add(Text.INDIGO + StatCollector.translateToLocal("mariculture.string.shift.hold") + " " +
					Text.WHITE + StatCollector.translateToLocal("mariculture.string.shift.shift") + " " + 
					Text.INDIGO + StatCollector.translateToLocal("mariculture.string.shift.rod"));
		}
	}
}
