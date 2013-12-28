package mariculture.fishery.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileMulti;
import mariculture.core.blocks.base.TileMultiInvMachinePowered;
import mariculture.core.blocks.base.TileMulti.Cached;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.AverageHelper;
import mariculture.core.helpers.ItemTransferHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.UtilMeta;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class TileIncubator extends TileMultiInvMachinePowered implements IHasNotification {

	public TileIncubator() {
		max = MachineSpeeds.getIncubatorSpeed();
		inventory = new ItemStack[22];
	}
	
	@Override
	public int getRFCapacity() {
		return 50000;
	}
	
	public int[] in = new int[] { 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	public int[] out = new int[] { 13, 14, 15, 16, 17, 18, 19, 20, 21 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 4, 5, 6, 7, 8, 9, 10, 11, 12,
							13, 14, 15, 16, 17, 18, 19, 20, 21};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot > 3 && slot < 13;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot > 12;
	}

	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.ITEM;
	}

	@Override
	public boolean canWork() {
		return hasPower() && hasEgg() && hasCorrectRSSignal() && outputHasRoom();
	}
	
	public boolean hasCorrectRSSignal() {
		return RedstoneMode.canWork(this, mode) && RedstoneMode.canWork(worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord), mode) &&
			RedstoneMode.canWork(worldObj.getBlockTileEntity(xCoord, yCoord + 2, zCoord), mode);
	}

	private boolean outputHasRoom() {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		for(Integer i: out) {
			if(inventory[i] == null);
				return true;
		}
		
		return false;
	}

	@Override
	public void updateMasterMachine() {
		if(!worldObj.isRemote) {
			if(canWork) {
				energyStorage.extractEnergy(getRFUsage(), false);
				processed+=speed;
				
				if(onTick(70))
					processed-=heat;
				if(processed >= max) {
					processed = 0;
					if(canWork()) {
						for(int o = 0; o < heat + 1; o++) {
							hatchEgg();
						}
					}
					
					canWork = canWork();
				}
			} else {
				processed = 0;
			}
			
			if(processed <= 0)
				processed = 0;
		}
	}
	
	@Override
	public void updateSlaveMachine() {
		return;
	}
	
	private boolean hasEgg() {
		for(Integer i: in) {
			if(inventory[i] != null)
				return true;
		}
		
		return false;
	}
	
	private boolean hasPower() {
		return energyStorage.extractEnergy(getRFUsage(), true) >= getRFUsage();
	}
	
	public int getRFUsage() {
		return 35 + ((speed - 1) * 40) + (heat * 40);
	}
	
	public boolean hatchEgg() {
		Integer[] inArray = new Integer[in.length];
		int i = 0;
		for (int value : in) {
			inArray[i++] = Integer.valueOf(value);
		}
		
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(inArray));
		Collections.shuffle(list);
		for(Integer j: list) {
			if(inventory[j] != null) {
				if(openEgg(j))
					return true;
			}
		}
		
		return false;
	}
	
	private boolean openEgg(int slot) {
		Random rand = new Random();
		if (inventory[slot].getItem() instanceof ItemFishy) {
			int[] fertility = inventory[slot].stackTagCompound.getIntArray(Fishery.fertility.getEggString());
			int[] lifes = inventory[slot].stackTagCompound.getIntArray(Fishery.lifespan.getEggString());

			if (inventory[slot].getTagCompound().hasKey("SpeciesList")) {
				int birthChance = AverageHelper.getMode(fertility);
				int eggLife = (AverageHelper.getMode(lifes)/20/60) * 10;

				if (inventory[slot].getTagCompound().getInteger("currentFertility") == -1) {
					inventory[slot].getTagCompound().setInteger("currentFertility", eggLife);
					inventory[slot].getTagCompound().setInteger("malesGenerated", 0);
					inventory[slot].getTagCompound().setInteger("femalesGenerated", 0);
				}

				inventory[slot].getTagCompound().setInteger("currentFertility",
						inventory[slot].getTagCompound().getInteger("currentFertility") - 1);

				int chance = rand.nextInt(birthChance);

				if (chance == 0) {
					ItemStack fish = Fishing.fishHelper.makeBredFish(inventory[slot], rand);
					int dna = Fishery.gender.getDNA(fish);
					new ItemTransferHelper(this).insertStack(fish, out);

					if (dna == FishHelper.MALE) {
						inventory[slot].getTagCompound().setInteger("malesGenerated",
								inventory[slot].getTagCompound().getInteger("malesGenerated") + 1);
					} else if (dna == FishHelper.FEMALE) {
						inventory[slot].getTagCompound().setInteger("femalesGenerated",
								inventory[slot].getTagCompound().getInteger("femalesGenerated") + 1);
					}
				}

				if (inventory[slot].getTagCompound().getInteger("currentFertility") == 0) {
					ItemStack fish = Fishing.fishHelper.makeBredFish(inventory[slot], rand);
					// If no males were generated create one
					if (inventory[slot].getTagCompound().getInteger("malesGenerated") <= 0) {
						new ItemTransferHelper(this).insertStack(Fishery.gender.addDNA(fish.copy(), FishHelper.MALE), out);
					}

					// If no females were generated create one
					if (inventory[slot].getTagCompound().getInteger("femalesGenerated") <= 0) {
						new ItemTransferHelper(this).insertStack(Fishery.gender.addDNA(fish.copy(), FishHelper.FEMALE), out);
					}

					decrStackSize(slot, 1);
					return true;
				}
			}
		} else if (inventory[slot].itemID == Item.egg.itemID) {
			if (Rand.nextInt(8)) {
				new ItemTransferHelper(this).insertStack(new ItemStack(383, 1, 93), out);
			}

			decrStackSize(slot, 1);
			return true;
		} else if(inventory[slot].itemID == Block.dragonEgg.blockID) {
			int chance = MaricultureHandlers.upgrades.hasUpgrade("ethereal", this)? 48000: 64000;
			if(Rand.nextInt(chance)) {
				new ItemTransferHelper(this).insertStack(new ItemStack(Core.craftingItem, 1, CraftingMeta.DRAGON_EGG), out);
			}
			
			if(Rand.nextInt(10)) {
				decrStackSize(slot, 1);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
		case NO_EGG:
			return !hasEgg();
		case NO_RF:
			return !hasPower();
		default:
			return false;
	}
	}

	@Override
	public String getProcess() {
		return "incubated";
	}
	
	//Master STUFF
	@Override
	public boolean setMaster() {
		cache = new ArrayList<Cached>();
		
		World world = worldObj;
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		
		if(mstr.built && !(x == mstr.x && y == mstr.y && z == mstr.z)) {
			if(world.getBlockTileEntity(mstr.x, mstr.y, mstr.z) != null) {
				((TileMulti) world.getBlockTileEntity(mstr.x, mstr.y, mstr.z)).setMaster();
			}
		}
		
		if(isBase(x, y, z) && isTop(x, y + 1, z) && isTop(x, y + 2, z) && !isTop(x, y + 3, z)) {
			cache.add(new Cached(x, y, z));
			cache.add(new Cached(x, y + 1, z));
			cache.add(new Cached(x, y + 2, z));
			return mstr.set(true, x, y, z);
		}
		
		if(isBase(x, y - 1, z) && isTop(x, y, z) && isTop(x, y + 1, z) && !isTop(x, y + 2, z)) {
			cache.add(new Cached(x, y - 1, z));
			cache.add(new Cached(x, y, z));
			cache.add(new Cached(x, y + 1, z));
			return mstr.set(true, x, y - 1, z);
		}
		
		if(isBase(x, y - 2, z) && isTop(x, y - 1, z) && isTop(x, y, z) && !isTop(x, y + 1, z)) {
			cache.add(new Cached(x, y - 2, z));
			cache.add(new Cached(x, y - 1, z));
			cache.add(new Cached(x, y, z));
			return mstr.set(true, x, y - 2, z);
		}
		
		return mstr.set(false, x, y, z);
	}
	
	public boolean isBase(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == this.getBlockType().blockID && worldObj.getBlockMetadata(x, y, z) == UtilMeta.INCUBATOR_BASE;
	}
	
	public boolean isTop(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == this.getBlockType().blockID && worldObj.getBlockMetadata(x, y, z) == UtilMeta.INCUBATOR_TOP;
	}
}
