package mariculture.api.fishery;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFishingRod {
	/** Handles the right clicking code for the fishing rods **/
	public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player, RodQuality quality, Random rand);

	/** adds the text for the bait types to the rods **/
	public void addBaitList(List list, RodQuality quality);
}
