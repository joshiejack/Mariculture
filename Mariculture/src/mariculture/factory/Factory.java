package mariculture.factory;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.diving.Diving;
import mariculture.factory.blocks.*;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.items.ItemPaintbrush;
import mariculture.factory.items.ItemPlan;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Factory extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
	public static Block customFlooring;
	public static Block customBlock;
	public static Block customStairs;
	public static Block customSlabs;
	public static Block customSlabsDouble;
	public static Block customFence;
	public static Block customGate;
	public static Block customWall;
	public static Block customLight;
	public static Block customRFBlock;
	public static Item plans;
	public static Item fludd;
	public static Item paintbrush;

	private static EnumArmorMaterial armorFLUDD = EnumHelper.addArmorMaterial("FLUDD", 0, new int[] { 0, 0, 0, 0 }, 0);

	@Override
	public void registerHandlers() {
		MaricultureHandlers.turbine = new GasTurbineHandler();
		MinecraftForge.EVENT_BUS.register(new FactoryEvents());
	}

	@Override
	public void registerBlocks() {
		customFlooring = new BlockCustomFlooring(BlockIds.customFlooring).setStepSound(Block.soundStoneFootstep)
				.setHardness(0.1F).setResistance(0.1F).setUnlocalizedName("customFlooring").setLightOpacity(0);
		customBlock = new BlockCustomBlock(BlockIds.customBlock).setStepSound(Block.soundStoneFootstep).setHardness(1F)
				.setResistance(0.1F).setUnlocalizedName("customBlock");
		customStairs = new BlockCustomStairs(BlockIds.customStairs, customBlock, 0)
				.setStepSound(Block.soundStoneFootstep).setHardness(1F).setResistance(0.1F) .setUnlocalizedName("customStairs");
		customSlabs = new BlockCustomSlab(BlockIds.customSlabs, false).setUnlocalizedName("customSlabs");
		customSlabsDouble = new BlockCustomSlab(BlockIds.customSlabsDouble, true).setUnlocalizedName("customSlabsDouble");
		customFence = new BlockCustomFence(BlockIds.customFence).setUnlocalizedName("customFence");
		customGate = new BlockCustomGate(BlockIds.customGate).setUnlocalizedName("customGate");
		customWall = new BlockCustomWall(BlockIds.customWall, customBlock).setUnlocalizedName("customWall");
		customLight = new BlockCustomLight(BlockIds.customLight).setUnlocalizedName("customLight").setLightValue(1.0F);
		customRFBlock = new BlockCustomPower(BlockIds.customRFBlock).setUnlocalizedName("customRFBlock");

		Item.itemsList[BlockIds.customFlooring] = new BlockItemCustom(BlockIds.customFlooring - 256, customFlooring).setUnlocalizedName("customFlooring");
		Item.itemsList[BlockIds.customBlock] = new BlockItemCustom(BlockIds.customBlock - 256, customBlock).setUnlocalizedName("customBlock");
		Item.itemsList[BlockIds.customStairs] = new BlockItemCustom(BlockIds.customStairs - 256, customStairs).setUnlocalizedName("customStairs");
		Item.itemsList[BlockIds.customFence] = new BlockItemCustom(BlockIds.customFence - 256, customFence).setUnlocalizedName("customFence");
		Item.itemsList[BlockIds.customGate] = new BlockItemCustom(BlockIds.customGate - 256, customGate).setUnlocalizedName("customGate");
		Item.itemsList[BlockIds.customWall] = new BlockItemCustom(BlockIds.customWall - 256, customWall).setUnlocalizedName("customWall");
		Item.itemsList[BlockIds.customLight] = new BlockItemCustom(BlockIds.customLight - 256, customLight).setUnlocalizedName("customLight");
		Item.itemsList[BlockIds.customRFBlock] = new BlockItemCustom(BlockIds.customRFBlock - 256, customRFBlock).setUnlocalizedName("customRFBlock");
		BlockItemCustomSlab.setSlabs((BlockHalfSlab) customSlabs, (BlockHalfSlab) customSlabsDouble);

		GameRegistry.registerBlock(customSlabs, mariculture.factory.blocks.BlockItemCustomSlab.class, "Mariculture_customSlab");
		GameRegistry.registerBlock(customSlabsDouble, mariculture.factory.blocks.BlockItemCustomSlab.class, "Mariculture_customSlabDouble");

		GameRegistry.registerTileEntity(TileCustom.class, "tileEntityCustom");
		GameRegistry.registerTileEntity(TileCustomPowered.class, "tileEntityCustomRF");
		GameRegistry.registerTileEntity(TileSawmill.class, "tileEntitySawmill");
		GameRegistry.registerTileEntity(TileSluice.class, "tileEntitySluice");
		GameRegistry.registerTileEntity(TileTurbineWater.class, "tileEntityTurbine");
		GameRegistry.registerTileEntity(TileFLUDDStand.class, "tileEntityFLUDD");
		GameRegistry.registerTileEntity(TilePressureVessel.class, "tileEntityPressureVessel");
		GameRegistry.registerTileEntity(TileDictionary.class, "tileEntityDictionary");
		GameRegistry.registerTileEntity(TileTurbineGas.class, "tileEntityTurbineGas");
        GameRegistry.registerTileEntity(TileSponge.class, "tileEntitySponge");

		MinecraftForge.setBlockHarvestLevel(Core.utilBlocks, UtilMeta.SLUICE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.utilBlocks, UtilMeta.SPONGE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.singleBlocks, SingleMeta.TURBINE_WATER, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(Core.singleBlocks, SingleMeta.TURBINE_GAS, "pickaxe", 1);

		RegistryHelper.register(new Object[] { customFlooring, customBlock, customStairs, customSlabs, 
				customFence, customGate, customWall, customLight, customRFBlock, customSlabsDouble });
	}

	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityFLUDDSquirt.class, "WaterSquirt", 43, Mariculture.instance, 80, 3, true);
	}

	@Override
	public void registerItems() {
		plans = new ItemPlan(ItemIds.plans).setUnlocalizedName("plans");
		fludd = new ItemArmorFLUDD(ItemIds.fludd, armorFLUDD, RenderIds.FLUDD, 1).setUnlocalizedName("fludd");
		paintbrush = new ItemPaintbrush(ItemIds.paintbrush, 128).setUnlocalizedName("paintbrush");
		RegistryHelper.register(new Object[] { plans, fludd, paintbrush });
	}

	@Override
	public void addRecipes() {
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(paintbrush), true, new Object[] { " WW", " IW", "S  ", 
					Character.valueOf('W'), new ItemStack(Block.cloth, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('I'), "blockAluminum", 
					Character.valueOf('S'), new ItemStack(Core.utilBlocks, 1, UtilMeta.SAWMILL) }));
		
		//TODO: Delete in the future
		CraftingManager
			.getInstance()
			.getRecipeList()
			.add(new ShapelessOreRecipe(new ItemStack(paintbrush), new Object[] { new ItemStack(Core.craftingItem, 1, CraftingMeta.LEGACY_PAINTBRUSH) }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.SAWMILL), new Object[] { " A ", "DWD", "IMI", 
					Character.valueOf('A'), Item.axeIron, Character.valueOf('D'), "slabWood",
					Character.valueOf('M'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_WOOD),
					Character.valueOf('W'), "logWood", 
					Character.valueOf('I'), "ingotCopper" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.DICTIONARY), new Object[] { " B ", "FPF", "IMI", 
					Character.valueOf('F'), Item.feather, 
					Character.valueOf('P'),new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('M'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_WOOD), 
					Character.valueOf('B'), Item.writableBook,
					Character.valueOf('I'), "ingotCopper" }));
		
		ItemStack sponge = (Modules.world.isActive())? new ItemStack(Block.sponge): new ItemStack(Item.bucketWater);
		ItemStack water = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): new ItemStack(Item.potion, 1, 0);

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.SPONGE), new Object[] { " D ", "ATA", "SCS", 
					Character.valueOf('D'), new ItemStack(Item.potion, 1, 0), 
					Character.valueOf('S'), sponge, 
					Character.valueOf('C'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON),
					Character.valueOf('A'), water,
					Character.valueOf('T'), new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_TITANIUM) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.utilBlocks, 4, UtilMeta.SLUICE), new Object[] { " H ", "WBW", "IMI", 
					Character.valueOf('H'), Block.hopperBlock, 
					Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), 
					Character.valueOf('M'), new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON), 
					Character.valueOf('B'), Block.fenceIron,
					Character.valueOf('I'), "ingotAluminum" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_WATER), new Object[] { "AAA", " G ", "BPB", 
					Character.valueOf('G'), "glass", 
					Character.valueOf('B'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), 
					Character.valueOf('A'), "ingotAluminum", 
					Character.valueOf('P'), Block.pistonBase }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_GAS), new Object[] { "AAA", " G ", "BPB", 
					Character.valueOf('G'), "glass", 
					Character.valueOf('B'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), 
					Character.valueOf('A'), "ingotTitanium", 
					Character.valueOf('P'), Block.pistonBase }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.doubleBlock, 1, DoubleMeta.PRESSURE_VESSEL), true,new Object[] { "ITT", "III", "W  ", 
					Character.valueOf('I'), Item.ingotIron,
					Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL),
					Character.valueOf('T'), "ingotAluminum" }));
		
		ItemStack fludd = new ItemStack(Factory.fludd);
		ItemStack tank = (Modules.diving.isActive())? new ItemStack(Diving.scubaTank, 1, OreDictionary.WILDCARD_VALUE): new ItemStack(Block.lever);
		fludd.setTagCompound(new NBTTagCompound());
		fludd.stackTagCompound.setInteger("mode", 0);
		fludd.stackTagCompound.setInteger("water", 0);
		
		CraftingManager
			.getInstance()
			.getRecipeList()
			.add(new ShapedOreRecipe(fludd, new Object[] { " E ", "PGP", "LUL", 
				Character.valueOf('E'), new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS), 
				Character.valueOf('P'),new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC_YELLOW), 
				Character.valueOf('G'), new ItemStack(Core.glassBlocks, 1, GlassMeta.PLASTIC), 
				Character.valueOf('L'), tank, 
				Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_PURITY) }));
		
		ItemStack fluddStack = fludd.copy();
		fluddStack.stackSize = 3;

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(fluddStack, new Object[] { "PAP", "FDF", "PAP", 
					Character.valueOf('P'), Item.enderPearl, 
					Character.valueOf('A'), Item.appleGold, 
					Character.valueOf('F'), new ItemStack(Factory.fludd), 
					Character.valueOf('D'), Item.diamond }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK), new Object[] { "LLB", 
					Character.valueOf('L'), "blockLimestone", 
					Character.valueOf('B'), "dyeWhite" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.BLANK_PLAN), new Object[] { 
					"dyeBlue", "dyeBlack", Item.paper, "dyeBlue" }));
	}
}
