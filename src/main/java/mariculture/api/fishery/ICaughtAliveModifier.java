package mariculture.api.fishery;

import net.minecraft.item.ItemStack;

//Implement this on armor that should increase the caught alive chance
public interface ICaughtAliveModifier {
    @Deprecated
    public double getModifier();
    
    public double getModifier(ItemStack stack);
}
