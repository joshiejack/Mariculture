package mariculture.plugins.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import mariculture.aesthetics.Aesthetics;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.Extra;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.TankMeta;
import mariculture.factory.Factory;
import mariculture.fishery.Fish;
import mariculture.fishery.Fishery;
import mariculture.fishery.FishyHelper;
import mariculture.magic.JewelryHandler;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import mariculture.magic.jewelry.parts.JewelryBinding;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import mariculture.plugins.nei.NEIAnvilRecipeHandler.RecipeJewelry;
import mariculture.world.WorldPlus;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.oredict.OreDictionary;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

	public static final HashMap<String, ArrayList<ItemStack>> containers = new HashMap();
	
	@Override
	public void loadConfig() {		
		//HOLY HIDE IN NEI!
		for(int i = 0; i < 16; i++) {
			if(i > 0) {
				API.hideItem(new ItemStack(Core.glass, 1, i));
				API.hideItem(new ItemStack(Core.transparent, 1, i));
				API.hideItem(new ItemStack(Core.water, 1, i));
				if(i > 1) {
					API.hideItem(new ItemStack(Core.metals, 1, i));
					API.hideItem(new ItemStack(Core.sands, 1, i));
					if(i > 2) {
						API.hideItem(new ItemStack(Core.woods, 1, i));
						API.hideItem(new ItemStack(Core.machinesMulti, 1, i));
						if(i > 3) {
							API.hideItem(new ItemStack(Core.renderedMachinesMulti, 1, i));
							API.hideItem(new ItemStack(Core.tanks, 1, i));
							if(i > 4) {
								API.hideItem(new ItemStack(Core.rocks, 1, i));
								if(i > 5) {
									API.hideItem(new ItemStack(Core.metals, 1, i));
									if(i > 6) {
										API.hideItem(new ItemStack(Core.machines, 1, i));
										if(i > 10) {
											API.hideItem(new ItemStack(Core.renderedMachines, 1, i));
											if(i > 11) {
												API.hideItem(new ItemStack(Core.pearlBlock, 1, i));
												if(Modules.isActive(Modules.aesthetics)) API.hideItem(new ItemStack(Aesthetics.pearlBrick, 1, i));
												if(Modules.isActive(Modules.fishery)) API.hideItem(new ItemStack(Fishery.lampsOn, 1, i));
												if(i > 12) {
													API.hideItem(new ItemStack(Core.food, 1, i));
													if(Modules.isActive(Modules.worldplus)) {
														API.hideItem(new ItemStack(WorldPlus.plantGrowable, 1, i));
														API.hideItem(new ItemStack(WorldPlus.plantStatic, 1, i));
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		API.hideItem(new ItemStack(Core.materials, 1, MaterialsMeta.UNUSED));
		API.hideItem(new ItemStack(Core.tanks, 1, TankMeta.BOTTLE));
		API.hideItem(new ItemStack(Core.limestone, 1, LimestoneMeta.PILLAR_2));
		API.hideItem(new ItemStack(Core.limestone, 1, LimestoneMeta.PILLAR_3));
		API.hideItem(new ItemStack(Core.limestone, 1, LimestoneMeta.PEDESTAL_2));
		API.hideItem(new ItemStack(Core.limestone, 1, LimestoneMeta.PEDESTAL_3));
		API.hideItem(new ItemStack(Core.limestone, 1, LimestoneMeta.PEDESTAL_4));
		API.hideItem(new ItemStack(Core.limestone, 1, LimestoneMeta.PEDESTAL_5));
		API.hideItem(new ItemStack(Core.limestone, 1, LimestoneMeta.PEDESTAL_6));
		
		API.registerRecipeHandler(new NEILiquifierRecipeHandler());
		API.registerUsageHandler(new NEILiquifierRecipeHandler());
		API.registerRecipeHandler(new NEIVatRecipeHandler());
		API.registerUsageHandler(new NEIVatRecipeHandler());
		API.registerRecipeHandler(new NEIIngotCasterRecipeHandler());
		API.registerUsageHandler(new NEIIngotCasterRecipeHandler());
		API.registerRecipeHandler(new NEIBlockCasterRecipeHandler());
		API.registerUsageHandler(new NEIBlockCasterRecipeHandler());
		API.registerRecipeHandler(new NEIAnvilRecipeHandler());
		API.registerUsageHandler(new NEIAnvilRecipeHandler());
		API.hideItem(new ItemStack(Core.air, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(Core.ticking, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(Core.worked, 1, OreDictionary.WILDCARD_VALUE));
		
		if(Modules.isActive(Modules.factory)) {
			API.hideItem(new ItemStack(Factory.customBlock, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customFence, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customFlooring, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customGate, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customLight, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customRFBlock, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customSlabs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customSlabsDouble, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customStairs, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Factory.customWall, 1, OreDictionary.WILDCARD_VALUE));
		}
		
		if(Modules.isActive(Modules.fishery)) {
			API.hideItem(new ItemStack(Fishery.lampsOff, 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(Fishery.fishEggs, 1, OreDictionary.WILDCARD_VALUE));
			API.registerRecipeHandler(new NEISifterRecipeHandler());
			API.registerUsageHandler(new NEISifterRecipeHandler());
			API.registerRecipeHandler(new NEIFishBreedingMutationHandler());
			API.registerUsageHandler(new NEIFishBreedingMutationHandler());
			API.registerRecipeHandler(new NEIFishProductHandler());
			API.registerUsageHandler(new NEIFishProductHandler());
			if(Extra.DISABLE_FISH) {
				API.hideItem(new ItemStack(Fishery.fishy));
			} else {
				for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
					FishSpecies fishy = species.getValue();
					ItemStack fish = Fishing.fishHelper.makePureFish(fishy);
					API.addItemListEntry(fish);
				}
			}
		}
		
		FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData().clone();
		for(FluidContainerData container: data) {
			String fluid = container.fluid.getFluid().getName();
			if(containers.containsKey("fluid")) {
				ArrayList<ItemStack> list = containers.get(fluid);
				list.add(container.filledContainer);
			} else {
				ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				list.add(container.filledContainer);
				containers.put(fluid, list);
			}
		}
		
		if(Modules.isActive(Modules.magic)) {
			API.registerRecipeHandler(new NEIJewelryShapedHandler());
			API.registerUsageHandler(new NEIJewelryShapedHandler());
			addJewelry((ItemJewelry) Magic.ring);
			addJewelry((ItemJewelry) Magic.bracelet);
			addJewelry((ItemJewelry) Magic.necklace);
		}
	}
	
	private void addJewelry(ItemJewelry item) {
		JewelryType type = item.getType();
		for (Entry<String, JewelryBinding> binding : JewelryBinding.list.entrySet()) {
			if(binding.getValue().ignore) continue;
			for (Entry<String, JewelryMaterial> material : JewelryMaterial.list.entrySet()) {
				if(material.getValue().ignore) continue;
				JewelryBinding bind = binding.getValue();
				JewelryMaterial mat = material.getValue();
				ItemStack worked = JewelryHandler.createJewelry(item, bind, mat);
				ItemStack output = JewelryHandler.createJewelry((ItemJewelry)item, binding.getValue(), material.getValue());
				int hits = (int)(bind.getHitsBase(type) * mat.getHitsModifier(type));
				NEIAnvilRecipeHandler.jewelry.put(output, new RecipeJewelry(MaricultureHandlers.anvil.createWorkedItem(worked, hits), hits));
			}
		}
	}

	@Override
	public String getName() {
		return "Mariculture NEI";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
