package mariculture.core.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.ItemEnergyContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBattery extends ItemEnergyContainer {
	public ItemBattery(int i, int capacity, int maxReceive, int maxExtract) {
		super(i);
		this.setCreativeTab(MaricultureTab.tabMariculture);
		this.setHasSubtypes(true);
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		this.setMaxStackSize(1);
	}
	
	public static ItemStack make(ItemStack stack, int power) {
		if(stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		stack.stackTagCompound.setInteger("Energy", power);
		
		return stack.copy();
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)));
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
		list.add("Transfer: " + this.maxExtract + " RF/t");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		ItemStack battery = new ItemStack(j, 1, 0);
		list.add(this.make(battery, 0));
		list.add(this.make(battery, this.capacity));
	}
}
