package mariculture.core.gui;

import mariculture.core.items.ItemStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiStorage extends GuiMariculture {
    protected final InventoryStorage storage;

    public GuiStorage(ContainerStorage storage, String gui) {
        super(storage, gui);
        this.storage = storage.storage;
        ((ItemStorage) storage.storage.player.getCurrentEquippedItem().getItem()).addFeatures(features);
    }

    public GuiStorage(IInventory playerInv, InventoryStorage storage, World world, String gui, int offset) {
        super(new ContainerStorage(playerInv, storage, world, offset), gui, offset);
        this.storage = storage;
        ((ItemStorage) storage.player.getCurrentEquippedItem().getItem()).addFeatures(features);
    }

    @Override
    public String getName() {
        if (storage != null) {
            ItemStack stack = storage.player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() instanceof ItemStorage) {
                ItemStorage item = (ItemStorage) stack.getItem();
                return StatCollector.translateToLocal("item." + item.getName(stack) + ".name");
            }
        }

        return "";
    }

    @Override
    public int getX() {
        if (storage != null) {
            ItemStack stack = storage.player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() instanceof ItemStorage) {
                ItemStorage item = (ItemStorage) stack.getItem();
                return item.getX(stack);
            }
        }

        return 0;
    }
}
