package joshie.mariculture.modules.fishery.traits;

import joshie.mariculture.api.fishing.FishingTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class TraitWood extends FishingTrait {
    public TraitWood() {
        super("wood");
    }

    @Override
    public int modifyXP(EntityPlayer player, ItemStack loot, Random rand, int original) {
        return (int)(original * 1.5D);
    }
}
