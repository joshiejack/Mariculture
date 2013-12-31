package mariculture.plugins;

import java.util.ArrayList;

import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.Modules;
import mariculture.core.util.RecipeRemover;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.hungryfish.ItemHungryBait;
import mariculture.plugins.hungryfish.ItemHungryCoral;
import mariculture.plugins.hungryfish.ItemHungryFood;
import mariculture.plugins.hungryfish.fish.HungryFishAngel;
import mariculture.plugins.hungryfish.fish.HungryFishBass;
import mariculture.plugins.hungryfish.fish.HungryFishBlaze;
import mariculture.plugins.hungryfish.fish.HungryFishButterfly;
import mariculture.plugins.hungryfish.fish.HungryFishCatfish;
import mariculture.plugins.hungryfish.fish.HungryFishClown;
import mariculture.plugins.hungryfish.fish.HungryFishCod;
import mariculture.plugins.hungryfish.fish.HungryFishDamsel;
import mariculture.plugins.hungryfish.fish.HungryFishDragon;
import mariculture.plugins.hungryfish.fish.HungryFishElectricRay;
import mariculture.plugins.hungryfish.fish.HungryFishEnder;
import mariculture.plugins.hungryfish.fish.HungryFishGlow;
import mariculture.plugins.hungryfish.fish.HungryFishGold;
import mariculture.plugins.hungryfish.fish.HungryFishJelly;
import mariculture.plugins.hungryfish.fish.HungryFishKoi;
import mariculture.plugins.hungryfish.fish.HungryFishManOWar;
import mariculture.plugins.hungryfish.fish.HungryFishMantaRay;
import mariculture.plugins.hungryfish.fish.HungryFishMinnow;
import mariculture.plugins.hungryfish.fish.HungryFishNether;
import mariculture.plugins.hungryfish.fish.HungryFishNight;
import mariculture.plugins.hungryfish.fish.HungryFishPerch;
import mariculture.plugins.hungryfish.fish.HungryFishPiranha;
import mariculture.plugins.hungryfish.fish.HungryFishPuffer;
import mariculture.plugins.hungryfish.fish.HungryFishSalmon;
import mariculture.plugins.hungryfish.fish.HungryFishSiamese;
import mariculture.plugins.hungryfish.fish.HungryFishSquid;
import mariculture.plugins.hungryfish.fish.HungryFishStingRay;
import mariculture.plugins.hungryfish.fish.HungryFishTang;
import mariculture.plugins.hungryfish.fish.HungryFishTetra;
import mariculture.plugins.hungryfish.fish.HungryFishTuna;
import mariculture.world.WorldPlus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginHungerOverhaul extends Plugin {
	public PluginHungerOverhaul(String name) {
		super(name);
	}

	@Override
	public void preInit() {
		return;
	}

	@Override
	public void init() {
		return;
	}

	@Override
	public void postInit() {
		Item.itemsList[Core.food.itemID] = null;
		Core.food = new ItemHungryFood(ItemIds.food).setUnlocalizedName("food");

		if (Modules.fishery.isActive()) {
			Item.itemsList[Fishery.bait.itemID] = null;
			Fishery.bait = new ItemHungryBait(ItemIds.bait).setUnlocalizedName("bait");
			
			FishSpecies.speciesList = new ArrayList();
			Fishery.cod = new HungryFishCod(0);
			Fishery.perch = new HungryFishPerch(1);
			Fishery.tuna = new HungryFishTuna(2);
			Fishery.nether = new HungryFishNether(3);
			Fishery.glow = new HungryFishGlow(4);
			Fishery.blaze = new HungryFishBlaze(5);
			Fishery.night = new HungryFishNight(6);
			Fishery.ender = new HungryFishEnder(7);
			Fishery.dragon = new HungryFishDragon(8);
			Fishery.minnow = new HungryFishMinnow(9);
			Fishery.salmon = new HungryFishSalmon(10);
			Fishery.bass = new HungryFishBass(11);
			Fishery.tetra = new HungryFishTetra(12);
			Fishery.catfish = new HungryFishCatfish(13);
			Fishery.piranha = new HungryFishPiranha(14);
			Fishery.stingRay = new HungryFishStingRay(15);
			Fishery.mantaRay = new HungryFishMantaRay(16);
			Fishery.electricRay = new HungryFishElectricRay(17);
			Fishery.damsel = new HungryFishDamsel(18);
			Fishery.angel = new HungryFishAngel(19);
			Fishery.puffer = new HungryFishPuffer(20);
			Fishery.squid = new HungryFishSquid(21);
			Fishery.jelly = new HungryFishJelly(22);
			Fishery.manOWar = new HungryFishManOWar(23);
			Fishery.gold = new HungryFishGold(24);
			Fishery.siamese = new HungryFishSiamese(25);
			Fishery.koi = new HungryFishKoi(26);
			Fishery.butterfly = new HungryFishButterfly(27);
			Fishery.tang = new HungryFishTang(28);
			Fishery.clown = new HungryFishClown(29);

			RecipeRemover.remove(new ItemStack(Core.food, 3, FoodMeta.CALAMARI));
			RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.CALAMARI), new Object[] {
				new ItemStack(Fishery.fishyFood, 1, Fishery.squid.fishID), Item.bowlEmpty
			});
		}

		if (Modules.world.isActive()) {
			Item.itemsList[WorldPlus.coral.blockID] = null;
			Item.itemsList[WorldPlus.coral.blockID] = new ItemHungryCoral(BlockIds.coral - 256, WorldPlus.coral).setUnlocalizedName("coral");
		}
	}
}
