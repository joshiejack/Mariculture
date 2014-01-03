package mariculture.factory.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeOven;
import mariculture.core.blocks.base.TileMachineTank;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileOven extends TileMachineTank implements IHasNotification {
	public float isBurning;
	
	public TileOven() {
		max = MachineSpeeds.getOvenSpeed();
		inventory = new ItemStack[15];
	}
	
	public static final int[] in = new int[] { 5, 6, 7, 8, 9, 10, 11 };
	public static final int[] out = new int[] { 12, 13, 14 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.ITEM;
	}

	@Override
	public void updateMachine() {
		if(!worldObj.isRemote) {
			if(onTick(20)) {
				drainGas();
			}
			
			
			if(canWork) {
				processed+=speed;
				if(processed >= max) {
					cook();
					processed = 0;
				}
			} else {
				processed = 0;
			}
		}
	}
	
	@Override
	public boolean canWork() {
		return isBurning() && RedstoneMode.canWork(this, mode) && hasItem() && hasRoom();
	}
	
	public boolean hasItem() {
		for(int i: in) {
			ItemStack stack = getStackInSlot(i);
			if(stack != null) {
				if(MaricultureHandlers.oven.getResult(stack) != null)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasRoom() {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		for(int i: in) {
			if(getStackInSlot(i) != null) {
				ItemStack stack = getStackInSlot(i);
				if(MaricultureHandlers.oven.getResult(stack) != null) {
					ItemStack output = ((RecipeOven)MaricultureHandlers.oven.getResult(stack)).output;
					if(InventoryHelper.canAddItemStackToInventory(inventory, output, out))
						return true;
				}
			}
		}
		
		return false;
	}
	
	private void cook() {
		int loop = (heat/5) + 1;
		for(int j = 0; j < loop; j++) {
			for(int i: in) {
				if(getStackInSlot(i) != null) {
					ItemStack stack = getStackInSlot(i);
					if(MaricultureHandlers.oven.getResult(stack) != null) {
						ItemStack output = ((RecipeOven)MaricultureHandlers.oven.getResult(stack)).output.copy();
						if(purity <= 3)
							output.stackSize+=purity/2;
						else if(purity <= 7)
							output.stackSize+=purity/1.5;
						else if(purity <= 12)
							output.stackSize+=purity;
						helper.insertStack(output, out);
						decrStackSize(i, output.stackSize);
						canWork = canWork();
						onInventoryChanged();
					}
				}
			}
		}
	}
	
	private float getDrainAmount() {
		return (float) (0.05F + ((heat*0.015 + purity*0.1 + speed*0.025)));
	}
	
	private void drainGas() {
		if(!RedstoneMode.canWork(this, mode))
			return;
		if(isBurning < 2.5F) {
			float value = MaricultureHandlers.turbine.getModifier(tank.fluid);
			if(value > 0F && tank.getFluidAmount() >= 1000) {
				drain(ForgeDirection.UNKNOWN, new FluidStack(tank.getFluidID(), 1000), true);
				isBurning+=value;
			}
		}		
				
		if(isBurning > 0F)
			isBurning-=getDrainAmount();
		else
			isBurning = 0F;
	}
	
	@Override
	public void getGUINetworkData(int id, int value) {
		super.getGUINetworkData(id, value);
		if(id - offset == 0)
			isBurning = value;
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		Packets.updateGUI(player, container, 0 + offset, isBurning > 0? 1: 0);
	}

	@Override
	public boolean isNotificationVisible(NotificationType type) {
		return false;
	}

	@Override
	public String getProcess() {
		return "cooked";
	}

	public boolean isBurning() {
		return isBurning > 0F;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isBurning = nbt.getFloat("IsBurning");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("IsBurning", isBurning);
	}
}
