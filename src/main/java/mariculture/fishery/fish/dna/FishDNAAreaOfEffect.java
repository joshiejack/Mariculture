package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics.FussyFish;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class FishDNAAreaOfEffect extends FishDNA {
    public ForgeDirection dir;

    public FishDNAAreaOfEffect(ForgeDirection dir) {
        this.dir = dir;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName().substring(7) + dir.name();
    }

    @Override
    public String getDNAName(ItemStack stack) {
        int type = stack.getTagCompound().getInteger(getHigherString());
        for (FishDNA dna : types)
            if (type >= dna.minimum && type <= dna.maximum) return mariculture.lib.util.Text.localize("mariculture.fish.data.aoe." + dna.name);

        return "";
    }

    @Override
    public String getLowerDNAName(ItemStack stack) {
        int type = stack.getTagCompound().getInteger(getLowerString());
        for (FishDNA dna : types)
            if (type >= dna.minimum && type <= dna.maximum) return mariculture.lib.util.Text.localize("mariculture.fish.data.aoe." + dna.name);

        return "";
    }

    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getAreaOfEffectBonus(dir);
    }
    
    @Override
    public int getCopyChance() {
        return 25;
    }
    public Integer sanitize(Integer i) {
        return Math.min(32, Math.max(-4, i));
    }

    @Override
    public Integer getDNA(ItemStack stack) {
        return sanitize(super.getDNA(stack) + FussyFish.RANGE_BOOSTER);
    }

    @Override
    public Integer getLowerDNA(ItemStack stack) {
        return sanitize(super.getLowerDNA(stack) + FussyFish.RANGE_BOOSTER);
    }
}
