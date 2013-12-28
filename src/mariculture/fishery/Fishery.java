package mariculture.fishery;

import java.util.Arrays;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.blocks.BlockItemNeonLamp;
import mariculture.fishery.blocks.BlockItemNet;
import mariculture.fishery.blocks.BlockNeonLamp;
import mariculture.fishery.blocks.BlockSift;
import mariculture.fishery.blocks.TileAutofisher;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileIncubator;
import mariculture.fishery.blocks.TileNet;
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
import mariculture.fishery.items.ItemFishyFood;
import mariculture.fishery.items.ItemFluxRod;
import mariculture.fishery.items.ItemRod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;

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
	public static Item fishyFood;
	public static Item net;

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
		Fishing.bait = new BaitHandler();
		Fishing.quality = new RodQualityHandler();
		Fishing.mutation = new FishMutationHandler();
		Fishing.fishHelper = new FishHelper();
		Fishing.loot = new LootHandler();
		Fishing.rodHandler = new RodRightClickHandler();
		Fishing.food = new FishFoodHandler();
		MinecraftForge.EVENT_BUS.register(new FisheryEventHandler());
	}

	@Override
	public void registerBlocks() {
		siftBlock = new BlockSift(BlockIds.sift).setStepSound(Block.soundWoodFootstep).setHardness(1F).setUnlocalizedName("siftBlock");
		lampsOff = new BlockNeonLamp(BlockIds.lampsOff, true).setUnlocalizedName("lampsOff").setLightOpacity(0);
		lampsOn = new BlockNeonLamp(BlockIds.lampsOn, false).setUnlocalizedName("lampsOn").setLightOpacity(0);

		Item.itemsList[BlockIds.lampsOn] = new BlockItemNeonLamp(BlockIds.lampsOn - 256, lampsOn).setUnlocalizedName("lampsOn");
		Item.itemsList[BlockIds.lampsOff] = new BlockItemNeonLamp(BlockIds.lampsOff - 256, lampsOff).setUnlocalizedName("lampsOff");

		GameRegistry.registerBlock(siftBlock, "Mariculture_siftBlock");
		GameRegistry.registerTileEntity(TileAutofisher.class, "tileEntityAutofisher");
		GameRegistry.registerTileEntity(TileSift.class, "tileEntitySift");
		GameRegistry.registerTileEntity(TileIncubator.class, "tileIncubator");
		GameRegistry.registerTileEntity(TileNet.class, "tileNet");
		GameRegistry.registerTileEntity(TileFeeder.class, "tileFeeder");

		MinecraftForge.setBlockHarvestLevel(Core.utilBlocks, UtilMeta.INCUBATOR_BASE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.utilBlocks, UtilMeta.INCUBATOR_TOP, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.singleBlocks, SingleMeta.AIR_PUMP, "pickaxe", 1);

		RegistryHelper.register(new Object[] { siftBlock, lampsOff, lampsOn });
	}

	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityBass.class, "BassBomb", 41, Mariculture.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityFishing.class, "NewFishing", 42, Mariculture.instance, 80, 3, true);
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

		Fishing.mutation.addMutation(nether, electricRay, glow, 5);
		Fishing.mutation.addMutation(glow, nether, blaze, 10);
		Fishing.mutation.addMutation(night, jelly, ender, 5);
		Fishing.mutation.addMutation(ender, night, dragon, 1);
		Fishing.mutation.addMutation(minnow, gold, salmon, 15);
		Fishing.mutation.addMutation(salmon, minnow, bass, 20);
		Fishing.mutation.addMutation(tetra, siamese, catfish, 10);
		Fishing.mutation.addMutation(catfish, tetra, piranha, 5);
		Fishing.mutation.addMutation(cod, gold, perch, 15);
		Fishing.mutation.addMutation(perch, cod, tuna, 20);
		Fishing.mutation.addMutation(stingRay, angel, mantaRay, 5);
		Fishing.mutation.addMutation(mantaRay, stingRay, electricRay, 3);
		Fishing.mutation.addMutation(damsel, squid, angel, 5);
		Fishing.mutation.addMutation(angel, damsel, blaze, 5);
		Fishing.mutation.addMutation(squid, tuna, jelly, 10);
		Fishing.mutation.addMutation(jelly, squid, manOWar, 5);
		Fishing.mutation.addMutation(minnow, cod, gold, 20);
		Fishing.mutation.addMutation(gold, stingRay, siamese, 10);
		Fishing.mutation.addMutation(siamese, gold, koi, 5);
		Fishing.mutation.addMutation(angel, tetra, butterfly, 5);
		Fishing.mutation.addMutation(butterfly, tuna, tang, 3);
		Fishing.mutation.addMutation(tang, butterfly, clown, 1);
	}

	@Override
	public void registerItems() {
		bait = new ItemBait(ItemIds.bait).setUnlocalizedName("bait");
		rodReed = new ItemRod(ItemIds.rodReed, EnumRodQuality.OLD).setUnlocalizedName("rodReed");
		rodWood = new ItemRod(ItemIds.rodWood, EnumRodQuality.GOOD).setUnlocalizedName("rodWood");
		rodTitanium = new ItemRod(ItemIds.rodTitanium, EnumRodQuality.SUPER).setUnlocalizedName("rodTitanium");
		rodFlux = new ItemFluxRod(ItemIds.rodFlux, EnumRodQuality.FLUX).setUnlocalizedName("rodFlux");
		fishy = new ItemFishy(ItemIds.fishy).setUnlocalizedName("fishy").setCreativeTab(MaricultureTab.tabFish);
		fishyFood = new ItemFishyFood(ItemIds.fishyFood).setUnlocalizedName("fishyFood");
		net = new BlockItemNet(ItemIds.net).setUnlocalizedName("net");

		RegistryHelper.register(new Object[] { bait, rodReed, rodWood, rodTitanium, fishy, fishyFood, net, rodFlux });
		
	}

	@Override
	public void registerOther() {
		registerFish();
		
		if(Extra.DEBUG_ON) {
			MapGenStructureIO.func_143031_a(ComponentFisherman.class, "Mariculture:FishermansHut");
			VillagerRegistry.instance().registerVillageCreationHandler(new FishermanHandler());
		}

		MaricultureTab.tabFish.icon = Fishing.fishHelper.makePureFish(Fishery.cod);
	}

	@Override
	public void addRecipes() {
		addBait();
		addDropletRecipes();
		addFishRecipes();
		FishingLoot.add();
		
		/* Fishing Rods */
		GameRegistry.addRecipe(new ItemStack(rodReed),new Object[] { "  S", " SW", "S W", 
				Character.valueOf('S'), Item.reed, 
				Character.valueOf('W'), Item.silk });
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(rodWood), new Object[] { "  S", " SW", "S W",
					Character.valueOf('S'), new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK), 
					Character.valueOf('W'), Item.silk }));
		
		GameRegistry.addRecipe(new ItemStack(rodTitanium), new Object[] { "  S", " SW", "S W", 
				Character.valueOf('S'), new ItemStack(Core.craftingItem, 1, CraftingMeta.ROD_TITANIUM), 
				Character.valueOf('W'), Item.silk });
		
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(rodFlux), 0), new Object[] {
			"  R", " RS", "B S",
			Character.valueOf('R'), rodTitanium,
			Character.valueOf('S'), Item.silk,
			Character.valueOf('B'), new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		/* Fishing Net, Autofisher and Sift */
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(net, 4, 0), new Object[] { "SWS", "WWW", "SWS",
					Character.valueOf('S'), "stickWood", 
					Character.valueOf('W'), Item.silk }));

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
					Character.valueOf('F'), new ItemStack(fishyFood, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('B'), new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD),
					Character.valueOf('P'), "plankWood"}));
		
		/* Fish Feeder and Incubator Blocks */
		GameRegistry.addRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.FISH_FEEDER), new Object[] { "WFW", "WCW", "WFW", 
				Character.valueOf('F'), new ItemStack(fishyFood, 1, OreDictionary.WILDCARD_VALUE), 
				Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), 
				Character.valueOf('C'), Block.chest });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.INCUBATOR_TOP), new Object[] {"DFD", "CHC", 
					Character.valueOf('F'), new ItemStack(fishyFood, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('D'), "dyeBrown", 
					Character.valueOf('C'), new ItemStack(Block.stainedClay, 1, 0), 
					Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER) }));
	
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.INCUBATOR_BASE), new Object[] {"DBD", "CHC", 
					Character.valueOf('C'), new ItemStack(Block.stainedClay, 1, 3),
					Character.valueOf('B'), new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('D'), "dyeLightBlue", 
					Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER) }));
	}

	private void addBait() {
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.ANT), 0, new Object[] { 
			new ItemStack(Block.dirt), 21, 1, 2, 
			new ItemStack(Block.tallGrass, 1, 1), 4, 1, 2, 
			new ItemStack(Block.wood), 10, 2, 5,
			new ItemStack(Block.grass), 16, 6, 18, 
			new ItemStack(Block.sapling), 4, 1, 1 });

		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.WORM), 3, new Object[] {
			new ItemStack(Block.dirt), 5, 2, 3, 
			new ItemStack(Block.grass), 12, 4, 9 });
	
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.HOPPER), 2, new Object[] {
			new ItemStack(Block.leaves), 10, 1, 1, 
			new ItemStack(Block.tallGrass, 1, 1), 12, 1, 2,
			new ItemStack(Block.grass), 20, 3, 7, 
			new ItemStack(Block.sapling), 10, 2, 5 });
	
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.MAGGOT), 2, new Object[] {
			new ItemStack(Item.rottenFlesh), 10, 2, 3, 
			new ItemStack(Item.beefRaw), 1, 7, 11,
			new ItemStack(Item.chickenRaw), 3, 3, 7, 
			new ItemStack(Item.porkRaw), 2, 5, 9 });
	
		Fishing.bait.addBait(new ItemStack(Item.bread), 1, new Object[] {});
		Fishing.bait.addBait(new ItemStack(fishyFood, 1, Fishery.minnow.fishID), 5, new Object[] {});
	
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.ANT), Arrays.asList(EnumRodQuality.OLD, EnumRodQuality.GOOD, EnumRodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.WORM), Arrays.asList(EnumRodQuality.GOOD, EnumRodQuality.SUPER, EnumRodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.HOPPER), Arrays.asList(EnumRodQuality.OLD, EnumRodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.MAGGOT), Arrays.asList(EnumRodQuality.OLD, EnumRodQuality.GOOD, EnumRodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(Item.bread), Arrays.asList(EnumRodQuality.SUPER, EnumRodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(fishyFood, 1, Fishery.minnow.fishID), Arrays.asList(EnumRodQuality.SUPER, EnumRodQuality.FLUX));
	}
	
	private void addDropletRecipes() {
		
		// Water Droplet to Water In Liquifier
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 1, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidRegistry.WATER.getName(), FluidContainerRegistry.BUCKET_VOLUME), null, 0)));
		
		// Bucket + Droplets to Water Bucket
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bucketWater), new Object[] {
			new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER),
			new ItemStack(Item.bucketEmpty) });
		
		// Droplet + Water to Water Bottle
		GameRegistry.addShapelessRecipe(new ItemStack(Item.potion), new Object[] {
				new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER),
				new ItemStack(Item.glassBottle) });

		
		// Aqua Droplet to Pressurised Water
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 1, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.hp_water, FluidContainerRegistry.BUCKET_VOLUME / 10), null, 0)));

		// Nether Droplet to Lava in Liquifier Only
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_NETHER), 1000, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidRegistry.LAVA.getName(), FluidContainerRegistry.BUCKET_VOLUME / 20), null, 0)));

		// Ender Droplet to Ender Pearl
		GameRegistry.addRecipe(new ItemStack(Item.enderPearl), new Object[] { "DDD", "DDD", "DDD", 
				Character.valueOf('D'), new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER) });

		// Destruction Droplet to TNT
		GameRegistry.addRecipe(new ItemStack(Block.tnt), new Object[] { "DS ", "SD ", 
				Character.valueOf('D'), new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 
				Character.valueOf('S'), Block.sand });
	}
	
	private void addFishRecipes() {
		//Food to feed a fish in a tank
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.ANT), 1);
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.MAGGOT), 1);
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.HOPPER), 1);
		Fishing.food.addFishFood(new ItemStack(Item.bread), 2);
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.WORM), 2);
		Fishing.food.addFishFood(new ItemStack(Core.materials.itemID, 1, MaterialsMeta.FISH_MEAL), 8);
		
		// Squid > Calamari
		GameRegistry.addShapelessRecipe(new ItemStack(Core.food, 2, FoodMeta.CALAMARI), new Object[] {
				new ItemStack(fishyFood, 1, Fishery.squid.fishID),
				new ItemStack(Item.bowlEmpty) });

		// Cod > Fish Finger
		GameRegistry.addRecipe(new ItemStack(Core.food, 64, FoodMeta.FISH_FINGER), new Object[] { " B ", "BFB", " B ",
				Character.valueOf('F'), new ItemStack(fishyFood, 1, Fishery.cod.fishID),
				Character.valueOf('B'), Item.bread });
		
		// Tetra > Neon Lamp
		for (int i = 0; i < 12; i++) {
			GameRegistry.addRecipe(new ItemStack(lampsOn, 1, i), new Object[] { "GDG", "PFP", "GRG", 
					Character.valueOf('P'), new ItemStack(Core.pearls, 1, i),
					Character.valueOf('G'), new ItemStack(Core.glassBlocks, 1, GlassMeta.PLASTIC),
					Character.valueOf('R'), Item.redstone, 
					Character.valueOf('D'), Item.glowstone,
					Character.valueOf('F'), new ItemStack(fishyFood, 1, Fishery.tetra.fishID) });
		}
		
		//Dragonfish to Eternal Upgrades
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETERNAL_MALE), new Object[] {"WEW", "FRF", "DWD", 
					Character.valueOf('W'), new ItemStack(Block.cloth, 1, 11),
					Character.valueOf('E'), Block.blockEmerald, 
					Character.valueOf('F'), new ItemStack(fishyFood, 1, Fishery.dragon.fishID),
					Character.valueOf('R'), Block.dragonEgg, 
					Character.valueOf('D'), Item.diamond }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETERNAL_FEMALE), new Object[] {"WEW", "FRF", "DWD", 
					Character.valueOf('W'), new ItemStack(Block.cloth, 1, 6),
					Character.valueOf('E'), Block.blockEmerald, 
					Character.valueOf('F'), new ItemStack(fishyFood, 1, Fishery.dragon.fishID),
					Character.valueOf('R'), Block.dragonEgg, 
					Character.valueOf('D'), Item.diamond }));
		
		//Netherfish as Liquifier fuel
		MaricultureHandlers.smelter.addFuel(new ItemStack(fishyFood, 1, Fishery.nether.fishID), 32, 2000);
		
		//Fish as fish oil and fish meal
		for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
			if (FishSpecies.speciesList.get(i) != null) {
				FishSpecies fish = FishSpecies.speciesList.get(i);
				ItemStack stack = new ItemStack(fishyFood, 1, fish.fishID);
				
				if(fish.getFishOilVolume() > 0) {
					MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack, 180, 
							new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.fish_oil, (int) (fish.getFishOilVolume() * FluidContainerRegistry.BUCKET_VOLUME)), 
									fish.getLiquifiedProduct(), fish.getLiquifiedProductChance())));
				}
				
				if (fish.getFishMealSize() > 0) {
					GameRegistry.addShapelessRecipe(new ItemStack(Core.materials, fish.getFishMealSize(), MaterialsMeta.FISH_MEAL), new Object[] { stack });
				}
			}
		}
		
	}
}
