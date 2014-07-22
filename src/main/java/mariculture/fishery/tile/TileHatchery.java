package mariculture.fishery.tile;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IIncubator;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.tile.base.TileStorage;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileHatchery extends TileStorage implements ISidedInventory, IIncubator {
    protected static final int MAX = MachineSpeeds.getHatcherySpeed();

    public static final int[] IN = new int[] { 0 };
    public static final int[] OUT = new int[] { 1 };

    private boolean isInit = false;
    private boolean canWork;
    private int processed;

    public TileHatchery() {
        inventory = new ItemStack[2];
    }

    @Override
    public void onInventoryChange(int slot) {
        updateCanWork();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side != ForgeDirection.DOWN.ordinal() ? IN : OUT;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return inventory[0] == null && stack.stackSize == 1 && side != ForgeDirection.DOWN.ordinal();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side == ForgeDirection.DOWN.ordinal();
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    private void updateCanWork() {
        canWork = Fishing.fishHelper.isEgg(inventory[0]) && inventory[1] == null;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (!isInit) {
                isInit = true;
                updateCanWork();
            }

            if (canWork) {
                processed++;
                if (processed >= MAX) {
                    inventory[0] = Fishing.fishHelper.attemptToHatchEgg(inventory[0], worldObj.rand, 1.0D, this);
                    updateCanWork();
                    processed = 0;
                }
            } else {
                processed = 0;
            }
        }
    }

    @Override
    public int getBirthChanceBoost() {
        return 0;
    }

    @Override
    public void eject(ItemStack fish) {
        setInventorySlotContents(1, fish);
    }
}
