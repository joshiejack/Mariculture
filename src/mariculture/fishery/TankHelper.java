package mariculture.fishery;

import java.util.ArrayList;


import mariculture.core.Core;
import mariculture.core.helpers.InventoHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TankHelper {
	public static int getTankSize(ArrayList<String> array) {
		if (array.hashCode() == -1322479784) {
			return 1;
		}

		if (array.hashCode() == 802800330) {
			return 2;
		}

		if (array.hashCode() == -1206851741) {
			return 3;
		}

		if (array.hashCode() == -446444789) {
			return 4;
		}

		if (array.hashCode() == 1655279786) {
			return 5;
		}

		return -1;
	}

	public static ArrayList<String> getSurroundingArray(TileEntity tile) {
		ArrayList<String> string = new ArrayList<String>();
		World world = tile.worldObj;
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		int size = 5;
		int height = 2;

		if (isAcceptableForTank(world, x + 5, y, z)) {
			size = 5;
			if (isAcceptableForTank(world, x + 5, y + 3, z)) {
				height = 4;
			} else if (isAcceptableForTank(world, x + 5, y + 2, z)) {
				height = 3;
			}
		}

		if (isAcceptableForTank(world, x + 3, y, z)) {
			size = 3;
			if (isAcceptableForTank(world, x + 3, y + 2, z)) {
				height = 3;
			}
		}

		if (isAcceptableForTank(world, x + 2, y, z)) {
			size = 2;
		}

		int id = 0;

		for (int i = x - size; i < x + size + 1; i++) {
			for (int j = y; j < y + height; j++) {
				for (int k = z - size; k < z + size + 1; k++) {
					if (isAcceptableForTank(world, i, j, k)) {
						string.add("t");
					} else if (world.getBlockMaterial(i, j, k) == Material.water) {
						string.add("w");
					} else if (world.getBlockMaterial(i, j, k) == Material.lava && world.provider.isHellWorld) {
						string.add("w");
					} else {
						string.add(" ");
					}

					id++;
				}
			}
		}

		return string;
	}

	private static boolean isAcceptableForTank(World world, int x, int y, int z) {
		if (world.isAirBlock(x, y, z)) {
			return false;
		}

		int blockID = world.getBlockId(x, y, z);

		if (isAcceptableMaterial(world, x, y, z)) {
			if (world.getBlockTileEntity(x, y, z) == null) {
				return true;
			}
		}

		return false;
	}

	private static boolean isAcceptableMaterial(World world, int x, int y, int z) {
		Material material = world.getBlockMaterial(x, y, z);
		
		if(material == Material.glass) {
			return true;
		} else if(material == Material.iron) {
			return true;
		} else if(material == Material.rock) {
			return true;
		} else if(material == Material.wood) {
			return true;
		}
		
		return false;
	}

	public static int getDistance(int tankSize) {
		switch (tankSize) {
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 3;
		case 4:
			return 5;
		case 5:
			return 5;
		}

		return 0;
	}
}
