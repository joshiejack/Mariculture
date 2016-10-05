package joshie.mariculture.modules.fishery.traits;

import joshie.mariculture.api.fishing.FishingTrait;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class TraitLuck extends FishingTrait {
    public TraitLuck() {
        super("luck");
    }

    @Override
    public float modifyLuck(EntityPlayer player, Random rand, float original) {
        return original * 1.5F;
    }
}
