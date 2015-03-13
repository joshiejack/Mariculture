package mariculture.api.fishery;

import mariculture.api.core.Environment.Salinity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

//Only ever called on hat pieces of armour
/** Guarantees the gender of a fish when this item is worn
 *  Helmet takes priority, boots lowest */
public interface IHelmetFishManipulator {
	/** Return 0 for male, or 1 for female **/
	public Integer getGender(ItemStack stack);

	/** Return the salt of this helmet **/
    public Salinity getSalt(ItemStack stack, Salinity salt);

    /** Return the temperature of this helmet **/
    public int getTemperature(ItemStack stack, int temperature);

    /** Whether we force a loot catch **/
    public boolean forceLoot(ItemStack stack);
    
    /** Whether we force a fish catch **/
    public boolean forceFish(ItemStack stack);

    /** The y height the fish think we're at **/
    public int getYHeight(ItemStack stack, int yHeight);

    /** The world the fish think they're in **/
    public World getWorld(ItemStack stack, World fakeWorld);

    /** Fish are always caught dead if this returns true **/
    public boolean alwaysDead(ItemStack stack);

    /** Gets the loot based on the loot ... **/
    public ItemStack getLoot(ItemStack helmet, ItemStack loot);
}
