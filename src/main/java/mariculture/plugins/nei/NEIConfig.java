package mariculture.plugins.nei;

import java.util.ArrayList;
import java.util.HashMap;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import mariculture.magic.JewelryHandler;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.parts.JewelryPart;
import mariculture.plugins.nei.NEIAnvilRecipeHandler.RecipeJewelry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
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
		
		if(Modules.isActive(Modules.fishery)) {
			API.registerRecipeHandler(new NEISifterRecipeHandler());
			API.registerUsageHandler(new NEISifterRecipeHandler());
			API.registerRecipeHandler(new NEIFishBreedingMutationHandler());
			API.registerUsageHandler(new NEIFishBreedingMutationHandler());
			API.registerRecipeHandler(new NEIFishProductHandler());
			API.registerUsageHandler(new NEIFishProductHandler());
			if(Extra.DISABLE_FISH) {
				API.hideItem(Fishery.fishy.itemID);
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
			addJewelry(Magic.ring.itemID, Jewelry.RING, Jewelry.RING_PART1, Jewelry.RING_PART2);
			addJewelry(Magic.bracelet.itemID, Jewelry.BRACELET, Jewelry.BRACELET_PART1, Jewelry.BRACELET_PART2);
			addJewelry(Magic.necklace.itemID, Jewelry.NECKLACE, Jewelry.NECKLACE_PART1, Jewelry.NECKLACE_PART2);
		}
	}
	
	private void addJewelry(int id, int type, String partOne, String partTwo) {
		for (int i = 0; i < JewelryPart.materialList.size(); i++) {
			for (int j = 0; j < JewelryPart.materialList.size(); j++) {
				if (JewelryPart.materialList.get(i).isValid(type) && JewelryPart.materialList.get(j).isValid(type)) {
					if (JewelryPart.materialList.get(i).getPartType(type).equals(partOne)) {
						if (JewelryPart.materialList.get(j).getPartType(type).equals(partTwo)) {
							ItemStack output = ItemJewelry.buildJewelry(id, i, j);
							output = JewelryPart.materialList.get(i).addEnchantments(output);
							if (i != j) {
								output = JewelryPart.materialList.get(j).addEnchantments(output);
							}
							ItemStack input1 = JewelryPart.materialList.get(i).getItemStack();
							ItemStack input2 = JewelryPart.materialList.get(j).getItemStack();
							int Multiply1 = (type == Jewelry.RING)? 1: (type == Jewelry.BRACELET)? 3: 7;
							int Multiply2 = (type == Jewelry.RING)? 7: (type == Jewelry.BRACELET)? 2: 1;
							int frame = JewelryPart.materialList.get(i).getHits(type) * Multiply1;
							int other = JewelryPart.materialList.get(j).getHits(type) * Multiply2;
							int hits = frame + other;
							if (input1 != null && input2 != null && output != null) {
								String damage = "" + JewelryHandler.getUIdentifier(output, input1, input2);
								ItemStack piece = MaricultureHandlers.anvil.createWorkedItem(output, hits);
								piece.setItemDamage(Integer.parseInt(damage));
								
								NEIAnvilRecipeHandler.jewelry.put(output, new RecipeJewelry(piece, hits));
							}
						}
					}
				}
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
