package mariculture.plugins;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodQuality;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.ItemIds;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.bloodmagic.ItemBoundRod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import WayofTime.alchemicalWizardry.ModItems;

public class PluginAWWayofTime extends Plugin {
	public static Item rodBlood;
	public static final RodQuality BLOOD = new RodQuality(0, 101, 5);
	
	@Override
	public void preInit() {
		rodBlood = new ItemBoundRod(ItemIds.rodBlood, BLOOD).setUnlocalizedName("rodBlood");
		RegistryHelper.register(new Object[] { rodBlood });
	}

	@Override
	public void init() {
		Fishing.bait.addBait(new ItemStack(Item.rottenFlesh), 35);
		Fishing.bait.addBait(new ItemStack(ModItems.weakBloodShard), 75);
		Fishing.bait.addBait(new ItemStack(ModItems.demonBloodShard), 100);
		Fishing.quality.addBaitForQuality(new ItemStack(Item.rottenFlesh), BLOOD);
		Fishing.quality.addBaitForQuality(new ItemStack(ModItems.weakBloodShard), BLOOD);
		Fishing.quality.addBaitForQuality(new ItemStack(ModItems.demonBloodShard), BLOOD);
		
		for(int i = 0; i <= 1; i++) {
			Fishing.loot.addLoot(Fishery.gender.addDNA(Fishing.fishHelper.makePureFish(Fishery.angel), i), new Object[] { BLOOD, 10 });
			Fishing.loot.addLoot(Fishery.gender.addDNA(Fishing.fishHelper.makePureFish(Fishery.gold), i), new Object[] { BLOOD, 10 });
			Fishing.loot.addLoot(Fishery.gender.addDNA(Fishing.fishHelper.makePureFish(Fishery.perch), i), new Object[] { BLOOD, 10 });
			Fishing.loot.addLoot(Fishery.gender.addDNA(Fishing.fishHelper.makePureFish(Fishery.salmon), i), new Object[] { BLOOD, 10 });
		}

		Fishing.loot.addLoot(new ItemStack(ModItems.aether), new Object[] { BLOOD, 10 });
	}

	@Override
	public void postInit() {
		
	}
}
