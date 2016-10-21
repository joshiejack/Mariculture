package joshie.mariculture.modules.aquaculture.tile;

import joshie.mariculture.core.util.capabilities.SingleStackHandler;
import joshie.mariculture.core.util.tile.TileInventory;
import joshie.mariculture.modules.aquaculture.AquacultureAPI;
import net.minecraft.item.ItemStack;

public class TileOyster extends TileInventory {
    private final SingleStackHandler handler = new SingleStackHandler(this, 1) {
        @Override
        public boolean isValidForInsertion(int slot, ItemStack stack) {
            return AquacultureAPI.INSTANCE.isSand(stack);
        }

        @Override
        public boolean isValidForExtraction(int slot, ItemStack stack) {
            return !AquacultureAPI.INSTANCE.isSand(stack);
        }
    };

    @Override
    protected SingleStackHandler getInventory() {
        return handler;
    }
}
