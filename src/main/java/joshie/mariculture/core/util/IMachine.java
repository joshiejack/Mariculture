package joshie.mariculture.core.util;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public interface IMachine extends IHasGUI {
    public void setGUIData(int id, int value);

    public ArrayList<Integer> getGUIData();

    public ItemStack[] getInventory();
}
