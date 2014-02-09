package mariculture.api.fishery;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ILootHandler {
	/**
	 * Add any new type of loot
	 * 
	 * @param The item you wish to add as fish loot
	 * @param Object
	 * @param EnumRodQuality
	 *            quality the type of rod quality that is needed as a minimum to
	 *            catch
	 * @param int chance, the chance to catch this loot, Example values: record
	 *        2000, expBottle 100, Diamond 5000, leatherBoots 50
	 * @param (Optional) Dimension ID: -1 for Nether, 1 for End. If ignored this
	 *        will default to the overworld and any dimension other than the end
	 *        and the nether
	 *
	 * 
	 * Example (Adds Void Bottle to Fishing Loot for the end dimension):
	 *  Fishing.loot.addLoot(new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_VOID), 
	 *  															new Object[] { EnumRodQuality.OLD, 75, 1 }); **/
	public void addLoot(ItemStack stack, Object... args);

	/** This will generate random loot making use of the parameters specified **/
	public ItemStack getLoot(Random rand, EnumRodQuality quality, World world, int x, int y, int z);
}
