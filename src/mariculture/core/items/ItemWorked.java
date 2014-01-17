package mariculture.core.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.fishery.Fishery;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class ItemWorked extends ItemDamageable {
	public Icon crack;
	
	public ItemWorked(int i) {
		super(i, 100);
		setNoRepair();
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			return StatCollector.translateToLocal("itemGroup.jewelryTab");
		}
		
		ItemStack worked = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
		return StatCollector.translateToLocal("mariculture.string.unworked") + " " + worked.getItem().getItemDisplayName(worked);
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			return 0;
		}
		
		return 1 + stack.stackTagCompound.getInteger("Required") - stack.stackTagCompound.getInteger("Worked");
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			return 0;
		}
		
		return 1 + stack.stackTagCompound.getInteger("Required");
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(stack.stackTagCompound == null) {
			return;
		}
		
		int max = stack.stackTagCompound.getInteger("Required");
		int cur = stack.stackTagCompound.getInteger("Worked");
		int percent = (int) (((double)cur/(double)max) * 100);
		
		list.add(percent + "% Worked");
		ItemStack worked = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
		worked.getItem().addInformation(stack, player, list, bool);
	}
	
	@Override
	public boolean isDamaged(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int meta) {
		return 5;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if (stack.hasTagCompound()) {
			ItemStack worked = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
			if(worked.itemID != this.itemID)
				return worked.getItem().getIcon(worked, pass);
		}
		
		if(pass == 5)
			return crack;

		return itemIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":unworked");
		crack = iconRegister.registerIcon(Mariculture.modid + ":crack");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creative, List list) {
		return;
	}
}
