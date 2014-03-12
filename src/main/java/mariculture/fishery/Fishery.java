package mariculture.fishery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.blocks.BlockItemNet;
import mariculture.fishery.blocks.BlockNeonLamp;
import mariculture.fishery.blocks.BlockSift;
import mariculture.fishery.blocks.TileAutofisher;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileFishTank;
import mariculture.fishery.blocks.TileIncubator;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.fish.FishAngel;
import mariculture.fishery.fish.FishBass;
import mariculture.fishery.fish.FishBlaze;
import mariculture.fishery.fish.FishButterfly;
import mariculture.fishery.fish.FishCatfish;
import mariculture.fishery.fish.FishClown;
import mariculture.fishery.fish.FishCod;
import mariculture.fishery.fish.FishDamsel;
import mariculture.fishery.fish.FishDragon;
import mariculture.fishery.fish.FishElectricRay;
import mariculture.fishery.fish.FishEnder;
import mariculture.fishery.fish.FishGlow;
import mariculture.fishery.fish.FishGold;
import mariculture.fishery.fish.FishJelly;
import mariculture.fishery.fish.FishKoi;
import mariculture.fishery.fish.FishManOWar;
import mariculture.fishery.fish.FishMantaRay;
import mariculture.fishery.fish.FishMinnow;
import mariculture.fishery.fish.FishNether;
import mariculture.fishery.fish.FishNight;
import mariculture.fishery.fish.FishPerch;
import mariculture.fishery.fish.FishPiranha;
import mariculture.fishery.fish.FishPuffer;
import mariculture.fishery.fish.FishSalmon;
import mariculture.fishery.fish.FishSiamese;
import mariculture.fishery.fish.FishSquid;
import mariculture.fishery.fish.FishStingRay;
import mariculture.fishery.fish.FishTang;
import mariculture.fishery.fish.FishTetra;
import mariculture.fishery.fish.FishTuna;
import mariculture.fishery.fish.dna.FishDNAFertility;
import mariculture.fishery.fish.dna.FishDNAFoodUsage;
import mariculture.fishery.fish.dna.FishDNAGender;
import mariculture.fishery.fish.dna.FishDNALifespan;
import mariculture.fishery.fish.dna.FishDNASpecies;
import mariculture.fishery.fish.dna.FishDNATankSize;
import mariculture.fishery.fish.dna.FishDNAWorkEthic;
import mariculture.fishery.items.ItemBait;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemFluxRod;
import mariculture.fishery.items.ItemRod;
import net.minecraft.block.Block;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Fishery extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
	public static Block siftBlock;
	public static Block lampsOff;
	public static Block lampsOn;

	public static Item bait;
	public static Item rodWood;
	public static Item rodTitanium;
	public static Item rodReed;
	public static Item rodFlux;
	public static Item fishy;
	public static Item net;

	public static Fluid fishFood;
	public static Fluid fishOil;
	public static Fluid milk;
	public static Fluid custard;
	
	public static FishDNA species;
	public static FishDNA gender;
	public static FishDNA lifespan;
	public static FishDNA fertility;
	public static FishDNA foodUsage;
	public static FishDNA tankSize;
	public static FishDNA production;

	public static FishSpecies nether;
	public static FishSpecies glow;
	public static FishSpecies blaze;
	public static FishSpecies night;
	public static FishSpecies ender;
	public static FishSpecies dragon;
	public static FishSpecies minnow;
	public static FishSpecies salmon;
	public static FishSpecies bass;
	public static FishSpecies tetra;
	public static FishSpecies catfish;
	public static FishSpecies piranha;
	public static FishSpecies cod;
	public static FishSpecies perch;
	public static FishSpecies tuna;
	public static FishSpecies stingRay;
	public static FishSpecies mantaRay;
	public static FishSpecies electricRay;
	public static FishSpecies damsel;
	public static FishSpecies angel;
	public static FishSpecies puffer;
	public static FishSpecies squid;
	public static FishSpecies jelly;
	public static FishSpecies manOWar;
	public static FishSpecies gold;
	public static FishSpecies siamese;
	public static FishSpecies koi;
	public static FishSpecies butterfly;
	public static FishSpecies tang;
	public static FishSpecies clown;

	@Override
	public void registerHandlers() {
		if(Fishing.rodHandler == null) Fishing.rodHandler = new FishingRodHandler();
		if(Fishing.fishHelper == null) Fishing.fishHelper = new FishHelper();
		Fishing.bait = new BaitHandler();
		Fishing.mutation = new FishMutationHandler();
		Fishing.food = new FishFoodHandler();
		Fishing.sifter = new SifterHandler();
		Fishing.loot = new FishingLootHandler();
		MinecraftForge.EVENT_BUS.register(new FisheryEventHandler());
		MinecraftForge.EVENT_BUS.register(new BaitListingsHandler());
	}

	@Override
	public void registerBlocks() {
		siftBlock = new BlockSift().setStepSound(Block.soundTypeWood).setHardness(1F).setBlockName("siftBlock");
		lampsOff = new BlockNeonLamp(true).setBlockName("lampsOff");
		lampsOn = new BlockNeonLamp(false).setBlockName("lampsOn");
		
		GameRegistry.registerTileEntity(TileAutofisher.class, "tileEntityAutofisher");
		GameRegistry.registerTileEntity(TileSift.class, "tileEntitySift");
		GameRegistry.registerTileEntity(TileIncubator.class, "tileIncubator");
		GameRegistry.registerTileEntity(TileFeeder.class, "tileFeeder");
		GameRegistry.registerTileEntity(TileFishTank.class, "tileFishTank");

		RegistryHelper.register(new Object[] { siftBlock, lampsOff, lampsOn });
		
		FluidDictionary.fish_food = Core.addFluid("food.fish", fishFood, 512, FluidContainerMeta.BOTTLE_FISH_FOOD);
		FluidDictionary.fish_oil = Core.addFluid("oil.fish", fishOil, 2000, FluidContainerMeta.BOTTLE_FISH_OIL);
		FluidDictionary.milk = Core.addFluid("milk", milk, 2000, FluidContainerMeta.BOTTLE_MILK);
		FluidDictionary.custard = Core.addFluid("custard", custard, 2000, FluidContainerMeta.BOTTLE_CUSTARD);
		
		Core.registerVanillaBottle(FluidDictionary.fish_food, 256, FluidContainerMeta.BOTTLE_NORMAL_FISH_FOOD);
		Core.registerVanillaBottle(FluidDictionary.fish_oil, 1000, FluidContainerMeta.BOTTLE_NORMAL_FISH_OIL);
		Core.registerVanillaBottle(FluidDictionary.milk, 1000, FluidContainerMeta.BOTTLE_NORMAL_MILK);
		Core.registerVanillaBottle(FluidDictionary.custard, 1000, FluidContainerMeta.BOTTLE_NORMAL_CUSTARD);
		
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(FluidDictionary.custard, 250), 
				new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Items.bowl));
		
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(FluidDictionary.milk, 1000), 
				new ItemStack(Items.milk_bucket), new ItemStack(Items.bucket));
	}
	
	

	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityBass.class, "BassBomb", EntityIds.BASS, Mariculture.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityHook.class, "FishingHook", EntityIds.FISHING, Mariculture.instance, 80, 3, true);
	}

	private void registerFish() {
		species = new FishDNASpecies();
		gender = new FishDNAGender();
		lifespan = new FishDNALifespan();
		fertility = new FishDNAFertility();
		tankSize = new FishDNATankSize();
		foodUsage = new FishDNAFoodUsage();
		production = new FishDNAWorkEthic();

		cod = new FishCod(0);
		perch = new FishPerch(1);
		tuna = new FishTuna(2);

		nether = new FishNether(3);
		glow = new FishGlow(4);
		blaze = new FishBlaze(5);

		night = new FishNight(6);
		ender = new FishEnder(7);
		dragon = new FishDragon(8);

		minnow = new FishMinnow(9);
		salmon = new FishSalmon(10);
		bass = new FishBass(11);

		tetra = new FishTetra(12);
		catfish = new FishCatfish(13);
		piranha = new FishPiranha(14);

		stingRay = new FishStingRay(15);
		mantaRay = new FishMantaRay(16);
		electricRay = new FishElectricRay(17);

		damsel = new FishDamsel(18);
		angel = new FishAngel(19);
		puffer = new FishPuffer(20);

		squid = new FishSquid(21);
		jelly = new FishJelly(22);
		manOWar = new FishManOWar(23);

		gold = new FishGold(24);
		siamese = new FishSiamese(25);
		koi = new FishKoi(26);

		butterfly = new FishButterfly(27);
		tang = new FishTang(28);
		clown = new FishClown(29);

		Fishing.mutation.addMutation(nether, koi, glow, 6D);
		Fishing.mutation.addMutation(glow, nether, blaze, 10D);
		Fishing.mutation.addMutation(night, jelly, ender, 8D);
		Fishing.mutation.addMutation(ender, night, dragon, 5D);
		Fishing.mutation.addMutation(minnow, gold, salmon, 15D);
		Fishing.mutation.addMutation(salmon, minnow, bass, 20D);
		Fishing.mutation.addMutation(tetra, siamese, catfish, 12D);
		Fishing.mutation.addMutation(catfish, tetra, piranha, 8D);
		Fishing.mutation.addMutation(cod, gold, perch, 15D);
		Fishing.mutation.addMutation(perch, cod, tuna, 20D);
		Fishing.mutation.addMutation(stingRay, puffer, mantaRay, 8D);
		Fishing.mutation.addMutation(mantaRay, stingRay, electricRay, 10D);
		Fishing.mutation.addMutation(damsel, squid, angel, 7.5D);
		Fishing.mutation.addMutation(angel, damsel, puffer, 15D);
		Fishing.mutation.addMutation(squid, tuna, jelly, 20D);
		Fishing.mutation.addMutation(jelly, squid, manOWar, 10D);
		Fishing.mutation.addMutation(minnow, cod, gold, 25D);
		Fishing.mutation.addMutation(gold, stingRay, siamese, 15D);
		Fishing.mutation.addMutation(siamese, gold, koi, 7.5D);
		Fishing.mutation.addMutation(angel, tetra, butterfly, 10D);
		Fishing.mutation.addMutation(butterfly, piranha, tang, 7.5D);
		Fishing.mutation.addMutation(tang, butterfly, clown, 5D);
	}

	@Override
	public void registerItems() {
		bait = new ItemBait().setUnlocalizedName("bait");
		rodReed = new ItemRod(EnumRodQuality.OLD, 128, 24).setUnlocalizedName("rodReed");
		rodWood = new ItemRod(EnumRodQuality.GOOD, 448, 10).setUnlocalizedName("rodWood");
		rodTitanium = new ItemRod(EnumRodQuality.SUPER, 960, 16).setUnlocalizedName("rodTitanium");
		rodFlux = new ItemFluxRod().setUnlocalizedName("rodFlux");
		fishy = new ItemFishy().setUnlocalizedName("fishy").setCreativeTab(MaricultureTab.tabFish);
		net = new BlockItemNet().setUnlocalizedName("net");

		RegistryHelper.register(new Object[] { bait, rodReed, rodWood, rodTitanium, fishy, net, rodFlux });
	}

	@Override
	public void registerOther() {
		registerFish();
		MaricultureTab.tabFish.icon = Fishing.fishHelper.makePureFish(Fishery.cod);
	}

	@Override
	public void addRecipes() {
		String bad = "field_146039_d";
		String good = "field_146041_e";
		String fish = "field_146036_f";
		
		//Copy over the existing loots to my versions
		try {
			List good_loot = FishingLootHandler.getFinalStatic(EntityFishHook.class.getDeclaredField(good));
			List rare_loot = FishingLootHandler.getFinalStatic(EntityFishHook.class.getDeclaredField(good));
			List bad_loot = FishingLootHandler.getFinalStatic(EntityFishHook.class.getDeclaredField(bad));
			List fish_loot = FishingLootHandler.getFinalStatic(EntityFishHook.class.getDeclaredField(fish));
			
			for(EnumBiomeType biome: EnumBiomeType.values()) {
				EntityHook.bad_loot.put(biome, new ArrayList(bad_loot));
				EntityHook.fish_loot.put(biome, new ArrayList(fish_loot));
				EntityHook.rare_loot.put(biome, new ArrayList(rare_loot));
				EntityHook.good_loot.put(biome, new ArrayList(good_loot));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addBait();
		addDropletRecipes();
		addFishRecipes();
		FishingLoot.add();
		
		/* Fishing Book */
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.guides, 1, GuideMeta.FISHING), new Object[] {
			Items.book, rodReed
		});
		
		//Fishing Rods
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodReed), Items.sugar);
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodWood), new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK));
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodTitanium), new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_TITANIUM));
		
		//Flux Rod
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(rodFlux), 0), new Object[] {
			"  R", " RS", "B S", 'R', rodTitanium, 'S', Items.string, 'B', new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		// Log > 30000mB of Fish Oil > 45 Seconds = 1 Polished Log (or 30000mB for 8 Sticks)
		RecipeHelper.addVatItemRecipe(new ItemStack(Blocks.planks), FluidDictionary.fish_oil, 30000, 
				new ItemStack(Core.woodBlocks, 1, WoodMeta.POLISHED_LOG), 45);
		//1 Plank = 9000mB > 30 Seconds = 1 Polished Plank (or 36000mB for 8 Sticks)
		RecipeHelper.addVatItemRecipe(new ItemStack(Blocks.planks), FluidDictionary.fish_oil, 10000, 
				new ItemStack(Core.woodBlocks, 1, WoodMeta.POLISHED_PLANK), 30);
		//1 Stick = 5000mB > 15 Seconds or (40000mB for 8 Sticks)
		RecipeHelper.addVatItemRecipe(new ItemStack(Items.stick), FluidDictionary.fish_oil, 5000, 
				new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK), 15);
				
		//1 Polished Log = 4 Polished Planks
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.woodBlocks, 4, WoodMeta.POLISHED_PLANK), new Object[] {
			new ItemStack(Core.woodBlocks, 1, WoodMeta.POLISHED_LOG)
		});
				
		//2 Polished Planks = 4 Polished Sticks
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 4, CraftingMeta.POLISHED_STICK), new Object[] {
			"S", "S", 'S', new ItemStack(Core.woodBlocks, 1, WoodMeta.POLISHED_PLANK)
		});
				
		//Polished Titanium Rod > 6500mB Fish Oil + Titanium Rod
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.TITANIUM_ROD), 
				FluidDictionary.fish_oil, 6500, new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_TITANIUM), 30);
		
		/* Fishing Net, Autofisher and Sift */
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(net, 4, 0), new Object[] { "SWS", "WWW", "SWS",
					Character.valueOf('S'), "stickWood", 
					Character.valueOf('W'), Items.string }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(siftBlock), new Object[] { "PNP", "S S",
					Character.valueOf('S'), "stickWood", 
					Character.valueOf('P'), "plankWood",
					Character.valueOf('N'), net }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.AUTOFISHER), new Object[] { " F ", "RPR", "WBW", 
					Character.valueOf('W'), "logWood", 
					Character.valueOf('R'), new ItemStack(rodWood), 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('B'), new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD),
					Character.valueOf('P'), "plankWood"}));
		
		/* Fish Feeder and Incubator Blocks */
		GameRegistry.addRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.FISH_FEEDER), new Object[] { "WFW", "WCW", "WFW", 
				Character.valueOf('F'), new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
				Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), 
				Character.valueOf('C'), Blocks.chest });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.INCUBATOR_TOP), new Object[] {"DFD", "CHC", 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('D'), "dyeBrown", 
					Character.valueOf('C'), new ItemStack(Blocks.stained_hardened_clay, 1, 0), 
					Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER) }));

		//Incubator Base
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.INCUBATOR_BASE), new Object[] {
			"DBD", "CHC",
			Character.valueOf('C'), new ItemStack(Blocks.stained_hardened_clay, 1, 3),
			Character.valueOf('B'), new ItemStack(Core.batteryCopper, 1, OreDictionary.WILDCARD_VALUE),
			Character.valueOf('D'), "dyeLightBlue",
			Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER)
		});
		
		//FishTank
		RecipeHelper.addShapedRecipe(new ItemStack(Core.tankBlocks, 1, TankMeta.FISH), new Object[] {
			"AGA", "GFG", "AGA", 'A', "ingotAluminum", 'G', "glass",
			'F', new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		//Milk + 2 Sugar = Custard
		RecipeHelper.addVatItemRecipeResultFluid(new ItemStack(Items.sugar, 2, 0), FluidRegistry.getFluidStack(FluidDictionary.milk, 1000),
				FluidRegistry.getFluidStack(FluidDictionary.custard, 1000), 15);
	}

	private void addBait() {
		Fishing.bait.addBaitType(new ItemStack(bait, 1, BaitMeta.ANT), 10);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.dirt), 1, 2, 50));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.tallgrass, 0, 1), 1, 1, 5));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.log), 1, 1, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.log2), 1, 1, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.grass), 2, 3, 40));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.sapling), 1, 1, 4));
		
		Fishing.bait.addBaitType(new ItemStack(bait, 1, BaitMeta.WORM), 60);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Blocks.dirt), 2, 3, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Blocks.grass), 3, 5, 20));
		
		Fishing.bait.addBaitType(new ItemStack(bait, 1, BaitMeta.HOPPER), 35);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.leaves), 2, 3, 15));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.tallgrass, 0, 1), 2, 3, 20));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.grass), 2, 3, 25));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.sapling), 2, 5, 50));
		
		Fishing.bait.addBaitType(new ItemStack(bait, 1, BaitMeta.MAGGOT), 30);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.rotten_flesh), 1, 2, 60));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.beef), 14, 22, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.chicken), 6, 14, 20));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.porkchop), 10, 18, 15));
		
		Fishing.bait.addBaitType(new ItemStack(bait, 1, BaitMeta.BEE), 70);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Blocks.yellow_flower), 2, 3, 20));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Blocks.red_flower), 1, 2, 25));
		
		Fishing.bait.addBaitType(new ItemStack(Items.bread), 25);
		Fishing.bait.addBaitType(new ItemStack(Items.fish, 1, Fishery.minnow.fishID), 90);
		
		//Extra Sifter Recipes
		Fishing.bait.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.ANT), Arrays.asList(EnumRodQuality.DIRE, EnumRodQuality.OLD, EnumRodQuality.ELECTRIC));
		Fishing.bait.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.WORM), Arrays.asList(EnumRodQuality.GOOD, EnumRodQuality.SUPER, EnumRodQuality.ELECTRIC));
		Fishing.bait.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.HOPPER), Arrays.asList(EnumRodQuality.OLD, EnumRodQuality.GOOD, EnumRodQuality.ELECTRIC));
		Fishing.bait.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.MAGGOT), Arrays.asList(EnumRodQuality.DIRE, EnumRodQuality.OLD, EnumRodQuality.GOOD, EnumRodQuality.ELECTRIC));
		Fishing.bait.addBaitForQuality(new ItemStack(Items.bread), Arrays.asList(EnumRodQuality.OLD, EnumRodQuality.GOOD, EnumRodQuality.SUPER, EnumRodQuality.ELECTRIC));
		Fishing.bait.addBaitForQuality(new ItemStack(Items.fish, 1, Fishery.minnow.fishID), Arrays.asList(EnumRodQuality.SUPER, EnumRodQuality.ELECTRIC));
		Fishing.bait.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.BEE), Arrays.asList(EnumRodQuality.ELECTRIC));
	}
	
	private void addDropletRecipes() {
		
		// Water Droplet to Water In Liquifier		
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 1, 
				FluidRegistry.getFluidStack("water", FluidContainerRegistry.BUCKET_VOLUME));
		
		// Aqua Droplet to Pressurised Water
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 1, 
				FluidRegistry.getFluidStack(FluidDictionary.hp_water, FluidContainerRegistry.BUCKET_VOLUME / 4));

		// Nether Droplet to Lava in Liquifier Only
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_NETHER), 1000, 
				FluidRegistry.getFluidStack("lava", FluidContainerRegistry.BUCKET_VOLUME / 10));

		// Ender Droplet to Ender Pearl
		GameRegistry.addRecipe(new ItemStack(Items.ender_pearl), new Object[] { "DDD", "DDD", "DDD", 
				Character.valueOf('D'), new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER) });

		// Destruction Droplet to TNT
		GameRegistry.addRecipe(new ItemStack(Blocks.tnt), new Object[] { "DS ", "SD ", 
				Character.valueOf('D'), new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 
				Character.valueOf('S'), Blocks.sand });
		
		if(FluidRegistry.getFluid("life essence") != null) {
			RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 100, FluidRegistry.getFluidStack("life essence", 250));
		}
	}
	
	private void addFishRecipes() {
		//Food to feed a fish in a tank
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.ANT), 1);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.MAGGOT), 2);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.HOPPER), 2);
		Fishing.food.addFishFood(new ItemStack(Items.bread), 8);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.WORM), 3);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.BEE), 3);
		Fishing.food.addFishFood(new ItemStack(Core.materials, 1, MaterialsMeta.FISH_MEAL), 12);
		
		// Squid > Calamari
		GameRegistry.addShapelessRecipe(new ItemStack(Core.food, 2, FoodMeta.CALAMARI), new Object[] {
				new ItemStack(Items.fish, 1, Fishery.squid.fishID),
				new ItemStack(Items.bowl) });
		
		//Smoked Salmon
		RecipeHelper.addSmelting(new ItemStack(Items.fish, 1, salmon.fishID), new ItemStack(Core.food, 1, FoodMeta.SMOKED_SALMON), 0.1F);

		// Cod > Fish Finger
		GameRegistry.addRecipe(new ItemStack(Core.food, 64, FoodMeta.FISH_FINGER), new Object[] { " B ", "BFB", " B ",
				Character.valueOf('F'), new ItemStack(Items.fish, 1, Fishery.cod.fishID),
				Character.valueOf('B'), Items.bread });
		
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 8, FoodMeta.CUSTARD), new Object[] { 
			 new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_CUSTARD), Items.bowl
		});
		
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 4, FoodMeta.CUSTARD), new Object[] { 
			 new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_NORMAL_CUSTARD), Items.bowl
		});
		
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.FISH_N_CUSTARD), new Object[] { 
			 new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER),
			 new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER)
		});
		
		// Tetra > Neon Lamp
		for (int i = 0; i < 12; i++) {
			GameRegistry.addRecipe(new ItemStack(lampsOn, 4, i), new Object[] { "GDG", "PFP", "GRG", 
					Character.valueOf('P'), new ItemStack(Core.pearls, 1, i),
					Character.valueOf('G'), new ItemStack(Core.transparentBlocks, 1, TransparentMeta.PLASTIC),
					Character.valueOf('R'), Items.redstone, 
					Character.valueOf('D'), Items.glowstone_dust,
					Character.valueOf('F'), new ItemStack(Items.fish, 1, Fishery.tetra.fishID) });
		}
		
		//Dragonfish to Eternal Upgrades
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETERNAL_MALE), new Object[] {"WEW", "FRF", "DWD", 
					Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 11),
					Character.valueOf('E'), Blocks.emerald_block, 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, Fishery.dragon.fishID),
					Character.valueOf('R'), Blocks.dragon_egg, 
					Character.valueOf('D'), Items.diamond }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETERNAL_FEMALE), new Object[] {"WEW", "FRF", "DWD", 
					Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 6),
					Character.valueOf('E'), Blocks.emerald_block, 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, Fishery.dragon.fishID),
					Character.valueOf('R'), Blocks.dragon_egg, 
					Character.valueOf('D'), Items.diamond }));
		
		//Netherfish as Liquifier fuel		
		MaricultureHandlers.smelter.addFuel(new ItemStack(Items.fish, 1, Fishery.nether.fishID), new FuelInfo(2000, 16, 2400));
		//Fish Eggs to Caviar
		CraftingManager.getInstance().getRecipeList().add(new ShapelessFishRecipe(new ItemStack(Core.food, 1, FoodMeta.CAVIAR), new ItemStack(fishy)));
		
		RecipeHelper.addMelting(new ItemStack(Items.fish), 180, FluidRegistry.getFluidStack(FluidDictionary.fish_oil, 100));
		GameRegistry.addShapelessRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.FISH_MEAL), new Object[] { Items.fish });
		ItemStack kelp = (Modules.world.isActive())? new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP): new ItemStack(Items.dye, 1, Dye.GREEN);
		for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
			if(FishSpecies.speciesList.get(i) != null) {
				FishSpecies species = FishSpecies.speciesList.get(i);
				ItemStack male = Fishing.fishHelper.makePureFish(species, FishHelper.MALE);
				ItemStack female = Fishing.fishHelper.makePureFish(species, FishHelper.FEMALE);
				ItemStack raw = new ItemStack(Items.fish, 1, species.fishID);
				Fish.init(male, female, raw, species);
			}
		}
	}
}
