package mariculture.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.Modules;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ItemFluidContainer extends ItemMariculture {
	public ItemFluidContainer(int i) {
		super(i);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if (!world.isRemote) {
			if (player.shouldHeal()) {
				player.heal(5);
			}
		}

		return stack.stackSize <= 0 ? new ItemStack(Item.glassBottle) : stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack.getItemDamage() == FluidContainerMeta.BOTTLE_FISH_OIL && player.shouldHeal()) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 24;
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.drink;
	}

	@Override
	public int getMetaCount() {
		return FluidContainerMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case FluidContainerMeta.BOTTLE_VOID: {
			name = "bottleVoid";
			break;
		}
		case FluidContainerMeta.BOTTLE_FISH_OIL: {
			name = "bottleFishOil";
			break;
		}
		case FluidContainerMeta.BOTTLE_IRON: {
			name = "bottleIron";
			break;
		}
		case FluidContainerMeta.BOTTLE_GOLD: {
			name = "bottleGold";
			break;
		}
		case FluidContainerMeta.BOTTLE_COPPER: {
			name = "bottleCopper";
			break;
		}
		case FluidContainerMeta.BOTTLE_TIN: {
			name = "bottleTin";
			break;
		}
		case FluidContainerMeta.BOTTLE_SILVER: {
			name = "bottleSilver";
			break;
		}
		case FluidContainerMeta.BOTTLE_LEAD: {
			name = "bottleLead";
			break;
		}
		case FluidContainerMeta.BOTTLE_BRONZE: {
			name = "bottleBronze";
			break;
		}
		case FluidContainerMeta.BOTTLE_STEEL: {
			name = "bottleSteel";
			break;
		}
		case FluidContainerMeta.BOTTLE_ALUMINUM: {
			name = "bottleAluminum";
			break;
		}
		case FluidContainerMeta.BOTTLE_TITANIUM: {
			name = "bottleTitanium";
			break;
		}
		case FluidContainerMeta.BOTTLE_MAGNESIUM: {
			name = "bottleMagnesium";
			break;
		}
		case FluidContainerMeta.BOTTLE_NICKEL: {
			name = "bottleNickel";
			break;
		}
		case FluidContainerMeta.BOTTLE_GLASS: {
			name = "bottleGlass";
			break;
		}
		case FluidContainerMeta.BOTTLE_GAS: {
			name = "bottleNaturalGas";
			break;
		}
		case FluidContainerMeta.BOTTLE_FISH_FOOD: {
			name = "bottleFishFood";
			break;
		}
		case FluidContainerMeta.BOTTLE_RUTILE: {
			name = "bottleRutile";
			break;
		}
		default:
			name = "container";
		}

		return name;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case FluidContainerMeta.BOTTLE_BRONZE:
			return OreDictionary.getOres("ingotBronze").size() > 0;
		case FluidContainerMeta.BOTTLE_LEAD:
			return OreDictionary.getOres("ingotLead").size() > 0;
		case FluidContainerMeta.BOTTLE_NICKEL:
			return OreDictionary.getOres("ingotNickel").size() > 0;
		case FluidContainerMeta.BOTTLE_SILVER:
			return OreDictionary.getOres("ingotSilver").size() > 0;
		case FluidContainerMeta.BOTTLE_STEEL:
			return OreDictionary.getOres("ingotSteel").size() > 0;
		case FluidContainerMeta.BOTTLE_TIN:
			return OreDictionary.getOres("ingotTin").size() > 0;
		case FluidContainerMeta.BOTTLE_FISH_OIL:
			return (Modules.fishery.isActive());

		default:
			return true;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];
		for (int i = 0; i < icons.length; i++) {
			if(isActive(i)) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this.itemID, 1, i)));
			}
		}
	}
}
