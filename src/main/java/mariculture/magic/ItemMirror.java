package mariculture.magic;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.MirrorHelper;
import mariculture.core.items.ItemStorage;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.Jewelry;
import mariculture.core.util.Rand;
import mariculture.magic.gui.ContainerMirror;
import mariculture.magic.gui.GuiMirror;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMirror extends ItemStorage {
	public final String name;
	
	public ItemMirror(int id, String img) {
		super(id, 4, "mirror");
		setMaxDamage(30);
		name = img;
		setCreativeTab(MaricultureTab.tabJewelry);
	}
	
	@Override
	public Slot getSlot(InventoryStorage storage, int i) {
		switch(i) {
			case 0: return new SlotJewelry(storage, i, 8, 10, Jewelry.RING);
			case 1: return new SlotJewelry(storage, i, 8, 32, Jewelry.BRACELET);
			case 2: return new SlotJewelry(storage, i, 8, 54, Jewelry.NECKLACE);
			case 3: return new Slot(storage, i, 35, 47);
		}
		
		return new Slot(storage, i, 100, 100);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack != null) {
			if (!player.isSneaking()) {
				world.playSoundAtEntity(player, Mariculture.modid + ":mirror", 1.0F, 1.0F);
				if(stack.attemptDamageItem(1, Rand.rand))
					stack.stackSize--;
				else
					player.openGui(Mariculture.instance, GuiIds.STORAGE, world, stack.itemID, 0, 0);
			}

			return stack;
		}
		
		return null;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return OreDicHelper.getDictionaryName(stack2).equals("ingotAluminum");
	}
	
	@Override
	public Object getGUIContainer(EntityPlayer player) {
		return new ContainerMirror(player.inventory, new InventoryStorage(player, size), player.worldObj, player.getCurrentEquippedItem());
	}

	public Object getGUIElement(EntityPlayer player) {
		return new GuiMirror(player.inventory, new InventoryStorage(player, size), player.worldObj, gui, player.getCurrentEquippedItem());
	}
	
	public ItemStack[] load(EntityPlayer player, ItemStack stack, int size) {
		return MirrorHelper.instance().get(player);
	}

	public void save(EntityPlayer player, ItemStack[] inventory) {
		MirrorHelper.instance().save(player, inventory);
	}
	
	private class SlotJewelry extends Slot {
		private ResourceLocation ring = new ResourceLocation(Mariculture.modid + ":" + "textures/gui/icon_ring.png");
		private ResourceLocation bracelet = new ResourceLocation(Mariculture.modid + ":" + "textures/gui/icon_bracelet.png");
		private ResourceLocation necklace = new ResourceLocation(Mariculture.modid + ":" + "textures/gui/icon_necklace.png");
		private int type;
		private Icon[] bgIcons;

		public SlotJewelry(IInventory inv, int id, int x, int y, int type) {
			super(inv, id, x, y);
			this.type = type;
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			if (stack.getItem() instanceof ItemJewelry) {
				if (((ItemJewelry) stack.getItem()).getType() == type) {
					return true;
				}
			}
			return false;
		}

		@SideOnly(Side.CLIENT)
		public ResourceLocation getBackgroundIconTexture() {
			switch (this.type) {
			case Jewelry.RING:
				return ring;
			case Jewelry.BRACELET:
				return bracelet;
			case Jewelry.NECKLACE:
				return necklace;
			}

			return (texture == null ? TextureMap.locationItemsTexture : texture);
		}
	}
	
	@Override
	public int getItemEnchantability() {
		return 10;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + name);
	}
}