package enchiridion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import enchiridion.api.GuideHandler;

public class CommonProxy implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		ItemStack stack = player.getCurrentEquippedItem();
		if(ItemEnchiridion.isBookBinder(stack)) {
			return new ContainerBinder(player.inventory, new InventoryBinder(player));
		} else return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		ItemStack stack = player.getCurrentEquippedItem();
		if(ItemEnchiridion.isBookBinder(stack)) {
			return new GuiBinder(player.inventory, new InventoryBinder(player));
		} else return (player.getCurrentEquippedItem() != null)? GuideHandler.getGui(player.getCurrentEquippedItem()): null;
	}

	public void preInit() {}
	public void init() {}
	public void postInit(){}
}