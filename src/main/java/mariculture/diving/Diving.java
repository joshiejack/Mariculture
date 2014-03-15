package mariculture.diving;

import java.util.HashMap;

import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.RenderIds;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Diving extends Module {
	public static final HashMap facingList = new HashMap();
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
	public static Item divingHelmet;
	public static Item divingTop;
	public static Item divingPants;
	public static Item divingBoots;
	public static Item scubaMask;
	public static Item scubaTank;
	public static Item scubaSuit;
	public static Item swimfin;
	public static Item snorkel;

	private static ArmorMaterial armorSCUBA = EnumHelper.addArmorMaterial("SCUBA", 15, new int[] { 0, 0, 1, 0 }, 0);
	private static ArmorMaterial armorDIVING = EnumHelper.addArmorMaterial("DIVING", 20, new int[] { 1, 0, 2, 1 }, 0);
	private static ArmorMaterial armorSnorkel = EnumHelper.addArmorMaterial("SNORKEL", 10, new int[] { 0, 0, 0, 0 }, 0);

	@Override
	public void registerHandlers() {
		MinecraftForge.EVENT_BUS.register(new ArmorEventHandler());
	}

	@Override
	public void registerBlocks() {
		GameRegistry.registerTileEntity(TileAirCompressor.class, "TileAirCompressor");		
	}

	@Override
	public void registerItems() {
		divingHelmet = (new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 0)).setUnlocalizedName("divingHelmet");
		divingTop = (new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 1)).setUnlocalizedName("divingTop");
		divingPants = (new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 2)).setUnlocalizedName("divingPants");
		divingBoots = (new ItemArmorDiving(armorDIVING, RenderIds.DIVING, 3)).setUnlocalizedName("divingBoots");

		scubaMask = (new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 0)).setUnlocalizedName("scubaMask");
		scubaTank = (new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 1)).setUnlocalizedName("scubaTank").setNoRepair();
		scubaSuit = (new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 2)).setUnlocalizedName("scubaSuit");
		swimfin = (new ItemArmorScuba(armorSCUBA, RenderIds.SCUBA, 3)).setUnlocalizedName("swimfin");
		
		snorkel = (new ItemArmorSnorkel(armorSnorkel, RenderIds.SNORKEL, 0)).setUnlocalizedName("snorkel");
		
		RegistryHelper.register(new Object[]{ divingHelmet, divingTop, divingPants, divingBoots, 
												scubaMask, scubaTank, scubaSuit, swimfin, snorkel });
	}
	
	@Override
	public void addRecipes() {		
		//Air Compressor Top
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMultiMachines, 2, DoubleMeta.COMPRESSOR_TOP), new Object[] {
			"  F", " PB", "III", 'I', new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET),
			'F', new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER),
			'B', new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE),
			'P', Blocks.piston
		});
		
		//Air Compressor Base
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMultiMachines, 1, DoubleMeta.COMPRESSOR_BASE), new Object[] {
			"ITT", "III", "W  ",
			'I', new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET),
			'W', new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL),
			'T', "ingotTitanium"
		});
		
		//Snorkel
		RecipeHelper.addShapedRecipe(new ItemStack(snorkel), new Object[] {
			"  R", "LLR", 'R', Items.sugar, 'L', new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS_GLASS)
		});
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Diving.divingHelmet), true, new Object[] { "CCC", "CGC",
					Character.valueOf('C'), "ingotCopper", Character.valueOf('G'), "glass" }));

		GameRegistry.addRecipe(new ItemStack(Diving.divingTop), new Object[] { " C ", "C C", " C ", 
			Character.valueOf('C'), new ItemStack(Items.leather) });
		
		GameRegistry.addRecipe(new ItemStack(Diving.divingPants), new Object[] { "CCC", " C ", "CCC", 
				Character.valueOf('C'), new ItemStack(Items.leather) });
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Diving.divingBoots), true, new Object[] { "C C", "L L",
					'C', new ItemStack(Items.leather),
					'L', new ItemStack(Items.iron_ingot) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(Diving.scubaMask, true, new Object[] { "PD", "LL", 
						Character.valueOf('P'), "dyeBlack", 
						Character.valueOf('L'), new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS), 
						Character.valueOf('D'), "dyeYellow" }));
		
		ItemStack tank = new ItemStack(Diving.scubaTank);
		tank.setItemDamage(tank.getMaxDamage() - 1);
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(tank, true, new Object[] { "DSD", "S S", "DSD",
					Character.valueOf('S'), new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET), 
					Character.valueOf('D'), "dyeYellow" }));

		GameRegistry.addRecipe(new ItemStack(Diving.scubaSuit), new Object[] { "NNN", " N ", "NNN", 
				Character.valueOf('N'), new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE) });
		
		GameRegistry.addRecipe(new ItemStack(Diving.swimfin), new Object[] { "N N", "PDP", "PDP", 
				Character.valueOf('N'), new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE), 
				Character.valueOf('P'), new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC), 
				Character.valueOf('D'), new ItemStack(Items.dye, 1, Dye.INK) });
	}
}
