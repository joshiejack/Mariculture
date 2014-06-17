package mariculture.fishery.items;

import java.util.List;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureTab;
import mariculture.api.util.Text;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.items.ItemStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ItemTemperatureControl extends ItemStorage implements IItemUpgrade {
    private static final int SIZE = 5;

    public ItemTemperatureControl() {
        super(SIZE, "tempControl");
        setCreativeTab(MaricultureTab.tabFishery);
    }

    @Override
    public int getX(ItemStack stack) {
        return 30;
    }

    @Override
    public Slot getSlot(InventoryStorage storage, int i) {
        switch (i) {
            case 0:
                return new SlotHeating(storage, i, 62, 26);
            case 1:
                return new SlotHeating(storage, i, 80, 26);
            case 2:
                return new SlotHeating(storage, i, 98, 26);
            case 3:
                return new SlotHeating(storage, i, 71, 44);
            case 4:
                return new SlotHeating(storage, i, 89, 44);
        }

        return new Slot(storage, i, 100, 100);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack.getItem() instanceof IItemUpgrade && !(stack.getItem() instanceof ItemTemperatureControl)) return getTemperature(stack) != 0;
        else return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add(Text.DARK_GREEN + Text.translate("temp.control"));
        if (stack.hasTagCompound()) {
            int temp = getTemperature(stack);
            if (temp != 0) {
                if (temp > 0) {
                    list.add(Text.ORANGE + "+" + temp + Text.DEGREES);
                }
                if (temp < 0) {
                    list.add(Text.INDIGO + temp + Text.DEGREES);
                }
            }
        }
    }

    @Override
    public int getTemperature(ItemStack stack) {
        int temperature = 0;
        ItemStack[] invent = load(null, stack, SIZE);
        for (ItemStack item : invent)
            if (item != null) if (item.getItem() instanceof IItemUpgrade) {
                temperature += ((IItemUpgrade) item.getItem()).getTemperature(item);
            }

        return temperature;
    }

    @Override
    public int getStorageCount(ItemStack stack) {
        return 0;
    }

    @Override
    public int getPurity(ItemStack stack) {
        return 0;
    }

    @Override
    public int getSpeed(ItemStack stack) {
        return 0;
    }

    @Override
    public int getRFBoost(ItemStack stack) {
        return 0;
    }

    @Override
    public int getSalinity(ItemStack stack) {
        return 0;
    }

    @Override
    public String getType(ItemStack stack) {
        return "control";
    }

    private static class SlotHeating extends Slot {
        private SlotHeating(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            if (stack.getItem() instanceof IItemUpgrade && !(stack.getItem() instanceof ItemTemperatureControl)) return ((IItemUpgrade) stack.getItem()).getTemperature(stack) != 0;
            else return false;
        }
    }
}
