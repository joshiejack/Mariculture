package mariculture.fishery.gui;

import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.InventoryStorage;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class ContainerScanner extends ContainerStorage {
	public ContainerScanner(IInventory inventory, InventoryStorage storage, World world) {
		super(inventory, storage, world, 30);
	}
}
