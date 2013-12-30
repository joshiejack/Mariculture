package mariculture.factory.blocks;

import java.util.Random;

import mariculture.api.core.IBlacklisted;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.base.TileMulti;
import mariculture.core.blocks.base.TileMultiInvTankMachine;
import mariculture.core.helpers.FluidTransferHelper;
import mariculture.core.network.Packets;
import mariculture.factory.FactoryEvents;
import mariculture.factory.gui.ContainerPressureVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;


public class TilePressureVessel extends TileMultiInvTankMachine implements IBlacklisted {
	public int blocksInStructure = 1;
	
	public TilePressureVessel() {
		this.inventory = new ItemStack[4];
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
	
	@Override
	public int getTankCapacity(int storage) {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return ((100 * tankRate) + (storage * 5 * tankRate)) * blocksInStructure;
	}
	
	@Override
	public boolean setMaster() {
		World world = worldObj;
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		
		int[] coords = getNeighbourMaster(x, y, z);
		if(coords.length == 3) {
			TilePressureVessel master  = (TilePressureVessel) world.getBlockTileEntity(coords[0], coords[1], coords[2]);
			if(master != null) {
				if(!mstr.built) {
					master.blocksInStructure++;
					addBlock(xCoord, yCoord, zCoord, coords);
				}
			}
			
			return mstr.set(true, coords[0], coords[1], coords[2]);
		} else {
			return mstr.set(true, x, y, z);
		}
	}

	private void addBlock(int x, int y, int z, int[] mstr) {
		TilePressureVessel master = (TilePressureVessel) worldObj.getBlockTileEntity(mstr[0], mstr[1], mstr[2]);
		this.blocksInStructure = master.blocksInStructure;
		this.tiles = master.tiles;
	}

	private int[] getNeighbourMaster(int x, int y, int z) {
		if(isComponent(x + 1, y, z))
			return getCoords(x + 1, y, z);
		if(isComponent(x - 1, y, z))
			return getCoords(x - 1, y, z);
		if(isComponent(x, y + 1, z))
			return getCoords(x, y + 1, z);
		if(isComponent(x, y - 1, z))
			return getCoords(x, y - 1, z);
		if(isComponent(x, y, z + 1))
			return getCoords(x, y, z + 1);
		if(isComponent(x, y, z - 1))
			return getCoords(x, y, z - 1);
			
		return new int[] { 0 };
	}
	
	private int[] getCoords(int x, int y, int z) {
		TileMulti tile = (TileMulti) worldObj.getBlockTileEntity(x, y, z);
		return new int[] { tile.mstr.x, tile.mstr.y, tile.mstr.z };
	}
	
	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[1], inventory[2], inventory[3] };
	}

	@Override
	public boolean isBlacklisted(World world, int x, int y, int z) {
		return true;
	}
	
	public int[][] tiles = new int[729][3];
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.blocksInStructure = tagCompound.getInteger("BlocksInVessel");
		
		NBTTagList tagList = tagCompound.getTagList("Coordinates");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			int id = tag.getInteger("ID");
			int x = tag.getInteger("X");
			int y = tag.getInteger("Y");
			int z = tag.getInteger("z");
			
			tiles[id][0] = x;
			tiles[id][1] = y;
			tiles[id][2] = z;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("BlocksInVessel", this.blocksInStructure);
		
		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < tiles.length; i++) {
			int x = tiles[i][0];
			int y = tiles[i][1];
			int z = tiles[i][2];
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("ID", i);
			tag.setInteger("X", x);
			tag.setInteger("Y", y);
			tag.setInteger("Z", z);
			itemList.appendTag(tag);
		}
	}
	
	
	public void getGUINetworkData(int i, int j) {
		super.getGUINetworkData(i, j);
		switch (i) {
		case 3:
			mstr.x = j;
			break;
		case 4:
			mstr.y = j;
			break;
		case 5:
			mstr.z = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerPressureVessel container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		Packets.updateGUI(player, container, 3, mstr.x);
		Packets.updateGUI(player, container, 4, mstr.y);
		Packets.updateGUI(player, container, 5, mstr.z);
	}
	
	@Override
	public Packet getDescriptionPacket() {		
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
	}
	
	@Override
	public void updateMaster() {
		if(onTick(20)) {
			fillFLUDD();
		}
	}

	@Override
	public void updateAll() {
		if(onTick(30)) {
			Packets.updateTile(this, 32, getDescriptionPacket());
			for(int i = 0; i < heat; i++) {
				transfer();
			}
		}
	}
	
	private void transfer() {
		int drainAmount = (purity < 1)? 100: purity * 250;

		FluidTransferHelper transfer = new FluidTransferHelper(this);
		transfer.transfer(new Random(), new int[] { drainAmount, (int) (drainAmount/1.5), (int)(drainAmount/2), (int)(drainAmount/3), 100, 20, 1 });
	}
	
	private void fillFLUDD() {
		purity = (this.purity < 1) ? 1 : this.purity;
		int transfer = (purity + 1 * 250);
		if (inventory[0] != null && tank.getFluidAmount() >= 1) {
			if (FactoryEvents.handleFill(inventory[0], false, transfer) != null && tank.getFluidAmount() - transfer >= 0) {
				tank.drain(transfer, true);
				inventory[0] = FactoryEvents.handleFill(inventory[0], true, transfer);
			} else if (FactoryEvents.handleFill(inventory[0], false, 1) != null && tank.getFluidAmount() - 1 >= 0) {
				tank.drain(1, true);
				inventory[0] = FactoryEvents.handleFill(inventory[0], true, 1);
			}

		}
	}
}

/*
public class TilePressureVessel extends TileMultiInvTankMachine implements IBlacklisted, ISidedInventory {
	private int tick = 0;
	private int tickAll = 0;

	private int times = 20;
	private int transfer = 100;
	private int slot = 0;

	private Random rand = new Random();

	public TilePressureVessel() {
		this.inventory = new ItemStack[4];
	}

	private void fillFLUDD() {
		//TODO: Readd fludd fill
		if (inventory[0] != null && liquidQty >= 1) {
			if (FactoryEvents.handleFill(inventory[0], false, this.transfer) != null
					&& this.liquidQty - this.transfer >= 0) {
				this.liquidQty -= this.transfer;
				inventory[0] = FactoryEvents.handleFill(inventory[0], true, this.transfer);
			} else if (FactoryEvents.handleFill(inventory[0], false, 1) != null && this.liquidQty - 1 >= 0) {
				this.liquidQty--;
				inventory[0] = FactoryEvents.handleFill(inventory[0], true, 1);
			}

		}
	}
	
	@Override
	public void updateMaster() {
		if(onTick(20)) {
			fillFLUDD();
		}
	}
	
	@Override
	public void updateAll() {
		if(onTick(16)) {
			transfer();
		}
	}

	@Override
	public int getTankCapacity(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 32) + (count * (FluidContainerRegistry.BUCKET_VOLUME * 2)));
	}

	@Override
	public void updateUpgrades() {
		super.updateUpgrades();

		int purityCount = MaricultureHandlers.upgrades.getData("purity", this);
		int heatAmount = MaricultureHandlers.upgrades.getData("temp", this);

		this.times = heatAmount + 1;
		this.transfer = (purityCount + 1) * 125;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	public void getGUINetworkData(int i, int j) {
		super.getGUINetworkData(i, j);

		switch (i) {
		case 3:
			times = j;
			break;
		case 4:
			transfer = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerPressureVessel container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);

		Packets.updateGUI(player, container, 3, this.times);
		Packets.updateGUI(player, container, 4, this.transfer);
	}

	private void transfer() {
		//TODO: Update Vessel Transferring
		new TransferHelper(this).transfer(rand, new int[] { this.transfer, 100, 50, 20, 1 });
	}

	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[1], inventory[2], inventory[3] };
	}

	@Override
	public boolean isBlacklisted(World world, int x, int y, int z) {
		return true;
	}

	private static final int[] slots = new int[] { 0 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return isFull(stack);
	}

	public boolean isFull(ItemStack stack) {
		if (stack == null) {
			return false;
		}

		if (stack.getItem() instanceof ItemArmorFLUDD) {
			int water = (stack.hasTagCompound()) ? stack.stackTagCompound.getInteger("water") : ItemArmorFLUDD.STORAGE;
			return water >= ItemArmorFLUDD.STORAGE;
		}

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == 3 && !isFull(stack);
	}
}
*/