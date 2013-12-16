package mariculture.fishery.items;

import java.util.List;

import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.Fishery;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.ItemEnergyContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishyFood extends Item implements IEnergyContainerItem {
	public ItemFishyFood(int i) {
		super(i);
		this.setCreativeTab(MaricultureTab.tabFish);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("fish.data.dead") + " "
				+ Fishing.fishHelper.getSpecies(stack.getItemDamage()).getName();
	}

	@Override
	public Icon getIconFromDamage(int dmg) {
		FishSpecies fish = Fishing.fishHelper.getSpecies(dmg);
		if(fish != null) {
			return fish.getIcon();
		}

		return Fishery.cod.getIcon();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.canEat(false)) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		stack = Fishing.fishHelper.getSpecies(stack.getItemDamage()).onRightClick(world, player, stack, itemRand);

		return stack;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;

		Fishing.fishHelper.getSpecies(stack.getItemDamage()).onConsumed(world, player);

		return stack;
	}
	
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return 0;
	}
	
	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if(container.getItemDamage() == Fishery.electricRay.fishID) {
			container.stackSize--;
			return 1000;
		}
		
		return 0;
	}
	
	@Override
	public int getEnergyStored(ItemStack container) {
		if(container.getItemDamage() == Fishery.electricRay.fishID) {
			return 1000;
		}
		
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		if(container.getItemDamage() == Fishery.electricRay.fishID) {
			return 1000;
		}
		
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		for (int i = 0; i < FishSpecies.speciesList.size(); ++i) {
			list.add(new ItemStack(j, 1, i));
		}
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
	}
}
