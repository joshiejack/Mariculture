package mariculture.plugins.enchiridion;

import java.util.List;

import joshie.enchiridion.api.EnchiridionAPI;
import joshie.enchiridion.api.IRecipeHandler;
import joshie.enchiridion.designer.recipe.RecipeHandlerBase;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.lib.util.Text;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeHandlerFishData extends RecipeHandlerBase {
    private String temp;
    private String salinity;
    private String lifespan;
    private String fertility;
    private String productivity;
    private String water;

    public RecipeHandlerFishData() {}

    public RecipeHandlerFishData(FishSpecies species) {
        this.temp = "Temp Range: " + (species.getTemperatureBase() - species.getTemperatureTolerance()) + Text.DEGREES + " to " + (species.getTemperatureBase() + species.getTemperatureTolerance() + Text.DEGREES);
        Salinity min = Salinity.values()[Math.max(0, species.getSalinityBase().ordinal() - species.getSalinityTolerance())];
        Salinity max = Salinity.values()[Math.min(2, species.getSalinityBase().ordinal() + species.getSalinityTolerance())];
        if (min == max) {
            this.salinity = "Salinity: " + min.name();
        } else this.salinity = "Salinity: " + min.name() + " to " + max.name();
        this.lifespan = "Lifespan: " + species.getLifeSpan() + " minutes";
        this.fertility = "Fertility: " + species.getFertility() + " eggs";
        this.productivity = "Productivity: " + species.getBaseProductivity() + "x";
        if (species.getWater1() != species.getWater2()) {
            this.water = "Water: " + species.getWaterRequired() + " " + new ItemStack(species.getWater1()).getDisplayName() + " or " + new ItemStack(species.getWater2()).getDisplayName();
        } else this.water = "Water: " + species.getWaterRequired() + " " + new ItemStack(species.getWater1()).getDisplayName();
        unique = species.getName();
    }

    @Override
    public void addRecipes(ItemStack output, List<IRecipeHandler> list) {
        FishSpecies theSpecies = Fishing.fishHelper.getSpecies(output);
        if (theSpecies == null && output.getItem() != Items.fish) return;
        if (theSpecies == null) theSpecies = Fishing.fishHelper.getSpecies(output.getItemDamage());
        for (FishSpecies species : FishSpecies.species.values()) {
            if (species == theSpecies) {
                list.add(new RecipeHandlerFishData(species));
                break;
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "MaricultureFishData";
    }

    @Override
    public double getHeight(double width) {
        return width / 2.5D;
    }

    @Override
    public double getWidth(double width) {
        return width;
    }

    @Override
    public float getSize(double width) {
        return (float) (width / 110D);
    }

    protected void drawBackground() {
        EnchiridionAPI.draw.drawText(temp, 0D, 0D, 0xFF333333, 0.9F);
        EnchiridionAPI.draw.drawText(salinity, 0D, 20D, 0xFF333333, 0.9F);
        EnchiridionAPI.draw.drawText(lifespan, 0D, 40D, 0xFF333333, 0.9F);
        EnchiridionAPI.draw.drawText(fertility, 0D, 60D, 0xFF333333, 0.9F);
        EnchiridionAPI.draw.drawText(productivity, 0D, 80D, 0xFF333333, 0.9F);
        EnchiridionAPI.draw.drawText(water, 0D, 100D, 0xFF333333, 0.9F);
    }
}
