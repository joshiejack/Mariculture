package mariculture.fishery.gui;

import mariculture.core.gui.ContainerMachine;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.tile.TileFishTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFishTank extends ContainerMachine {
    public ContainerFishTank(TileFishTank tile, InventoryPlayer playerInventory) {
        super(tile);

        int i = (6 - 4) * 18;
        int j;
        int k;

        for (j = 0; j < 6; ++j) {
            for (k = 0; k < 9; ++k) {
                addSlotToContainer(new SlotFishTank(tile, tile.thePage * 54 + k + j * 9, 8 + k * 18, 16 + j * 18));
            }
        }

        bindPlayerInventory(playerInventory, 52);
    }

    @Override
    public int getSizeInventory() {
        return ((IInventory) tile).getSizeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ((IInventory) tile).isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        return null;

    }

    public class SlotFishTank extends Slot {
        public SlotFishTank(TileFishTank invent, int slot, int x, int y) {
            super(invent, slot, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof ItemFishy;
        }
    }
}
