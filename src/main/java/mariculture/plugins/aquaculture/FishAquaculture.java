package mariculture.plugins.aquaculture;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import rebelkeithy.mods.aquaculture.items.AquacultureItems;

public class FishAquaculture extends FishSpecies {
    private final int meta;

    public FishAquaculture(int meta) {
        this.meta = meta;
    }

    @Override
    public ItemStack getRawForm(int stackSize) {
        return new ItemStack(AquacultureItems.fish, stackSize, meta);
    }

    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 5, 15 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { Salinity.FRESH };
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 0;
    }

    @Override
    public int getFertility() {
        return 0;
    }

    @Override
    public void addFishProducts() {

    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public IIcon getIcon(int gender) {
        return getRawForm(1).getIconIndex();
    }
}
