package mariculture.plugins.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import mariculture.magic.JewelryHandler;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import mariculture.magic.jewelry.parts.JewelryBinding;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import mariculture.plugins.nei.NEIAnvilRecipeHandler.RecipeJewelry;
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
			API.hideItem(new ItemStack(Fishery.fishEggs, 1, OreDictionary.WILDCARD_VALUE));
			API.registerRecipeHandler(new NEISifterRecipeHandler());
			API.registerUsageHandler(new NEISifterRecipeHandler());
			API.registerRecipeHandler(new NEIFishBreedingMutationHandler());
			API.registerUsageHandler(new NEIFishBreedingMutationHandler());
			API.registerRecipeHandler(new NEIFishProductHandler());
			API.registerUsageHandler(new NEIFishProductHandler());
			if(Extra.DISABLE_FISH) {
				API.hideItem(new ItemStack(Fishery.fishy));
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
