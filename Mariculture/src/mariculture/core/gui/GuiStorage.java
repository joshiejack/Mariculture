package mariculture.core.gui;

import java.util.Random;

import mariculture.core.items.ItemStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class GuiStorage extends GuiMariculture {
	private final InventoryStorage storage;
	private final Random rand = new Random();

	public GuiStorage(IInventory playerInv, InventoryStorage storage, World world, String gui) {
		super(new ContainerStorage(playerInv, storage, world), gui);
		this.storage = storage;
	}

	@Override
	public void drawForeground() {
		if(storage != null && storage.player.getCurrentEquippedItem() != null 
				&& storage.player.getCurrentEquippedItem().getItem() instanceof ItemStorage) {
			ItemStorage item = (ItemStorage) storage.player.getCurrentEquippedItem().getItem();
			item.draw(fontRenderer, storage.player.getCurrentEquippedItem());
		}
	}

	@Override
	public void drawBackground(int x, int y) {
		
	}
}
