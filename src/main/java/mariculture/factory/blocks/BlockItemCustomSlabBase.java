package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockItemCustomSlabBase extends ItemBlock {
	private final boolean isFullBlock;

	/** Instance of BlockHalfSlab. */
	private final BlockHalfSlab theHalfSlab;

	/** The double-slab block corresponding to this item. */
	private final BlockHalfSlab doubleSlab;

	public BlockItemCustomSlabBase(int par1, BlockHalfSlab par2BlockHalfSlab, BlockHalfSlab par3BlockHalfSlab, boolean par4) {
		super(par1);
		this.theHalfSlab = par2BlockHalfSlab;
		this.doubleSlab = par3BlockHalfSlab;
		this.isFullBlock = par4;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1) {
		return Block.blocksList[this.itemID].getIcon(2, par1);
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	public int getMetadata(int par1) {
		return par1;
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return this.theHalfSlab.getFullSlabName(par1ItemStack.getItemDamage());
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8,
			float par9, float par10) {
		if (this.isFullBlock) {
			return super.onItemUse(stack, player, world, x, y, z, side, par8, par9, par10);
		} else if (stack.stackSize == 0) {
			return false;
		} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else {
			int i1 = world.getBlockId(x, y, z);
			int j1 = world.getBlockMetadata(x, y, z);
			int k1 = j1 & 7;
			boolean flag = (j1 & 8) != 0;

			if (PlansMeta.isTheSame(world, x, y, z, stack)
					&& ((side == 1 && !flag || side == 0 && flag) && i1 == this.theHalfSlab.blockID)) {
				if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z))
						&& world.setBlock(x, y, z, this.doubleSlab.blockID, k1, 3)) {
					this.doubleSlab.onBlockPlacedBy(world, x, y, z, player, stack);

					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F),
							this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F,
							this.doubleSlab.stepSound.getPitch() * 0.8F);
					--stack.stackSize;
				}

				return true;
			} else {
				return this.func_77888_a(stack, player, world, x, y, z, side) ? true : super.onItemUse(stack, player, world, x,
						y, z, side, par8, par9, par10);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns true if the given ItemBlock can be placed on the given side of the given block position.
	 */
	public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5,
			EntityPlayer par6EntityPlayer, ItemStack par7ItemStack) {
		int i1 = par2;
		int j1 = par3;
		int k1 = par4;
		int l1 = par1World.getBlockId(par2, par3, par4);
		int i2 = par1World.getBlockMetadata(par2, par3, par4);
		int j2 = i2 & 7;
		boolean flag = (i2 & 8) != 0;

		if ((par5 == 1 && !flag || par5 == 0 && flag) && l1 == this.theHalfSlab.blockID && j2 == par7ItemStack.getItemDamage()) {
			return true;
		} else {
			if (par5 == 0) {
				--par3;
			}

			if (par5 == 1) {
				++par3;
			}

			if (par5 == 2) {
				--par4;
			}

			if (par5 == 3) {
				++par4;
			}

			if (par5 == 4) {
				--par2;
			}

			if (par5 == 5) {
				++par2;
			}

			l1 = par1World.getBlockId(par2, par3, par4);
			i2 = par1World.getBlockMetadata(par2, par3, par4);
			j2 = i2 & 7;
			flag = (i2 & 8) != 0;
			return l1 == this.theHalfSlab.blockID && j2 == par7ItemStack.getItemDamage() ? true : super.canPlaceItemBlockOnSide(
					par1World, i1, j1, k1, par5, par6EntityPlayer, par7ItemStack);
		}
	}

	private boolean func_77888_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
		if (side == 0) {
			--y;
		}

		if (side == 1) {
			++y;
		}

		if (side == 2) {
			--z;
		}

		if (side == 3) {
			++z;
		}

		if (side == 4) {
			--x;
		}

		if (side == 5) {
			++x;
		}

		int i1 = world.getBlockId(x, y, z);
		int j1 = world.getBlockMetadata(x, y, z);
		int k1 = j1 & 7;

		if (PlansMeta.isTheSame(world, x, y, z, stack) && (i1 == this.theHalfSlab.blockID && k1 == stack.getItemDamage())) {
			if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z))
					&& world.setBlock(x, y, z, this.doubleSlab.blockID, k1, 3)) {
				this.theHalfSlab.onBlockPlacedBy(world, x, y, z, player, stack);
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F),
						this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F,
						this.doubleSlab.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}
}
