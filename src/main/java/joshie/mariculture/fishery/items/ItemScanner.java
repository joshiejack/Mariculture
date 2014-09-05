package joshie.mariculture.fishery.items;

import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.gui.InventoryStorage;
import joshie.mariculture.core.items.ItemMCStorage;
import joshie.mariculture.fishery.gui.ContainerScanner;
import joshie.mariculture.fishery.gui.GuiScanner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ItemScanner extends ItemMCStorage {
    public ItemScanner() {
        super(1, "scanner");
        setCreativeTab(MaricultureTab.tabFishery);
    }

    @Override
    public Slot getSlot(InventoryStorage storage, int i) {
        switch (i) {
            case 0:
                return new Slot(storage, i, 179, 14);
        }

        return new Slot(storage, i, 100, 100);
    }

    @Override
    public Object getGUIContainer(EntityPlayer player) {
        return new ContainerScanner(player.inventory, new InventoryStorage(player, size), player.worldObj);
    }

    @Override
    public Object getGUIElement(EntityPlayer player) {
        return new GuiScanner(player.inventory, new InventoryStorage(player, size), player.worldObj, gui);
    }
}
