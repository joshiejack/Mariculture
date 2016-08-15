package joshie.mariculture.modules.aquaculture.tile;

import joshie.mariculture.core.util.SingleStackHandler;
import joshie.mariculture.core.util.TileInventory;
import joshie.mariculture.modules.aquaculture.AquacultureAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileOyster extends TileInventory {
    private final SingleStackHandler handler = new SingleStackHandler(this, 1) {
        @Override
        public boolean isValidForInsertion(int slot, ItemStack stack) {
            return AquacultureAPI.isSand(stack);
        }

        @Override
        public boolean isValidForExtraction(int slot, ItemStack stack) {
            return !AquacultureAPI.isSand(stack);
        }
    };

    @Override
    protected SingleStackHandler getInventory() {
        return handler;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (facing != null && capability == ITEM_HANDLER_CAPABILITY)
            return (T) handler;
        return super.getCapability(capability, facing);
    }
}
