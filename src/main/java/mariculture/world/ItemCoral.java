package mariculture.world;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoral extends ItemMariculture {
	private IIcon[] icons;
	private Block spawnBlock;

	public ItemCoral(Block block) {
		this.spawnBlock = block;
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case CoralMeta.KELP:
			return "kelp";
		case CoralMeta.KELP_MIDDLE:
			return "kelp_middle";
		case CoralMeta.CORAL_BLUE:
			return"blue";
		case CoralMeta.CORAL_BRAIN:
			return "yellow";
		case CoralMeta.CORAL_CANDYCANE:
			return "magenta";
		case CoralMeta.CORAL_CUCUMBER:
			return "brown";
		case CoralMeta.CORAL_ORANGE:
			return "orange";
		case CoralMeta.CORAL_PINK:
			return "pink";
		case CoralMeta.CORAL_PURPLE:
			return "purple";
		case CoralMeta.CORAL_RED:
			return "red";
		case CoralMeta.CORAL_GREY:
			return "grey";
		case CoralMeta.CORAL_LIGHT_GREY:
			return "lightgrey";
		case CoralMeta.CORAL_WHITE:
			return "white";
		default:
			return "coral";
		}
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg < getMetaCount()) {
			return icons[dmg];
		}

		return icons[0];
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		Block block = world.getBlock(x, y, z);
		if (block == Blocks.snow && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
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
		}

		if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else if (stack.stackSize == 0) {
			return false;
		} else {
			if (canPlaceBlockOnSide(world, x, y, z, side, stack)) {
				int j1 = stack.getItemDamage();
				if (world.setBlock(x, y, z, spawnBlock, j1, 2)) {
					if (world.getBlock(x, y, z) == spawnBlock) {
						spawnBlock.onBlockPlacedBy(world, x, y, z, player, stack);
						spawnBlock.onPostBlockPlaced(world, x, y, z, j1);
					}

					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F),
							(double) ((float) z + 0.5F), block.stepSound.func_150496_b(),
							(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
					--stack.stackSize;
				}
			}

			return true;
		}
	}

	private boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side, ItemStack stack) {
		if (side != 1) {
			return false;
		}
		
		if (world.getBlock(x, y + 1, z).getMaterial() != Material.water) {
			return false;
		}

		if (stack.getItemDamage() == CoralMeta.KELP) {
			if (world.getBlock(x, y - 1, z) == Blocks.gravel) {
				return true;
			}
			if (world.getBlock(x, y - 1, z) == Blocks.cobblestone) {
				return true;
			}
			if (world.getBlock(x, y - 1, z) == Blocks.mossy_cobblestone) {
				return true;
			}
			if (world.getBlock(x, y - 1, z) == Core.oreBlocks && world.getBlockMetadata(x, y - 1, z) == OresMeta.CORAL_ROCK) {
				return true;
			}
			if (world.getBlock(x, y - 1, z) == Blocks.sand) {
				return true;
			}

			if (world.getBlock(x, y - 1, z) == WorldPlus.coral && world.getBlockMetadata(x, y - 1, z) <= CoralMeta.KELP_MIDDLE) {
				return true;
			}
		}

		if (stack.getItemDamage() > CoralMeta.KELP_MIDDLE) {
			if (world.getBlock(x, y - 1, z) == Blocks.cobblestone) {
				return true;
			}
			if (world.getBlock(x, y - 1, z) == Blocks.mossy_cobblestone) {
				return true;
			}
			if (world.getBlock(x, y - 1, z) == Core.oreBlocks && world.getBlockMetadata(x, y - 1, z) == OresMeta.CORAL_ROCK) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int getMetaCount() {
		return CoralMeta.COUNT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "coral_" + getName(new ItemStack(this, 1, i)));
		}
	}
}