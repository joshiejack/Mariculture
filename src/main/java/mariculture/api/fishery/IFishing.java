package mariculture.api.fishery;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFishing {
	public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player);
	public RodType getRodType(ItemStack stack);
	public void registerRod(Item item, RodType quality);
	public boolean canUseBait(ItemStack rod, ItemStack bait);
	public ArrayList<List> getCanUseList(RodType quality);
	public void addBaitForQuality(ItemStack bait, RodType quality);
	public void addBaitForQuality(ItemStack bait, List<RodType> rods);
	public void addBait(ItemStack bait, Integer catchRate);
	public int getBaitQuality(ItemStack bait);
	public void addLoot(Loot loot);
	
	/** Returns a catch for the location, You need to pass in the RodQuality **/
	public ItemStack getCatch(World world, int x, int y, int z, ItemStack stack);
}
