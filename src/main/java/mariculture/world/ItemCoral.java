package mariculture.world;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.blocks.ItemBlockMariculture;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoral extends ItemMariculture {
	private Icon[] icons;
	private int spawnID;

	public ItemCoral(int i, Block block) {
		super(i);
		this.spawnID = block.blockID;
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
	public Icon getIconFromDamage(int dmg) {
		if (dmg < getMetaCount()) {
			return icons[dmg];
		}

		return icons[0];
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		int i1 = world.getBlockId(x, y, z);

		if (i1 == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID) {
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
				Block block = Block.blocksList[this.spawnID];
				int j1 = stack.getItemDamage();
				if (world.setBlock(x, y, z, this.spawnID, j1, 2)) {
					if (world.getBlockId(x, y, z) == this.spawnID) {
						Block.blocksList[this.spawnID].onBlockPlacedBy(world, x, y, z, player, stack);
						Block.blocksList[this.spawnID].onPostBlockPlaced(world, x, y, z, j1);
					}

					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F),
							(double) ((float) z + 0.5F), block.stepSound.getPlaceSound(),
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
		
		if (world.getBlockMaterial(x, y + 1, z) != Material.water) {
			return false;
		}

		if (stack.getItemDamage() == CoralMeta.KELP) {
			if (world.getBlockId(x, y - 1, z) == Block.gravel.blockID) {
				return true;
			}
			if (world.getBlockId(x, y - 1, z) == Block.cobblestone.blockID) {
				return true;
			}
			if (world.getBlockId(x, y - 1, z) == Block.cobblestoneMossy.blockID) {
				return true;
			}
			if (world.getBlockId(x, y - 1, z) == Core.oreBlocks.blockID
					&& world.getBlockMetadata(x, y - 1, z) == OresMeta.CORAL_ROCK) {
				return true;
			}
			if (world.getBlockId(x, y - 1, z) == Block.sand.blockID) {
				return true;
			}

			if (world.getBlockId(x, y - 1, z) == WorldPlus.coral.blockID
					&& world.getBlockMetadata(x, y - 1, z) <= CoralMeta.KELP_MIDDLE) {
				return true;
			}
		}

		if (stack.getItemDamage() > CoralMeta.KELP_MIDDLE) {
			if (world.getBlockId(x, y - 1, z) == Block.cobblestone.blockID) {
				return true;
			}
			if (world.getBlockId(x, y - 1, z) == Block.cobblestoneMossy.blockID) {
				return true;
			}
			if (world.getBlockId(x, y - 1, z) == Core.oreBlocks.blockID && world.getBlockMetadata(x, y - 1, z) == OresMeta.CORAL_ROCK) {
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
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "coral_" + getName(new ItemStack(this.itemID, 1, i)));
		}
	}
}