package mariculture.magic.gui;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mariculture.core.Mariculture;
import mariculture.core.lib.Jewelry;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ContainerMirror extends Container {
	private final InventoryMirror mirrorInventory;
	private final int numRows;
	private final Random rand = new Random();
	private static World worldPointer;

	public ContainerMirror(IInventory playerInventory, IInventory mirrorInventory, World world) {
		worldPointer = world;
		this.mirrorInventory = (InventoryMirror) mirrorInventory;
		this.numRows = mirrorInventory.getSizeInventory() / 9;
		mirrorInventory.openChest();
		final int var3 = (this.numRows - 4) * 18;
		int var4;
		int var5;

		this.addSlotToContainer(new SlotJewelry(mirrorInventory, 0, 8, 12, Jewelry.RING));
		this.addSlotToContainer(new SlotJewelry(mirrorInventory, 1, 8, 34, Jewelry.BRACELET));
		this.addSlotToContainer(new SlotJewelry(mirrorInventory, 2, 8, 56, Jewelry.NECKLACE));
		this.addSlotToContainer(new Slot(mirrorInventory, 3, 35, 47));

		final int id = 2;

		for (var4 = 0; var4 < 3; ++var4) {
			for (var5 = 0; var5 < 9; ++var5) {
				this.addSlotToContainer(new Slot(playerInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, 102 + var4 * 18
						+ var3));
			}
		}

		for (var4 = 0; var4 < 9; ++var4) {
			this.addSlotToContainer(new Slot(playerInventory, var4, 8 + var4 * 18, 160 + var3));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.mirrorInventory.isUseableByPlayer(player);
	}

	@Override
	public boolean enchantItem(EntityPlayer player, int levelToEnchant) {
		InventoryMirror inventoryMirror = (InventoryMirror) this.mirrorInventory;
		ItemStack itemToEnchant = inventoryMirror.getStackInSlot(3);

		if ((levelToEnchant > 0) && itemToEnchant != null
				&& (player.experienceLevel >= levelToEnchant || player.capabilities.isCreativeMode)) {

			if (!player.worldObj.isRemote) {
				List var4 = MirrorEnchantmentHelper
						.buildEnchantmentList(this.rand, itemToEnchant, levelToEnchant);
				boolean var5 = itemToEnchant.itemID == Item.book.itemID;

				if (var4 != null) {
					player.addExperienceLevel(-levelToEnchant);

					if (var5) {
						itemToEnchant.itemID = Item.enchantedBook.itemID;
					}

					int var6 = var5 ? this.rand.nextInt(var4.size()) : -1;

					for (int var7 = 0; var7 < var4.size(); ++var7) {
						final EnchantmentData enchantData = (EnchantmentData) var4.get(var7);

						if (!var5 || var7 == var6) {
							if (var5) {
								Item.enchantedBook.addEnchantment(itemToEnchant, enchantData);
							} else {
								itemToEnchant.addEnchantment(enchantData.enchantmentobj, enchantData.enchantmentLevel);
							}
						}
					}

					this.onCraftMatrixChanged(this.mirrorInventory);
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int slotID) {
		int size = 4;
		int low = size + 27;
		int high = low + 9;
		ItemStack newStack = null;
		final Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			newStack = stack.copy();

			if (slotID < size) {
				if (!this.mergeItemStack(stack, size, high, true)) {
					return null;
				}
			} else {
				if (stack.isItemEnchantable()) {
					if (!this.mergeItemStack(stack, 3, 4, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == Jewelry.RING) {
					if (!this.mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == Jewelry.BRACELET) {
					if (!this.mergeItemStack(stack, 1, 2, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == Jewelry.NECKLACE) {
					if (!this.mergeItemStack(stack, 2, 3, false)) {
						return null;
					}
				} else if (slotID >= size && slotID < low) {
					if (!this.mergeItemStack(stack, low, high, false)) {
						return null;
					}
				} else if (slotID >= low && slotID < high && !this.mergeItemStack(stack, size, low, false)) {
					return null;
				}
			}

			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stack.stackSize == newStack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stack);
		}

		return newStack;
	}

	@Override
	public void onCraftMatrixChanged(final IInventory par1IInventory) {
		this.detectAndSendChanges();
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(final EntityPlayer player) {
		super.onContainerClosed(player);

		if (!player.worldObj.isRemote) {
			final ItemStack var2 = this.mirrorInventory.getStackInSlotOnClosing(3);

			if (var2 != null) {
				player.dropPlayerItem(var2);
			}
		}

		this.mirrorInventory.closeChest();
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		final InventoryMirror mirror = (InventoryMirror) mirrorInventory;

		for (int i = 0; i < crafters.size(); i++) {
			mirror.sendGUINetworkData(this, (ICrafting) crafters.get(i));
		}

	}

	@Override
	public void updateProgressBar(int par1, int par2) {
		InventoryMirror mirror = (InventoryMirror) mirrorInventory;
		mirror.getGUINetworkData(par1, par2);
	}

	public class SlotJewelry extends Slot {
		private ResourceLocation ring = new ResourceLocation(Mariculture.modid + ":" + "textures/gui/icon_ring.png");
		private ResourceLocation bracelet = new ResourceLocation(Mariculture.modid + ":"
				+ "textures/gui/icon_bracelet.png");
		private ResourceLocation necklace = new ResourceLocation(Mariculture.modid + ":"
				+ "textures/gui/icon_necklace.png");
		private EntityPlayer thePlayer;
		private int field_75228_b;
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

		@Override
		public ItemStack decrStackSize(int par1) {
			if (this.getHasStack()) {
				this.field_75228_b += Math.min(par1, this.getStack().stackSize);
			}

			return super.decrStackSize(par1);
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
}
