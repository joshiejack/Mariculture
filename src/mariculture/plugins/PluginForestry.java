package mariculture.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import forestry.api.core.ItemInterface;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FermenterFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;
import forestry.api.storage.IBackpackDefinition;

public class PluginForestry extends Plugin {
	private static Item aquaBackpackT1;
	private static Item aquaBackpackT2;

	public PluginForestry(String name) {
		super(name);
	}
	
	@Override
	public void preInit() {
		
	}
	
	public void addBee(String str, int num) {
		ItemStack bee = ItemInterface.getItem(str);
		if(bee != null) {
			RecipeHelper.addShapelessRecipe(new ItemStack(Fishery.bait, num, BaitMeta.BEE), new Object[] { bee });
		}
	}

	@Override
	public void init() {
		if (Modules.world.isActive()) {
			FuelManager.fermenterFuel.put(new ItemStack(WorldPlus.coral, 1, CoralMeta.KELP), new FermenterFuel(
					new ItemStack(WorldPlus.coral, 1, CoralMeta.KELP), 150, 1));
		}
		
		if (Modules.fishery.isActive()) {
			addBee("beeDroneGE", 1);
			addBee("beePrincessGE", 5);
			addBee("beeQueenGE", 7);
			
			FMLInterModComms.sendMessage(
					"Forestry",
					"add-backpack-items",
					String.format("%s@%d:%d", new Object[] { "digger", Integer.valueOf(Core.oreBlocks.blockID),
							Integer.valueOf(OresMeta.LIMESTONE) }));

			if (BackpackManager.backpackInterface != null) {
				AquaBackpack backpack = new AquaBackpack();
				aquaBackpackT1 = BackpackManager.backpackInterface.addBackpack(ItemIds.aquaBPT1,
						backpack, EnumBackpackType.T1);
				aquaBackpackT2 = BackpackManager.backpackInterface.addBackpack(ItemIds.aquaBPT2,
						backpack, EnumBackpackType.T2);
				

				backpack.setup();

				CraftingManager
						.getInstance()
						.getRecipeList()
						.add(new ShapedOreRecipe(new ItemStack(aquaBackpackT1), new Object[] { "SWS", "FCF", "SWS",
								Character.valueOf('S'), Item.silk, Character.valueOf('W'),
								new ItemStack(Block.cloth, 1, OreDictionary.WILDCARD_VALUE),
								Character.valueOf('F'),
								new ItemStack(Fishery.fishyFood, 1, OreDictionary.WILDCARD_VALUE),
								Character.valueOf('C'), Block.chest }));

				ItemStack silk = ItemInterface.getItem("craftingMaterial");

				RecipeManagers.carpenterManager.addRecipe(200,
						FluidRegistry.getFluidStack("water", 1000),
						null,
						new ItemStack(aquaBackpackT2),
						new Object[] { "WDW", "WBW", "WWW", Character.valueOf('D'), Item.diamond,
									Character.valueOf('W'), Item.silk, Character.valueOf('B'), aquaBackpackT1 });
			}

			FuelManager.bronzeEngineFuel.put(FluidRegistry.getFluid(FluidDictionary.fish_oil), new EngineBronzeFuel(
					FluidRegistry.getFluid(FluidDictionary.fish_oil), 1, 7500, 1));
				
			for(int i = 0; i < FishSpecies.speciesList.size(); i++) {
				if(FishSpecies.speciesList.get(i) != null) {
					FishSpecies fish = FishSpecies.speciesList.get(i);
					int id = fish.fishID;
						
					RecipeManagers.squeezerManager.addRecipe(fish.getLifeSpan(), new ItemStack[] { new ItemStack(Fishery.fishyFood, 1, id) }, 
							new FluidStack(Fishery.fishOil, (int) fish.getFishOilVolume() * FluidContainerRegistry.BUCKET_VOLUME), fish.getLiquifiedProduct(), fish.getLiquifiedProductChance());
				}
			}
		}
	}

	@Override
	public void postInit() {

	}
	
	@Optional.Interface(iface = "forestry.api.storage.IBackpackDefinition", modid = "Forestry")
	public class AquaBackpack implements IBackpackDefinition {
		private final List items = new ArrayList(50);
		public void setup() {
			if (Modules.fishery.isActive()) {
				addValidItem(new ItemStack(Fishery.fishy, 1, OreDictionary.WILDCARD_VALUE));
				addValidItem(new ItemStack(Fishery.fishyFood, 1, OreDictionary.WILDCARD_VALUE));
				addValidItem(new ItemStack(Fishery.bait, 1, OreDictionary.WILDCARD_VALUE));
			}
			
			if(Modules.world.isActive()) {
				addValidItem(new ItemStack(WorldPlus.coral, 1, OreDictionary.WILDCARD_VALUE));
			}
			
			addValidItem(new ItemStack(Block.waterlily));
			addValidItem(new ItemStack(Item.fishRaw));
			addValidItem(new ItemStack(Item.dyePowder, 1, Dye.INK));
			addValidItem(new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE));
			addValidItem(new ItemStack(Core.materials, 1, MaterialsMeta.FISH_MEAL));
			addValidItem(new ItemStack(Core.oysterBlock));
			for(int i = 0; i < 9; i++) {
				addValidItem(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER + i));
			}
		}

		public String getKey() {
			return "AQUA";
		}

		public String getName() {
			return StatCollector.translateToLocal("item.aquaBackpack.name");
		}

		public int getPrimaryColour() {
			return 4301985;
		}

		public int getSecondaryColour() {
			return 1736058;
		}

		public void addValidItem(ItemStack validItem) {
			if (validItem == null) {
				return;
			}
			this.items.add(validItem);
		}

		public Collection<ItemStack> getValidItems(EntityPlayer player) {
			return this.items;
		}

		public boolean isValidItem(EntityPlayer player, ItemStack itemstack) {
			for (ItemStack stack : getValidItems(player)) {
				if (stack.getItemDamage() < 0) {
					if (stack.itemID == itemstack.itemID) {
						return true;
					}
				} else if (stack.isItemEqual(itemstack)) {
					return true;
				}
			}

			return false;
		}
	}
}
