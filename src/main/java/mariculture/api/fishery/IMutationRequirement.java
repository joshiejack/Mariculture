package mariculture.api.fishery;

import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IMutationRequirement {
    /** Returns true if all requirements for this mutation to occur have been met
     *  @param      the egg item**/
    public boolean canMutationOccur(ItemStack egg);
}
