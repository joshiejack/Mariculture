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
    public static final RodType DIRE = new RodType(10, 45D, 0D, 0D, 5);
    public static final RodType OLD = new RodType(25, 27.5D, 5.5D, 0D, 10);
    public static final RodType GOOD = new RodType(50, 10D, 17.5D, 1D, 20);
    public static final RodType SUPER = new RodType(75, 1D, 15.5D, 5D, 30);
    public static final RodType FLUX = new RodTypeFlux(90, 0D, 13D, 7.5D, 15);

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
        chances = new LinkedList();
        chances.add(junk);
        chances.add(good);
        chances.add(rare);
        this.enchantment = enchantment;
    }

    public LinkedList<Double> getChances() {
        return chances;
    }

    public int getQuality() {
        return quality;
    }

    public int getLootEnchantmentChance() {
        return enchantment;
    }

    /** This is how much damage this fishing rod types bobber does **/
    public float getDamage() {
        return 0.0F;
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
        if (player != null) {
            stack.damageItem(fish, player);
        } else if (stack.attemptDamageItem(1, rand)) {
            stack = null;
        }

        return stack;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RodType)) return false;
        else return ((RodType) o).quality == quality;
    }

    public static class RodTypeFlux extends RodType {
        public RodTypeFlux(int quality, double junk, double good, double rare, int enchantment) {
            super(quality, junk, good, rare, enchantment);
        }

        @Override
        public boolean canFish(World world, int posX, int posY, int posZ, EntityPlayer player, ItemStack stack) {
            if (stack.hasTagCompound()) return stack.stackTagCompound.getInteger("Energy") >= 100;
            else return false;
        }

        @Override
        public ItemStack damage(World world, EntityPlayer player, ItemStack stack, int fish, Random rand) {
            if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey("Energy")) return stack;

            if (stack.stackTagCompound.getInteger("Energy") <= 0) return stack;

            int energy = stack.stackTagCompound.getInteger("Energy");
            int energyExtracted = Math.min(energy, 100);
            energy -= energyExtracted;
            stack.stackTagCompound.setInteger("Energy", energy);

            return stack;
        }
    }
}
