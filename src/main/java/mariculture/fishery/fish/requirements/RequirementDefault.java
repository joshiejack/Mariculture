package mariculture.fishery.fish.requirements;

import mariculture.api.fishery.IMutationRequirement;
import net.minecraft.item.ItemStack;

public class RequirementDefault implements IMutationRequirement {
    public static final IMutationRequirement INSTANCE = new RequirementDefault();
    
    @Override
    public boolean canMutationOccur(ItemStack egg) {
        return true;
    }
}
