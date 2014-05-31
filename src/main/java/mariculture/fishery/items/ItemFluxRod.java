package mariculture.fishery.items;

import java.util.List;

import mariculture.core.items.ItemBattery;
import mariculture.core.util.Text;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluxRod extends ItemRod implements IEnergyContainerItem {
	public ItemFluxRod() {
		super(0, 0);
		setNoRepair();
		setMaxStackSize(1);
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			return 1 + this.capacity;
		}
		
		return 1 + this.capacity - stack.stackTagCompound.getInteger("Energy");
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return 1 + this.capacity;
	}
	
	@Override
	public boolean isDamaged(ItemStack stack) {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(stack.stackTagCompound == null) {
			return;
		}
		
		list.add(Text.translate("charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.capacity + " " + Text.translate("rf"));
		list.add(Text.translate("transfer") + ": " + this.maxReceive + " " + Text.translate("rft"));
		list.add(Text.translate("peruse") + ": " + this.maxExtract + " " + Text.translate("rf"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creative, List list) {
		ItemStack battery = new ItemStack(item, 1, 0);
		list.add(ItemBattery.make(battery, 0));
		list.add(ItemBattery.make(battery, this.capacity));
	}
	
	protected int capacity = 250000;
	protected int maxReceive = 250;
	protected int maxExtract = 100;

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		
		if(container.stackTagCompound.getInteger("Energy") <= 0) {
			return 0;
		}
		
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, this.maxExtract);

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}
}
