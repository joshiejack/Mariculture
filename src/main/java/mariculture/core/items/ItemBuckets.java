package mariculture.core.items;

import java.util.List;

import mariculture.core.Core;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ItemBuckets extends ItemMariculture {
	public ItemBuckets() {
		maxStackSize = 1;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
		int amount = fluid == null? 0: fluid.amount;
		list.add(FluidHelper.getFluidName(fluid));
		FluidHelper.getFluidQty(list, fluid, -1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);
		if (movingobjectposition == null) {
			return stack;
		} else {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!world.canMineBlock(player, i, j, k)) {
					return stack;
				}

				if (movingobjectposition.sideHit == 0) {
					--j;
				}

				if (movingobjectposition.sideHit == 1) {
					++j;
				}

				if (movingobjectposition.sideHit == 2) {
					--k;
				}

				if (movingobjectposition.sideHit == 3) {
					++k;
				}

				if (movingobjectposition.sideHit == 4) {
					--i;
				}

				if (movingobjectposition.sideHit == 5) {
					++i;
				}

				if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
					return stack;
				}

				if (this.tryPlaceContainedLiquid(stack, world, i, j, k) && !player.capabilities.isCreativeMode) {
					return new ItemStack(Items.bucket);
				}

			}

			return stack;
		}
	}
	
	public boolean tryPlaceContainedLiquid(ItemStack stack, World world, int x, int y, int z) {
		Block block = getFluid(stack.getItemDamage());
		Material material = world.getBlock(x, y, z).getMaterial();
		boolean flag = !material.isSolid();
		if (!world.isAirBlock(x, y, z) && !flag) {
			return false;
		} else {
			if (!world.isRemote && flag && !material.isLiquid()) {
				world.func_147480_a(x, y, z, true);
			}

			world.setBlock(x, y, z, block, 0, 3);
			return true;
		}
	}

	public Block getFluid(int meta) {
		if(meta == BucketMeta.PRESSURE) return Core.hpWaterBlock;
		else if (meta == BucketMeta.DIRT) return Blocks.dirt;
		if(Modules.isActive(Modules.fishery)) {
			if(meta == BucketMeta.FISH_OIL) return Fishery.fishOilBlock;
			if(meta == BucketMeta.CUSTARD)	return Fishery.custardBlock;
		}

		return Blocks.water;
	}
	
	public ItemStack getBucket(Block block) {
		if(block == Core.hpWaterBlock) return new ItemStack(this, 1, BucketMeta.PRESSURE);
		else if(Modules.isActive(Modules.fishery)) {
			if(block == Fishery.fishOilBlock) return new ItemStack(this, 1, BucketMeta.FISH_OIL);
			else if(block == Fishery.custardBlock) return new ItemStack(this, 1, BucketMeta.CUSTARD);
			else return null;
		} else return null;
 	}
	
	@Override
	public int getMetaCount() {
		return BucketMeta.COUNT;
	}
	
	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case BucketMeta.CUSTARD:
			return "custardBucket";
		case BucketMeta.DIRT:
			return "dirt";
		case BucketMeta.FISH_OIL:
			return "fishoil";
		case BucketMeta.PRESSURE:
			return "hpwater";
		default:
			return "container";
		}
	}
}