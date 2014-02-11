package mariculture.fishery.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.core.items.ItemBattery;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluxRod extends ItemBaseRod implements IEnergyContainerItem {
	public ItemFluxRod(EnumRodQuality quality) {
		super(quality);
		setNoRepair();
		setMaxStackSize(1);
		setCreativeTab(MaricultureTab.tabMariculture);
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
		
		list.add("Charge: " + stack.stackTagCompound.getInteger("Energy") + " / " + this.capacity + " RF");
		list.add("Transfer: " + this.maxReceive + " RF/t");
		list.add("RF Per Use: " + this.maxExtract + " RF");
		
		super.addInformation(stack, player, list, bool);
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
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creative, List list) {
		ItemStack battery = new ItemStack(item, 1, 0);
		list.add(ItemBattery.make(battery, 0));
		list.add(ItemBattery.make(battery, this.capacity));
	}
	
	protected int capacity = 100000;
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
