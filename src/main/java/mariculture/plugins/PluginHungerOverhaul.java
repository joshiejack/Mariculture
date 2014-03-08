package mariculture.plugins;

import mariculture.plugins.Plugins.Plugin;
public class PluginHungerOverhaul extends Plugin {

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}
	/*
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
		Items.itemsList[Core.food.itemID] = null;
		Core.food = new ItemHungryFood(food).setUnlocalizedName("food");

		if (Modules.fishery.isActive()) {
			Items.itemsList[Fishery.bait.itemID] = null;
			Fishery.bait = new ItemHungryBait(bait).setUnlocalizedName("bait");
			
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
				new ItemStack(Fishery.fishyFood, 1, Fishery.squid.fishID), Items.bowlEmpty
			});
		}
	} */
}
