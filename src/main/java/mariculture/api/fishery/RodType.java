package mariculture.api.fishery;

import java.util.LinkedList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


/** This class determines what quality of fishing rod you are using
 *  You can extend the class, for special RodTypes, For different ways of handling
 *  damage, or whether you can fish, it's essentially the handler for your fishing rods */
public class RodType {
	public static final RodType NET = new RodType(0, 0D, 0D, 0D, 0);
	public static final RodType DIRE = new RodType(10, 55D, 2.5D, 0D, 5);
	public static final RodType OLD = new RodType(25, 30D, 15D, 1D, 10);
	public static final RodType GOOD = new RodType(50, 20D, 20D, 2.5D, 20);
	public static final RodType SUPER = new RodType(75, 5D, 20D, 5D, 30);
	public static final RodType FLUX = new RodType(90, 1D, 20D, 7.5D, 15);
	
	/** The following are called in sequence, when attempting to catch loot, 
	 * JUNK, GOOD, RARE, UNIQUE, If all of these fail, the rod will catch fish loot
	 * If you set one to 0, this rodquality cannot catch that rarity of loot.
	 * If you were to set GOOD to 100, the chance to catch rare or fish loot will be 0, as you will
	 * always catch loot, based on the order it is called. */
	private final LinkedList<Double> chances;
	
	/** This variable is the ranking of this rod, it can go from any number to any number
	 * I'd recommend to keep the rank, between 0-100. It is what determines, which rod are better than others */
	private final int quality;
	
	/** This is the maximum level, that this quality of rod will attempt to enchant caught items to **/
	private final int enchantment;
	public RodType(int quality, double junk, double good, double rare, int enchantment) {
		this.quality = quality;
		this.chances = new LinkedList();
		this.chances.add(junk);
		this.chances.add(good);
		this.chances.add(rare);
		this.enchantment = enchantment;
	}
	
	public LinkedList<Double> getChances() {
		return this.chances;
	}

	public int getQuality() {
		return this.quality;
	}
	
	public int getLootEnchantmentChance() {
		return this.enchantment;
	}

	/** If you want certain fish to be caught alive by this rod quality
	 * you can create your own class, and override this method */
	public boolean caughtAlive(String species) {
		return false;
	}

	/** This is called by the on right click handler, to check whether this rod quality can fish in this location **/
	public boolean canFish(World world, int posX, int posY, int posZ, EntityPlayer player, ItemStack stack) {
		return true;
	}

	/** This is called to damage this RodType **/
	public ItemStack damage(World world, EntityPlayer player, ItemStack stack, int fish, Random rand) {
		if(player != null) stack.damageItem(fish, player);
		else {
			if(stack.attemptDamageItem(1, rand)) {
				stack = null;
			}
		}
		
		return stack;
	}
}
