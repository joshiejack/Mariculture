package joshie.mariculture.modules.fishery.item;

import joshie.mariculture.modules.fishery.item.ItemBait.Bait;
import joshie.mariculture.core.util.item.ItemFoodMC;

public class ItemBait extends ItemFoodMC<Bait> {
    public enum Bait {
        ANT(1, 0.01F);

        private final int heal;
        private final float saturation;

        Bait(int heal, float saturation) {
            this.heal = heal;
            this.saturation = saturation;
        }
    }

    public ItemBait() {
        super(Bait.class);
    }

    @Override
    public int getHealAmount(Bait bait) {
        return bait.heal;
    }

    @Override
    public float getSaturationModifier(Bait bait) {
        return bait.saturation;
    }
}
