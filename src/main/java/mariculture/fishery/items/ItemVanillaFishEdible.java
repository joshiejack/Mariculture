package mariculture.fishery.items;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Modules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;
import squeek.applecore.api.food.ItemFoodProxy;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "squeek.applecore.api.food.IEdible", modid = "AppleCore")
public class ItemVanillaFishEdible implements IEdible {
    public static ItemVanillaFishEdible INSTANCE = new ItemVanillaFishEdible();
    
    @Optional.Method(modid = "AppleCore")
    @Override
    public FoodValues getFoodValues(ItemStack stack) {
        if(!Modules.isActive(Modules.fishery)) return new FoodValues(2, 1F); //If fishery is disabled
        FishSpecies fish = Fishing.fishHelper.getSpecies(stack.getItemDamage());
        if (fish != null) {
            return new FoodValues(fish.getFoodStat(), fish.getFoodSaturation());
        } else return new FoodValues(2, 1F);
    }

    @Optional.Method(modid = "AppleCore")
    public void onEatenCompatibility(ItemStack stack, EntityPlayer player) {
        player.getFoodStats().func_151686_a(new ItemFoodProxy(this), stack);
    }
}
