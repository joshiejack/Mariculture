package mariculture.fishery.fish.requirements;

import mariculture.api.fishery.IMutationRequirement;
import net.minecraft.item.ItemStack;

public class RequirementHasTagCount implements IMutationRequirement {
    private final String[] keys;
    private final int[] values;
    
    public RequirementHasTagCount(String[] keys, int[] values) {
        this.keys = keys;
        this.values = values;
    }
    
    public RequirementHasTagCount(String key, int value) {
        this.keys = new String[] { key };
        this.values = new int[] { value };
    }
    
    @Override
    public boolean canMutationOccur(ItemStack egg) {
        for (int i = 0; i < keys.length; i++) {
            if (egg.getTagCompound().getInteger(keys[i]) < values[i]) return false;
        }
        
        return true;
    }

    @Override
    public String getMutationInfo() {
        return "Requires " + values[0] + " Block of " + keys[0] + " in the water.";
    }
}
