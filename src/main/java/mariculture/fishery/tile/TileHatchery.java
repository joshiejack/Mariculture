package mariculture.fishery.tile;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IIncubator;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketParticle;
import mariculture.core.network.PacketParticle.Particle;
import mariculture.core.tile.base.TileStorage;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileHatchery extends TileStorage implements ISidedInventory, IIncubator {
    protected static final int MAX = MachineSpeeds.getHatcherySpeed();

    public static final int[] IN = new int[] { 0 };
    public static final int[] OUT = new int[] { 1, 2 };

    private boolean isInit = false;
    private boolean canWork;
    private int processed;

    public TileHatchery() {
        inventory = new ItemStack[3];
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void onInventoryChange(int slot) {
        updateCanWork();
        PacketHandler.syncInventory(this, getInventory());
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
        return side == ForgeDirection.DOWN.ordinal() && slot > 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    private void updateCanWork() {
        canWork = inventory[0] != null && Fishing.fishHelper.isEgg(inventory[0]) && (inventory[1] == null || inventory[2] == null);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (!isInit) {
                isInit = true;
                updateCanWork();
            }

            if (canWork) {
                if(onTick(20)) {
                    PacketHandler.sendAround(new PacketParticle(Particle.SPLASH, 8, xCoord, yCoord - 0.05, zCoord), this);
                }
                
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

    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public int getBirthChanceBoost() {
        return 0;
    }

    @Override
    public void eject(ItemStack fish) {
        if (inventory[1] == null) setInventorySlotContents(1, fish);
        else if (inventory[2] == null) setInventorySlotContents(2, fish);
        else SpawnItemHelper.spawnItem(worldObj, xCoord, yCoord + 1, zCoord, fish);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        canWork = nbt.getBoolean("CanWork");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("CanWork", canWork);
    }
}
