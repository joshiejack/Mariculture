package mariculture.plugins;

import mariculture.plugins.Plugins.Plugin;

public class PluginThermalExpansion extends Plugin {

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
	public PluginThermalExpansion(String name) {
		super(name);
	}

	@Override
	public void preInit() {
		
	}
	
	public static FluidStack glowstone(int vol) {
		return FluidRegistry.getFluidStack("glowstone", vol);
	}
	
	public static FluidStack redstone(int vol) {
		return FluidRegistry.getFluidStack("redstone", vol);
	}

	@Override
	public void init() {
		if(Modules.world.isActive()) {
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BLUE), new ItemStack(Items.dyePowder, 2, Dye.LIGHT_BLUE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BRAIN), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_YELLOW));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_CANDYCANE), new ItemStack(Items.dyePowder, 2, Dye.MAGENTA));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_CUCUMBER), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_BROWN));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_GREY), new ItemStack(Items.dyePowder, 2, Dye.GREY));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_LIGHT_GREY), new ItemStack(Items.dyePowder, 2, Dye.LIGHT_GREY));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_ORANGE), new ItemStack(Items.dyePowder, 2, Dye.ORANGE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_PINK), new ItemStack(Items.dyePowder, 2, Dye.PINK));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_PURPLE), new ItemStack(Items.dyePowder, 2, Dye.PURPLE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_RED), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_RED));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_WHITE), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_WHITE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.KELP), new ItemStack(Core.materials, 1, MaterialsMeta.DYE_GREEN));
			ThermalExpansionHelper.addReactantFuel(FluidDictionary.fish_oil, 300000);
		}
	}

	@Override
	public void postInit() {
		//Redstone Melting Recipes
		if(FluidRegistry.getFluid("redstone") != null) {
			RecipeHelper.addMelting(new ItemStack(Blocks.torchRedstoneActive), 1600, redstone(100), new ItemStack(Items.stick), 1);
			RecipeHelper.addMelting(new ItemStack(Blocks.blockRedstone), 1600, redstone(900));
			RecipeHelper.addMelting(new ItemStack(Items.redstone), 1600, redstone(100));
		}
				
		//Glowstone Melting Recipes
		if(FluidRegistry.getFluid("glowstone") != null) {
			RecipeHelper.addMelting(new ItemStack(Items.glowstone), 2000, glowstone(250));
			RecipeHelper.addMelting(new ItemStack(Blocks.glowStone), 2000, glowstone(1000));
		}
	} */
}
