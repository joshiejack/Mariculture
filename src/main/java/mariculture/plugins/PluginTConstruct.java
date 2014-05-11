package mariculture.plugins;

import java.util.ArrayList;
import java.util.logging.Level;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.Core;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.Items;
import mariculture.core.lib.MetalRates;
import mariculture.core.util.FluidDictionary;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.tconstruct.ModPearl;
import mariculture.plugins.tconstruct.TiCEvents;
import mariculture.plugins.tconstruct.TitaniumPart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginTConstruct extends Plugin {
	public static final int titanium_id = 200;
	public static TitaniumPart arrowhead;
	public static TitaniumPart axe_head;
	public static TitaniumPart battle_sign_head;
	public static TitaniumPart binding;
	public static TitaniumPart broad_axe_head;
	public static TitaniumPart chisel_head;
	public static TitaniumPart chunk;
	public static TitaniumPart crossbar;
	public static TitaniumPart excavator_head;
	public static TitaniumPart frypan_head;
	public static TitaniumPart full_guard;
	public static TitaniumPart hammer_head;
	public static TitaniumPart hand_guard;
	public static TitaniumPart knife_blade;
	public static TitaniumPart large_guard;
	public static TitaniumPart large_sword_blade;
	public static TitaniumPart large_plate;
	public static TitaniumPart pickaxe_head;
	public static TitaniumPart scythe_head;
	public static TitaniumPart shovel_head;
	public static TitaniumPart sword_blade;
	public static TitaniumPart tool_rod;
	public static TitaniumPart tough_binding;
	public static TitaniumPart tough_rod;

	@Override
	public void preInit() {
		try {
			FluidDictionary.instance.addFluid("aluminum.molten", FluidType.Aluminum.fluid);
			FluidDictionary.instance.addFluid("bronze.molten", FluidType.Bronze.fluid);
			FluidDictionary.instance.addFluid("copper.molten", FluidType.Copper.fluid);
			FluidDictionary.instance.addFluid("glass.molten", FluidType.Glass.fluid);
			FluidDictionary.instance.addFluid("gold.molten", FluidType.Gold.fluid);
			FluidDictionary.instance.addFluid("iron.molten", FluidType.Iron.fluid);
			FluidDictionary.instance.addFluid("lead.molten", FluidType.Lead.fluid);
			FluidDictionary.instance.addFluid("nickel.molten", FluidType.Nickel.fluid);
			FluidDictionary.instance.addFluid("silver.molten", FluidType.Silver.fluid);
			FluidDictionary.instance.addFluid("steel.molten", FluidType.Steel.fluid);
			FluidDictionary.instance.addFluid("tin.molten", FluidType.Tin.fluid);
			FluidDictionary.instance.addFluid("electrum.molten", FluidType.Electrum.fluid);
			FluidDictionary.instance.addFluid("cobalt.molten", FluidType.Cobalt.fluid);
			MinecraftForge.EVENT_BUS.register(new TiCEvents());
		} catch (Exception e) {
			LogHandler.log(Level.SEVERE, "Mariculture will NOT use Tinkers constructs molten fluids due to an error on load");
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		arrowhead = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_1).setUnlocalizedName("titanium.arrow.head");
		axe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_2).setUnlocalizedName("titanium.axe.head");
		battle_sign_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_3).setUnlocalizedName("titanium.battlesign.head");
		binding = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_4).setUnlocalizedName("titanium.binding");
		chisel_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_5).setUnlocalizedName("titanium.chisel.head");
		chunk = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_6).setUnlocalizedName("titanium.chunk");
		crossbar = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_7).setUnlocalizedName("titanium.crossbar");
		excavator_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_8).setUnlocalizedName("titanium.excavator.head");
		frypan_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_9).setUnlocalizedName("titanium.frypan.head");
		full_guard = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_10).setUnlocalizedName("titanium.full.guard");
		hammer_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_11).setUnlocalizedName("titanium.hammer.head");
		knife_blade = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_12).setUnlocalizedName("titanium.knife.blade");
		large_guard = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_13).setUnlocalizedName("titanium.large.guard");
		large_sword_blade = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_14).setUnlocalizedName("titanium.large.sword.blade");
		large_plate = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_15).setUnlocalizedName("titanium.large.plate");
		broad_axe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_16).setUnlocalizedName("titanium.lumberaxe.head");
		hand_guard = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_17).setUnlocalizedName("titanium.medium.guard");
		pickaxe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_18).setUnlocalizedName("titanium.pickaxe.head");
		scythe_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_19).setUnlocalizedName("titanium.scythe.head");
		shovel_head = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_20).setUnlocalizedName("titanium.shovel.head");
		sword_blade = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_21).setUnlocalizedName("titanium.sword.blade");
		tool_rod = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_22).setUnlocalizedName("titanium.tool.rod");
		tough_binding = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_23).setUnlocalizedName("titanium.tough.binding");
		tough_rod = (TitaniumPart) new TitaniumPart(ItemIds.titanium_part_24).setUnlocalizedName("titanium.tough.rod");

		// Register Parts
		GameRegistry.registerItem(arrowhead, "titanium_arrow_head");
		GameRegistry.registerItem(axe_head, "titanium_axe_head");
		GameRegistry.registerItem(battle_sign_head, "titanium_battle_sign");
		GameRegistry.registerItem(binding, "titanium_binding");
		GameRegistry.registerItem(broad_axe_head, "titanium_broad_axe_head");
		GameRegistry.registerItem(chisel_head, "titanium_chisel_head");
		GameRegistry.registerItem(chunk, "titanium_chunk");
		GameRegistry.registerItem(crossbar, "titanium_crossbar");
		GameRegistry.registerItem(excavator_head, "titanium_excavator_head");
		GameRegistry.registerItem(frypan_head, "titanium_frypan_head");
		GameRegistry.registerItem(full_guard, "titanium_full_guard");
		GameRegistry.registerItem(hammer_head, "titanium_hammer_head");
		GameRegistry.registerItem(hand_guard, "titanium_hand_guard");
		GameRegistry.registerItem(knife_blade, "titanium_knife_blade");
		GameRegistry.registerItem(large_guard, "titanium_large_guard");
		GameRegistry.registerItem(large_sword_blade, "titanium_large_swordBlade");
		GameRegistry.registerItem(large_plate, "titanium_large_plate");
		GameRegistry.registerItem(pickaxe_head, "titanium_pickaxe_head");
		GameRegistry.registerItem(scythe_head, "titanium_scythe_head");
		GameRegistry.registerItem(shovel_head, "titanium_shovel_head");
		GameRegistry.registerItem(sword_blade, "titanium_sword_blade");
		GameRegistry.registerItem(tool_rod, "titanium_tool_rod");
		GameRegistry.registerItem(tough_binding, "titanium_tough_binding");
		GameRegistry.registerItem(tough_rod, "titanium_tough_rod");
	}

	@Override
	public void postInit() {
		int level = Loader.isModLoaded("IguanaTweaks") ? 5 : 4;
		TConstructRegistry.addToolMaterial(titanium_id, "Titanium", level, 600, 1500, 2, 1.5F, 2, 0.0F, "", "");
		TConstructClientRegistry.addMaterialRenderMapping(titanium_id, "Mariculture", "titanium", true);
		TConstructRegistry.addBowMaterial(titanium_id, 768, 40, 1.2F);
		TConstructRegistry.addArrowMaterial(titanium_id, 5.0F, 0.25F, 100.0F);

		addMelting();
		addTools();
		addAlloy();
		addModifiers();
	}

	public void addMelting() {
		// Melt Down Titanium
		addMelting("ingotRutile", new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), MetalRates.INGOT), 800);
		addMelting("ingotTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 800);
		addMelting("oreRutile", new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), MetalRates.ORE), 800);
		addMelting("dustTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 800);
		addMelting("blockTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.BLOCK), 800);
		addMelting("nuggetTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.NUGGET), 800);
		// >> Form Ingot and Block
		addCasting("ingotRutile", new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), MetalRates.INGOT), 100);
		addCasting("ingotTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 100);
		addBlockCasting("blockTitanium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.BLOCK), 100);
		// Melt Down Magnesium
		addMelting("ingotMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.INGOT), 300);
		addMelting("oreMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.ORE), 300);
		addMelting("dustMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.INGOT), 300);
		addMelting("blockMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.BLOCK), 300);
		addMelting("nuggetMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.NUGGET), 300);
		
		//Magnesium Dust
		Smeltery.addMelting(Items.dustMagnesium, 300, FluidDictionary.getFluidStack(FluidDictionary.magnesium, MetalRates.INGOT));
		
		// >> Form Ingot and Block
		addCasting("ingotMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.INGOT), 100);
		addBlockCasting("blockMagnesium", new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), MetalRates.BLOCK), 100);
		
		// >> Limestone Melted to Quicklime
		addMelting("blockLimestone", FluidDictionary.getFluidStack(FluidDictionary.quicklime, 900), 100);
		addMelting("limestone", FluidDictionary.getFluidStack(FluidDictionary.quicklime, 900), 100);

		addCastings(titanium_id, new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), MetalRates.INGOT), 80);

		// Register Titanium with Patterns
		if (OreDictionary.getOres("ingotTitanium").size() > 0) {
			for (ItemStack ingot : OreDictionary.getOres("ingotTitanium")) {
				PatternBuilder.instance.registerFullMaterial(ingot, 2, "Titanium", new ItemStack(chunk, 1, titanium_id), new ItemStack(tool_rod, 1, titanium_id), titanium_id);
			}
		}
	}

	public void addTools() {
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("arrow"), new Item[] { arrowhead, tool_rod, TConstructRegistry.getItem("fletching") });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("battleaxe"), new Item[] { broad_axe_head, tough_rod, broad_axe_head, tough_binding });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("battlesign"), new Item[] { battle_sign_head, tool_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("broadsword"), new Item[] { sword_blade, tool_rod, large_guard });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("chisel"), new Item[] { chisel_head, tool_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("cleaver"), new Item[] { large_sword_blade, tough_rod, large_plate, tough_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("cutlass"), new Item[] { sword_blade, tool_rod, full_guard });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("dagger"), new Item[] { knife_blade, tool_rod, crossbar });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("excavator"), new Item[] { excavator_head, tough_rod, large_plate, tough_binding });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("frypan"), new Item[] { frypan_head, tool_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("hammer"), new Item[] { hammer_head, tough_rod, large_plate, large_plate });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("hatchet"), new Item[] { axe_head, tool_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("longsword"), new Item[] { sword_blade, tool_rod, hand_guard });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("lumberaxe"), new Item[] { broad_axe_head, tough_rod, large_plate, tough_binding });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("mattock"), new Item[] { axe_head, tool_rod, shovel_head });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("pickaxe"), new Item[] { pickaxe_head, tool_rod, binding });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("rapier"), new Item[] { sword_blade, tool_rod, crossbar });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("scythe"), new Item[] { scythe_head, tough_rod, tough_binding, tough_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shortbow"), new Item[] { tool_rod, TConstructRegistry.getItem("bowstring"), tool_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shortbow"), new Item[] { TConstructRegistry.getItem("toolRod"), TConstructRegistry.getItem("bowstring"), tool_rod });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shortbow"), new Item[] { tool_rod, TConstructRegistry.getItem("bowstring"), TConstructRegistry.getItem("toolRod") });
		TConstructRegistry.addToolRecipe((ToolCore) TConstructRegistry.getItem("shovel"), new Item[] { shovel_head, tool_rod });
	}

	private void addAlloy() {
		FluidStack titanium = new FluidStack(FluidRegistry.getFluid(FluidDictionary.titanium), 8);
		FluidStack rutile = new FluidStack(FluidRegistry.getFluid(FluidDictionary.rutile), 8);
		FluidStack magnesium = new FluidStack(FluidRegistry.getFluid(FluidDictionary.magnesium), 8);

		Smeltery.addAlloyMixing(titanium, new FluidStack[] { rutile, magnesium });
	}

	private void addModifiers() {
		ToolBuilder tb = ToolBuilder.instance;
		ItemStack pearl = new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE);
		ItemStack pearlBlock = new ItemStack(Core.pearl, 1, OreDictionary.WILDCARD_VALUE);
		int effect = 20;
		tb.registerToolMod(new ModPearl(new ItemStack[] { pearl }, effect, 1));
		tb.registerToolMod(new ModPearl(new ItemStack[] { pearl, pearl }, effect, 2));
		tb.registerToolMod(new ModPearl(new ItemStack[] { pearlBlock }, effect, 4));
		tb.registerToolMod(new ModPearl(new ItemStack[] { pearl, pearlBlock }, effect, 5));
		tb.registerToolMod(new ModPearl(new ItemStack[] { pearlBlock, pearlBlock }, effect, 8));

		for (ToolCore tool : TConstructRegistry.getToolMapping()) {
			if (tool instanceof HarvestTool) {
				TConstructClientRegistry.addEffectRenderMapping(tool, effect, "mariculture", "pearl", true);
			}
		}

		if (FluidRegistry.getFluid("xpjuice") != null) {
			ItemStack xpberry = TConstructRegistry.getItemStack("oreberryEssence");
			ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();
			ArrayList<Integer> chances = new ArrayList<Integer>();

			int j = 3;
			for (int i = 80; i <= 120; i += 10) {
				chances.add(j);
				fluids.add(FluidRegistry.getFluidStack("xpjuice", i));
				j++;
			}

			MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(xpberry, 1000, fluids.toArray(new FluidStack[fluids.size()]), chances.toArray(new Integer[chances.size()]), null, 0));
		}
	}

	private void addCasting(String dic, FluidStack fluid, int delay) {
		if (OreDictionary.getOres(dic).size() > 0) {
			TConstructRegistry.getTableCasting().addCastingRecipe(OreDictionary.getOres(dic).get(0), fluid, TConstructRegistry.getItemStack("ingotCast"), delay);
		}
	}

	private void addCastings(int id, FluidStack fluid, int delay) {
		addPartCasting(new ItemStack(tool_rod, 1, id), TConstructRegistry.getItemStack("toolRodCast"), fluid, delay);
		addPartCasting(new ItemStack(pickaxe_head, 1, id), TConstructRegistry.getItemStack("pickaxeHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(shovel_head, 1, id), TConstructRegistry.getItemStack("shovelHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(axe_head, 1, id), TConstructRegistry.getItemStack("hatchetHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(sword_blade, 1, id), TConstructRegistry.getItemStack("swordBladeCast"), fluid, delay);
		addPartCasting(new ItemStack(large_guard, 1, id), TConstructRegistry.getItemStack("wideGuardCast"), fluid, delay);
		addPartCasting(new ItemStack(hand_guard, 1, id), TConstructRegistry.getItemStack("handGuardCast"), fluid, delay);
		addPartCasting(new ItemStack(crossbar, 1, id), TConstructRegistry.getItemStack("crossBarCast"), fluid, delay);
		addPartCasting(new ItemStack(binding, 1, id), TConstructRegistry.getItemStack("bindingCast"), fluid, delay);
		addPartCasting(new ItemStack(frypan_head, 1, id), TConstructRegistry.getItemStack("frypanHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(battle_sign_head, 1, id), TConstructRegistry.getItemStack("signHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(knife_blade, 1, id), TConstructRegistry.getItemStack("knifeBladeCast"), fluid, delay);
		addPartCasting(new ItemStack(chisel_head, 1, id), TConstructRegistry.getItemStack("chiselHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(tough_rod, 1, id), TConstructRegistry.getItemStack("toughRodCast"), fluid, delay);
		addPartCasting(new ItemStack(tough_binding, 1, id), TConstructRegistry.getItemStack("toughBindingCast"), fluid, delay);
		addPartCasting(new ItemStack(large_plate, 1, id), TConstructRegistry.getItemStack("largePlateCast"), fluid, delay);
		addPartCasting(new ItemStack(broad_axe_head, 1, id), TConstructRegistry.getItemStack("broadAxeHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(scythe_head, 1, id), TConstructRegistry.getItemStack("scytheHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(excavator_head, 1, id), TConstructRegistry.getItemStack("excavatorHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(large_sword_blade, 1, id), TConstructRegistry.getItemStack("largeBladeCast"), fluid, delay);
		addPartCasting(new ItemStack(hammer_head, 1, id), TConstructRegistry.getItemStack("hammerHeadCast"), fluid, delay);
		addPartCasting(new ItemStack(full_guard, 1, id), TConstructRegistry.getItemStack("fullGuardCast"), fluid, delay);
		addPartCasting(new ItemStack(arrowhead, 1, id), new ItemStack(TConstructRegistry.getItem("metalPattern"), 1, 25), fluid, delay);
	}

	private void addMelting(String dic, FluidStack fluid, int temp) {
		if (OreDictionary.getOres(dic).size() > 0) {
			for (ItemStack ore : OreDictionary.getOres(dic)) {
				Smeltery.addMelting(ore, temp, fluid);
			}
		}
	}

	private void addBlockCasting(String dic, FluidStack fluid, int delay) {
		if (OreDictionary.getOres(dic).size() > 0) {
			TConstructRegistry.getBasinCasting().addCastingRecipe(OreDictionary.getOres(dic).get(0), fluid, delay);
		}
	}

	private void addPartCasting(ItemStack output, ItemStack cast, FluidStack fluid, int hardeningDelay) {
		TConstructRegistry.getTableCasting().addCastingRecipe(output, fluid, cast, hardeningDelay);
	}
}