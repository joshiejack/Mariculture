package mariculture.fishery.tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.Core;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketSifterSync;
import mariculture.core.tile.base.TileMultiStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileSifter extends TileMultiStorage implements ISidedInventory {
	public ItemStack display = null;
	public ItemStack texture = new ItemStack(Blocks.planks);
	public LinkedList<ItemStack> toSift = new LinkedList();
	public boolean hasInventory;
	private int[] slots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	
	public TileSifter() {
		inventory = new ItemStack[10];
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return this.hasInventory;
	}
	
	public void updateRender() {
		ItemStack stack = toSift != null && toSift.size() > 0 && toSift.getFirst() != null? toSift.getFirst(): new ItemStack(Core.air);
		PacketHandler.sendAround(new PacketSifterSync(xCoord, yCoord, zCoord, stack), this);
	}
	
	public void addItem(ItemStack stack) {
		if(toSift == null) {
			toSift = new LinkedList();
		}
		
		toSift.add(stack);
		updateRender();
	}
	
	public void process(EntityPlayer player, Random rand) {
		if(toSift != null && toSift.size() > 0) {
			ItemStack stack = toSift.getFirst();
			ArrayList<RecipeSifter> result = Fishing.sifter.getResult(stack);
			if(result != null) {
				for(RecipeSifter bait: result) {
					int chance = rand.nextInt(100);
					if(chance < bait.chance) {
						ItemStack ret = bait.bait.copy();
						ret.stackSize = bait.minCount + rand.nextInt((bait.maxCount + 1) - bait.minCount);
						if(hasInventory) {
							InventoryHelper.addItemStackToInventory(inventory, ret, slots);
						} else {
							if (!player.inventory.addItemStackToInventory(ret)) {
								if(!worldObj.isRemote) {
									SpawnItemHelper.spawnItem(worldObj, xCoord, yCoord + 1, zCoord, ret);
								}
							}
						}
					}
				}
			} else {
				if (!player.inventory.addItemStackToInventory(stack)) {
					if(!worldObj.isRemote) {
						SpawnItemHelper.spawnItem(worldObj, xCoord, yCoord + 1, zCoord, stack);
					}
				}
			}
			
			toSift.removeFirst();
		}
		
		updateRender();
	}
	
	public int getSuitableSlot(ItemStack item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				return i;
			}

			if ((inventory[i].getItemDamage() == item.getItemDamage() && inventory[i].getItem() == item.getItem() 
					&& (inventory[i].stackSize + item.stackSize) <= inventory[i].getMaxStackSize())) {
				return i;
			}
		}

		return 10;
	}
	
	@Override
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		hasInventory = nbt.getBoolean("HasInventory");
		if(nbt.hasKey("Display")) display = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("Display"));
		texture = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("Texture"));
		toSift = new LinkedList();
		NBTTagList list = nbt.getTagList("Memory", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.getCompoundTagAt(i);
			toSift.add(ItemStack.loadItemStackFromNBT(tag));
		}
		
		if(toSift.size() > 0) {
			display = toSift.getFirst();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("HasInventory", hasInventory);
		if(display != null) nbt.setTag("Display", display.writeToNBT(new NBTTagCompound()));
		nbt.setTag("Texture", texture.writeToNBT(new NBTTagCompound()));
		NBTTagList list = new NBTTagList();
		if(toSift.size() > 0) {
			for(ItemStack stack: toSift) {
				NBTTagCompound tag = new NBTTagCompound();
				stack.writeToNBT(tag);
				list.appendTag(tag);
			}
		}
		
		nbt.setTag("Memory", list);
	}
	
	public boolean isSifter(int x, int y, int z) {
		return worldObj.getTileEntity(x, y, z) instanceof TileSifter && !isPartnered(x, y, z);
	}
	
	@Override
	public TileSifter getMaster() {
		if(master == null) return null;
		TileEntity tile = worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
		return tile != null && tile instanceof TileSifter? (TileSifter)tile: null;
	}
	
	@Override
	public void onBlockPlaced() {
		if(onBlockPlaced(xCoord, yCoord, zCoord)) {
			TileSifter master = (TileSifter) getMaster();
			if(master != null && !master.isInit()) {
				master.init();
			}
		}
	}
	
	public boolean onBlockPlaced(int x, int y, int z) {
		if(isSifter(x, y, z) && isSifter(x + 1, y, z)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x+ 1, y, z, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
			return true;
		}
		
		if(isSifter(x - 1, y, z) && isSifter(x, y, z)) {
			MultiPart mstr = new MultiPart(x - 1, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
			return true;
		}
		
		if(isSifter(x, y, z) && isSifter(x, y, z + 1)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.SOUTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
			return true;
		}
		
		if(isSifter(x, y, z - 1) && isSifter(x, y, z)) {
			MultiPart mstr = new MultiPart(x, y, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.SOUTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onBlockBreak() {
		if(!worldObj.isRemote && isMaster()) {
			for(ItemStack stack: toSift) {
				SpawnItemHelper.spawnItem(worldObj, xCoord, yCoord + 1, zCoord, stack);
			}
		}
		
		super.onBlockBreak();
	}
}
