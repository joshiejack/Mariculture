package mariculture.core.blocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import mariculture.core.Core;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.lib.Extra;
import mariculture.core.lib.PacketIds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileOyster extends TileEntity implements IInventory, ISidedInventory {
	public ItemStack[] inventory = new ItemStack[1];

	public boolean loaded = false;
	private int clientTick = 0;
	private int tick = 0;
	Random rand = new Random();

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			clientTick++;
			if (clientTick >= Extra.REFRESH_CLIENT_RATE) {
				clientTick = 0;
				sendOysterUpdate();
			}
		}

		if (!this.worldObj.isRemote
				&& hasSand()
				&& (this.worldObj.getBlockMaterial(this.xCoord, this.yCoord + 1, this.zCoord) == Material.water)) {
			if (tick >= Extra.PEARL_GEN_SPEED) {
				if (rand.nextInt(Extra.PEARL_GEN_CHANCE) == 0) {
					if (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Core.pearlBrick.blockID) {
						setInventorySlotContents(0,
								new ItemStack(Core.pearls, 1, worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord)));
					} else {
						setInventorySlotContents(0, PearlGenHandler.getRandomPearl(rand));
					}

					sendOysterUpdate();
				}

				tick = 0;
			}

			tick++;
		}
	}

	public boolean hasSand() {
		if (inventory[0] == null) {
			return false;
		}

		return true;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.inventory[par1];
	}

	public ItemStack getCurrentPearl() {
		return inventory[0];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.inventory[par1] != null) {
			ItemStack var3;

			if (this.inventory[par1].stackSize <= par2) {
				var3 = this.inventory[par1];
				this.inventory[par1] = null;
				this.onInventoryChanged();
				return var3;
			} else {
				var3 = this.inventory[par1].splitStack(par2);

				if (this.inventory[par1].stackSize == 0) {
					this.inventory[par1] = null;
				}

				this.onInventoryChanged();
				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		final ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack stack) {
		this.inventory[par1] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		if (stack != null) {
			sendOysterUpdate();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "TileOyster";
	}

	@Override
	public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			final NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			final int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < this.inventory.length) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}

		tick = par1NBTTagCompound.getInteger("CurrentTick");
	}

	@Override
	public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setInteger("CurrentTick", this.tick);

		final NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.inventory.length; ++var3) {
			if (this.inventory[var3] != null) {
				final NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false
				: par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public void onInventoryChanged() {
		if (this.worldObj != null) {
			this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		}

		sendOysterUpdate();
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	public static void handleOysterUpdate(Packet250CustomPayload packet, World world) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;
		int itemID;
		int itemMeta;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			itemID = inputStream.readInt();
			itemMeta = inputStream.readInt();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileOyster) {
			ItemStack stack = (itemID > -1) ? new ItemStack(itemID, 1, itemMeta) : null;
			((TileOyster) tile).inventory[0] = stack;
			((TileOyster) tile).loaded = true;
		}
	}

	public void sendOysterUpdate() {
		int id = inventory[0] != null ? inventory[0].itemID : -1;
		int meta = inventory[0] != null ? inventory[0].getItemDamage() : 0;

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.RENDER_OYSTER_UPDATE);
			os.writeInt(xCoord);
			os.writeInt(yCoord);
			os.writeInt(zCoord);
			os.writeInt(id);
			os.writeInt(meta);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 25, worldObj.provider.dimensionId, packet);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.itemID == Block.sand.blockID || stack.itemID == Core.pearls.itemID;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		return stack.itemID == Block.sand.blockID && inventory[0] == null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return stack.itemID == Core.pearls.itemID;
	}
}