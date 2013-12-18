package hungryfish;

import hungryfish.fish.HungryFishAngel;
import hungryfish.fish.HungryFishBass;
import hungryfish.fish.HungryFishBlaze;
import hungryfish.fish.HungryFishButterfly;
import hungryfish.fish.HungryFishCatfish;
import hungryfish.fish.HungryFishClown;
import hungryfish.fish.HungryFishCod;
import hungryfish.fish.HungryFishDamsel;
import hungryfish.fish.HungryFishDragon;
import hungryfish.fish.HungryFishElectricRay;
import hungryfish.fish.HungryFishEnder;
import hungryfish.fish.HungryFishGlow;
import hungryfish.fish.HungryFishGold;
import hungryfish.fish.HungryFishJelly;
import hungryfish.fish.HungryFishKoi;
import hungryfish.fish.HungryFishManOWar;
import hungryfish.fish.HungryFishMantaRay;
import hungryfish.fish.HungryFishMinnow;
import hungryfish.fish.HungryFishNether;
import hungryfish.fish.HungryFishNight;
import hungryfish.fish.HungryFishPerch;
import hungryfish.fish.HungryFishPiranha;
import hungryfish.fish.HungryFishPuffer;
import hungryfish.fish.HungryFishSalmon;
import hungryfish.fish.HungryFishSiamese;
import hungryfish.fish.HungryFishSquid;
import hungryfish.fish.HungryFishStingRay;
import hungryfish.fish.HungryFishTang;
import hungryfish.fish.HungryFishTetra;
import hungryfish.fish.HungryFishTuna;

import java.util.ArrayList;

import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.Modules;
import mariculture.core.util.RecipeRemover;
import mariculture.fishery.Fishery;
import mariculture.world.WorldPlus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "HungryFish", name = "Hungry Fish", version = "0.2", dependencies = "required-after:Mariculture")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class HungryFish {
	public static final String modid = "hungryfish";

	@Mod.Instance("HungryFish")
	public static HungryFish instance = new HungryFish();

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
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
			GameRegistry.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.CALAMARI), new Object[] {
				new ItemStack(Fishery.fishyFood, 1, Fishery.squid.fishID), Item.bowlEmpty
			});
		}

		if (Modules.world.isActive()) {
			Item.itemsList[WorldPlus.coral.blockID] = null;
			Item.itemsList[WorldPlus.coral.blockID] = new ItemHungryCoral(BlockIds.coral - 256, WorldPlus.coral).setUnlocalizedName("coral");
		}
	}
}
