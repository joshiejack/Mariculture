package joshie.maritech.tile;

import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishDNABase;
import joshie.mariculture.core.gui.feature.FeatureEject.EjectSetting;
import joshie.mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import joshie.mariculture.core.lib.MachineSpeeds;
import joshie.mariculture.core.util.Fluids;
import joshie.maritech.items.ItemFishDNA;
import joshie.maritech.tile.base.TileTankPowered;
import net.minecraft.item.ItemStack;

public class TileExtractor extends TileTankPowered {
    public static final int FISH = 6;
    
    public TileExtractor() {
        inventory = new ItemStack[13];
        max = MachineSpeeds.getDNAMachineSpeed();
        output = new int[] { 7, 8, 9, 10, 11, 12 };
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 3, 4, 6, 7, 8, 9, 10, 11, 12 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public int getTankCapacity(int size) {
        return (1 + size) * 2500;
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    @Override
    public int getRFCapacity() {
        return (1 + rf) * 100000;
    }

    private boolean hasFish() {
        return inventory[FISH] != null && Fishing.fishHelper.isPure(inventory[FISH]);
    }

    private boolean hasFishOil() {
        return tank.getFluid() != null && tank.getFluid().getFluid() == Fluids.getFluid("fish_oil") && tank.getFluidAmount() >= 2500;
    }

    private boolean hasPower() {
        return energyStorage.getEnergyStored() > 10000;
    }

    @Override
    public boolean canWork() {
        return RedstoneMode.canWork(this, mode) && hasFish() && hasPower() && hasFishOil() && hasRoom(null);
    }

    @Override
    public void process() {
        tank.drain(2500, true);
        //Loop through the fishies dna
        for (FishDNABase dna : FishDNABase.DNAParts) {
            int higher = dna.getDNA(inventory[FISH]);
            int lower = dna.getDNA(inventory[FISH]);
            if (higher == lower) {
                if (worldObj.rand.nextInt(Math.max(1, dna.getCopyChance())) == 0) {
                    helper.insertStack(ItemFishDNA.create(dna, higher), output);
                    break;
                }
            }
        }
    }
}
