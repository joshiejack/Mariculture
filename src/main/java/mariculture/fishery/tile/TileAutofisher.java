package mariculture.fishery.tile;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodType;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.tile.base.TileMachinePowered;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileAutofisher extends TileMachinePowered implements IHasNotification {
    //Slot Helper variables
    public static final int rod = 4;
    private static final int[] rod_slot = new int[] { 4 };
    private static final int[] bait = new int[] { 5, 6, 7, 8, 9, 10 };
    private static final int[] all = new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };

    private int baitChance;

    public TileAutofisher() {
        max = MachineSpeeds.getAutofisherSpeed();
        inventory = new ItemStack[20];
        setting = EjectSetting.ITEM;
        output = new int[] { 11, 12, 13, 14, 15, 16, 17, 18, 19 };
    }

    @Override
    public int getRFCapacity() {
        return 20000;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side <= 1 ? rod_slot : all;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (slot == rod) return Fishing.fishing.getRodType(stack) != null;
        else if (slot >= 5 && slot <= 10) return Fishing.fishing.getBaitQuality(stack) > 0;
        else return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 4 || slot > 10;
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    @Override
    public void process() {
        int speed = 1 + EnchantHelper.getLevel(Enchantment.field_151369_A, inventory[rod]);
        for (int i = 0; i < speed && canWork; i++) {
            int baitSlot = destroyBait();
            int bonusQuality = getBait() + EnchantHelper.getLevel(Enchantment.field_151370_z, inventory[rod]) * 4;
            if (Rand.rand.nextInt(100) < bonusQuality && baitSlot >= 0) {
                RodType type = Fishing.fishing.getRodType(inventory[rod]);
                setInventorySlotContents(rod, type.damage(worldObj, null, inventory[rod], 0, Rand.rand));
                ItemStack lootResult = Fishing.fishing.getCatch(worldObj, xCoord, yCoord - 1, zCoord, null, inventory[rod]);
                if (lootResult != null) {
                    decrStackSize(destroyBait(), 1);
                    helper.insertStack(lootResult, output);
                }
            }
        }
    }

    private int destroyBait() {
        for (int i : bait)
            if (inventory[i] != null) {
                int quality = Fishing.fishing.getBaitQuality(inventory[i]);
                if (i > 0 && Fishing.fishing.canUseBait(inventory[rod], inventory[i])) {
                    return i;
                }
            }

        return -1;
    }

    //Returns how much RF this machine uses
    @Override
    public void updatePowerPerTick() {
        if (rf <= 300000) {
            double modifier = 1D - (rf / 300000D) * 0.75D;
            usage = (int) (modifier * (20 + (speed * 20)));
        } else usage = 1;
    }

    @Override
    public boolean canWork() {
        return RedstoneMode.canWork(this, mode) && hasRod() && getBait() > 0 && hasPower() && isFishable() && hasRoom(null);
    }

    //Checks whether the item in the rod slot is a fishing rod, and if so, whether it can fish at the coordinates
    private boolean hasRod() {
        if (inventory[rod] != null) {
            RodType type = Fishing.fishing.getRodType(inventory[rod]);
            return type != null ? type.canFish(worldObj, xCoord, yCoord - 1, zCoord, null, inventory[rod]) : false;
        } else return false;
    }

    //Return whether the autofisher has bait available to be used
    private int getBait() {
        for (int i : bait)
            if (inventory[i] != null) {
                int quality = Fishing.fishing.getBaitQuality(inventory[i]);
                if (i > 0 && Fishing.fishing.canUseBait(inventory[rod], inventory[i])) return quality;
            }

        return 0;
    }

    //Returns whether the rod can operate or not, depends on the bait and the rod
    private boolean hasPower() {
        return energyStorage.extractEnergy(getPowerPerTick(), true) >= getPowerPerTick();
    }

    //Returns whether the block below can be fished in or not
    private boolean isFishable() {
        return BlockHelper.isFishable(worldObj, xCoord, yCoord - 1, zCoord);
    }

    @Override
    public boolean isNotificationVisible(NotificationType type) {
        switch (type) {
            case NO_ROD:
                return !hasRod();
            case NO_BAIT:
                return hasRod() && getBait() <= 0;
            case NOT_FISHABLE:
                return !isFishable();
            case NO_RF:
                return !hasPower();
            default:
                return false;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        baitChance = nbt.getInteger("BaitQuality");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("BaitQuality", baitChance);
    }
}
