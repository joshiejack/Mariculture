package mariculture.fishery.fish.requirements;

import mariculture.api.fishery.IMutationRequirement;
import net.minecraft.item.ItemStack;

public class RequirementHasTag implements IMutationRequirement {
    private final String[] keys;
    
    public RequirementHasTag(String[] keys) {
        this.keys = keys;
    }
    
    public RequirementHasTag(String key) {
        this.keys = new String[] { key };
    }
    
    @Override
    public boolean canMutationOccur(ItemStack egg) {
        for (String key: keys) {
            if (!egg.getTagCompound().hasKey(key)) return false;
        }
        
        return true;
    }
}
