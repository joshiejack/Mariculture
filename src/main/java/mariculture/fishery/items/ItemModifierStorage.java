package mariculture.fishery.items;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.IMutationEffect;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.items.ItemMCStorage;
import mariculture.core.util.MCTranslate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ItemModifierStorage extends ItemMCStorage implements IItemUpgrade {
    private static final int SIZE = 5;

    public ItemModifierStorage() {
        super(SIZE, "modifierStorage");
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
                return new SlotMutation(storage, i, 62, 26);
            case 1:
                return new SlotMutation(storage, i, 80, 26);
            case 2:
                return new SlotMutation(storage, i, 98, 26);
            case 3:
                return new SlotMutation(storage, i, 71, 44);
            case 4:
                return new SlotMutation(storage, i, 89, 44);
        }

        return new Slot(storage, i, 100, 100);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack.getItem() instanceof IItemUpgrade && !(stack.getItem() instanceof ItemModifierStorage)) return getTemperature(stack) != 0;
        else return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add(mariculture.lib.util.Text.DARK_GREEN + MCTranslate.translate("temp.control"));
        if (stack.hasTagCompound()) {
            int temp = getTemperature(stack);
            if (temp != 0) {
                if (temp > 0) {
                    list.add(mariculture.lib.util.Text.ORANGE + "+" + temp + mariculture.lib.util.Text.DEGREES);
                }
                if (temp < 0) {
                    list.add(mariculture.lib.util.Text.INDIGO + temp + mariculture.lib.util.Text.DEGREES);
                }
            }
        }
    }

    @Override
    public List<IMutationEffect> getMutationEffects(ItemStack stack) {
        return new ArrayList();
    }

    @Override
    public int getTemperature(ItemStack stack) {
        return 0;
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

    private static class SlotMutation extends Slot {
        private SlotMutation(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            if (stack.getItem() instanceof IItemUpgrade) {
                List<IMutationEffect> effects = ((IItemUpgrade)stack.getItem()).getMutationEffects(stack);
                return effects.size() > 0;
            } else return false;
        }
    }
}
