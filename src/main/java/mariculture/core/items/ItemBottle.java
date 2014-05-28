package mariculture.core.items;

import java.util.List;

import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.TankMeta;
import mariculture.core.util.Text;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemBottle extends ItemMariculture {
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if (!world.isRemote) {
			if (player.shouldHeal()) {
				int meta = stack.getItemDamage();
				player.heal(meta == BottleMeta.FISH_OIL? 7: 3);
			}
		}

		return stack.stackSize <= 0 ? new ItemStack(Items.glass_bottle) : stack;
	}
	
	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int meta = stack.getItemDamage();
		if((meta == BottleMeta.FISH_OIL || meta == BottleMeta.FISH_OIL_BASIC) && player.shouldHeal()) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 24;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		int meta = stack.getItemDamage();
		if(meta == BottleMeta.VOID) {
			list.add(Text.PURPLE + StatCollector.translateToLocal("mariculture.string.blackhole"));
			return;
		}
			
		if(meta == BottleMeta.EMPTY) {
			list.add(Text.YELLOW + StatCollector.translateToLocal("mariculture.string.doubleBottle"));
			return;
		}
		
		FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
		int amount = fluid == null? 0: fluid.amount;
		list.add(FluidHelper.getFluidName(fluid));
		FluidHelper.getFluidQty(list, fluid, -1);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(stack.getItemDamage() != BottleMeta.VOID)
			return false;
		Block block = world.getBlock(x, y, z);
		if(side == 1 && (block instanceof BlockFluidBase || block instanceof BlockLiquid)) {
			--y;
		} else if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
			if (side == 0)
				--y;
			if (side == 1)
				++y;
			if (side == 2)
				--z;
			if (side == 3)
				++z;
			if (side == 4)
				--x;
			if (side == 5)
				++x;
		}

		if (stack.stackSize == 0) {
			return false;
		} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else if (y == 255 && block.getMaterial().isSolid()) {
			return false;
		} else if (world.canPlaceEntityOnSide(block, x, y, z, false, side, player, stack)) {
			int meta = TankMeta.BOTTLE;
			int metadata = block.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);

			if (placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), 
				block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		Block block = Core.tanks;
		if (!world.setBlock(x, y, z, block, meta, 3)) {
			return false;
		}

		if (world.getBlock(x, y, z) == block) {
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, meta);
		}

		return true;
	}

	@Override
	public int getMetaCount() {
		return BottleMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case BottleMeta.VOID:
			return "bottleVoid";
		case BottleMeta.FISH_OIL:
			return "bottleFishOil";
		case BottleMeta.IRON:
			return "bottleIron";
		case BottleMeta.GOLD:
			return "bottleGold";
		case BottleMeta.COPPER:
			return "bottleCopper";
		case BottleMeta.TIN:
			return "bottleTin";
		case BottleMeta.SILVER:
			return "bottleSilver";
		case BottleMeta.LEAD:
			return "bottleLead";
		case BottleMeta.BRONZE:
			return "bottleBronze";
		case BottleMeta.STEEL:
			return "bottleSteel";
		case BottleMeta.ALUMINUM:
			return "bottleAluminum";
		case BottleMeta.TITANIUM:
			return "bottleTitanium";
		case BottleMeta.MAGNESIUM:
			return "bottleMagnesium";
		case BottleMeta.NICKEL:
			return "bottleNickel";
		case BottleMeta.GLASS:
			return "bottleGlass";
		case BottleMeta.GAS:
			return "bottleNaturalGas";
		case BottleMeta.FISH_FOOD:
			return "bottleFishFood";
		case BottleMeta.RUTILE:
			return "bottleRutile";
		case BottleMeta.QUICKLIME:
			return "bottleQuicklime";
		case BottleMeta.SALT:
			return "bottleSalt";
		case BottleMeta.EMPTY:
			return "bottleEmpty";
		case BottleMeta.ELECTRUM:
			return "bottleElectrum";
		case BottleMeta.WATER:
			return "bottleWater";
		case BottleMeta.LAVA:
			return "bottleLava";
		case BottleMeta.MILK:
			return "bottleMilk";
		case BottleMeta.CUSTARD:
			return "bottleCustard";
		case BottleMeta.CUSTARD_BASIC:
			return "bottleNormalCustard";
		case BottleMeta.FISH_FOOD_BASIC:
			return "bottleNormalFishFood";
		case BottleMeta.FISH_OIL_BASIC:
			return "bottleNormalFishOil";
		case BottleMeta.GAS_BASIC:
			return "bottleNormalNaturalGas";
		case BottleMeta.MILK_BASIC:
			return "bottleNormalMilk";
		case BottleMeta.HP_WATER:
			return "bottleHPWater";
		default:
			return "container";
		}
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case BottleMeta.BRONZE:
			return OreDictionary.getOres("ingotBronze").size() > 0;
		case BottleMeta.LEAD:
			return OreDictionary.getOres("ingotLead").size() > 0;
		case BottleMeta.NICKEL:
			return OreDictionary.getOres("ingotNickel").size() > 0;
		case BottleMeta.SILVER:
			return OreDictionary.getOres("ingotSilver").size() > 0;
		case BottleMeta.STEEL:
			return OreDictionary.getOres("ingotSteel").size() > 0;
		case BottleMeta.TIN:
			return OreDictionary.getOres("ingotTin").size() > 0;
		case BottleMeta.FISH_OIL:
			return (Modules.isActive(Modules.fishery));
		case BottleMeta.FISH_FOOD:
			return (Modules.isActive(Modules.fishery));
		default:
			return true;
		}
	}
}
