package mariculture.api.fishery;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFishingRod {
	/**
	 * Helper for adding your own EnumRodQuality
	 * @param Name of rod quality
	 * @param Maximum usage until it breaks  **/
	public EnumRodQuality addRodQuality(String name, int maxUses);
	
	/** Handles the right clicking code for the fishing rods, call this from your fishing rods **/
	public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player);
	
	/** Returns the quality that this rod is considered **/
	public EnumRodQuality getRodQuality(ItemStack stack);
	
	/** Register a rod as a certain rod quality **/
	public void registerRod(Item item, EnumRodQuality quality);
	
	/**
	 * @param Itemstack to compare
	 * @param to  compare
	 * @return returns true if this quality rod can use the current bait  */
	public boolean canUseBait(ItemStack stack, EnumRodQuality quality);
}
