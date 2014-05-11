package mariculture.plugins;

import static mariculture.api.fishery.Loot.Rarity.GOOD;
import static mariculture.api.fishery.Loot.Rarity.JUNK;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodQuality;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.bloodmagic.BloodRodQuality;
import mariculture.plugins.bloodmagic.ItemBoundRod;
import mariculture.plugins.bloodmagic.ItemMobMagnetBloodEdition;
import mariculture.plugins.bloodmagic.RitualOfTheBloodRiver;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.api.bindingRegistry.BindingRegistry;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;

public class PluginAWWayofTime extends Plugin {
	public static final RodQuality BLOOD = new BloodRodQuality(0, 100, 20);
	public static Item rodBlood;

	@Override
	public void preInit() {
		rodBlood = new ItemBoundRod(ItemIds.rodBlood, BLOOD).setUnlocalizedName("rodBlood");
		RegistryHelper.register(new Object[] { rodBlood });
	}

	@Override
	public void init() {
		if (Modules.isActive(Modules.fishery)) {
			// Fishing
			Fishing.bait.addBait(new ItemStack(Item.rottenFlesh), 35);
			Fishing.bait.addBait(new ItemStack(ModItems.weakBloodShard), 75);
			Fishing.bait.addBait(new ItemStack(ModItems.demonBloodShard), 100);
			Fishing.quality.addBaitForQuality(new ItemStack(Item.rottenFlesh), BLOOD);
			Fishing.quality.addBaitForQuality(new ItemStack(ModItems.weakBloodShard), BLOOD);
			Fishing.quality.addBaitForQuality(new ItemStack(ModItems.demonBloodShard), BLOOD);

			addLoot(new ItemStack(ModItems.blankSlate), JUNK, 10);
			addLoot(new ItemStack(Block.web), JUNK, 10);
			addLoot(new ItemStack(ModItems.baseItems, 1, 3), GOOD, 10);
			addLoot(new ItemStack(ModItems.baseAlchemyItems, 1, 5), GOOD, 10);

			addLoot(new ItemStack(ModItems.simpleCatalyst), GOOD, 7);
			addLoot(new ItemStack(ModItems.baseItems, 1, 0), GOOD, 7);

			addLoot(new ItemStack(ModItems.weakFillingAgent), JUNK, 15);
			addLoot(new ItemStack(ModItems.baseAlchemyItems, 1, 3), JUNK, 15);
			addLoot(new ItemStack(ModItems.baseItems, 1, 1), GOOD, 15);
			addLoot(new ItemStack(ModItems.reinforcedSlate), JUNK, 20);
			addLoot(new ItemStack(ModItems.alchemyFlask), GOOD, 40);

			addLoot(new ItemStack(ModItems.standardFillingAgent), JUNK, 50);
			addLoot(new ItemStack(ModItems.aether), GOOD, 50);
			addLoot(new ItemStack(ModItems.baseAlchemyItems, 1, 4), GOOD, 45);
			addLoot(new ItemStack(ModItems.baseItems, 1, 4), GOOD, 45);

			addLoot(new ItemStack(ModItems.imbuedSlate), JUNK, 75);
			addLoot(new ItemStack(ModItems.enhancedFillingAgent), GOOD, 75);
			addLoot(new ItemStack(ModItems.itemKeyOfDiablo), GOOD, 75);
			addLoot(new ItemStack(ModItems.boundBoots), GOOD, 200);

			// Rituals
			Rituals.ritualList.add(new Rituals(1, 50000, new RitualOfTheBloodRiver(), "Ritual of the Blood River"));
			BindingRegistry.registerRecipe(new ItemStack(rodBlood), new ItemStack(Fishery.rodTitanium));
		}

		if (Modules.isActive(Modules.magic)) {
			Item.itemsList[Magic.magnet.itemID] = null;
			Magic.magnet = new ItemMobMagnetBloodEdition(ItemIds.magnet, 0).setUnlocalizedName("mobMagnet");
		}
	}

	private void addLoot(ItemStack stack, Rarity rarity, int chance) {
		RecipeHelper.addExactLoot(stack, BLOOD, rarity, chance, OreDictionary.WILDCARD_VALUE);
	}

	@Override
	public void postInit() {

	}
}
