package mariculture.core.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.ItemEnergyContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBattery extends ItemEnergyContainer {
	public ItemBattery(int i, int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
		this.setCreativeTab(MaricultureTab.tabMariculture);
		this.setHasSubtypes(true);
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
	public void registerIcons(IIconRegister iconRegister) {
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
	public void getSubItems(Item item, CreativeTabs creative, List list) {
		ItemStack battery = new ItemStack(item, 1, 0);
		list.add(this.make(battery, 0));
		list.add(this.make(battery, this.capacity));
	}
}
