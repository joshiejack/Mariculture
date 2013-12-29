package mariculture.core.helpers;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.TileLiquifier;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HeatHelper {
	public static int getTileTemperature(World world, int xCoord, int yCoord, int zCoord, ItemStack[] upgrades) {
		int heat = 0;
		//Get the Base temperature of the blocks around you
		heat+=getWorldHeat(world, xCoord, yCoord, zCoord);
		
		int upgradeBonus = MaricultureHandlers.upgrades.getData("temp", (IUpgradable) world.getBlockTileEntity(xCoord, yCoord, zCoord));
		if(world.getBlockTileEntity(xCoord, yCoord, zCoord) instanceof TileLiquifier) {
			upgradeBonus*=20;
		}
		heat+= upgradeBonus;
				
		return heat;
	}
	
	public static int getWorldHeat(World world, int x, int y, int z) {
		int heat = 0;
		heat+= getHeatFor(world, x + 1, y, z);
		heat+= getHeatFor(world, x - 1, y, z);
		heat+= getHeatFor(world, x, y + 1, z);
		heat+= getHeatFor(world, x, y - 1, z);
		heat+= getHeatFor(world, x, y, z + 1);
		heat+= getHeatFor(world, x, y, z - 1);
		
		EnumBiomeType type = MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(x, z));
		heat+= (3 - type.getCoolingSpeed());
		return heat;
	}
	
	public static int getHeatFor(World world, int x, int y, int z) {
		int heat = 0;
		heat = (world.getBlockMaterial(x, y, z) == Material.craftedSnow) ? heat - 1 : heat;
		heat = (world.getBlockMaterial(x, y, z) == Material.snow) ? heat - 1 : heat;
		heat = (world.getBlockMaterial(x, y, z) == Material.ice) ? heat - 2 : heat;
		heat = (world.getBlockMaterial(x, y, z) == Material.fire) ? heat + 1 : heat;
		heat = (world.getBlockMaterial(x, y, z) == Material.lava) ? heat + 2 : heat;

		return heat;
	}
}
