package mariculture.plugins;

import mariculture.plugins.Plugins.Plugin;
public class PluginForestry extends Plugin {

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
		if (Modules.isActive(Modules.world)) {
			FuelManager.fermenterFuel.put(new ItemStack(WorldPlus.coral, 1, CoralMeta.KELP), new FermenterFuel(
					new ItemStack(WorldPlus.coral, 1, CoralMeta.KELP), 150, 1));
		}
		
		if (Modules.isActive(Modules.fishery)) {
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
				aquaBackpackT1 = BackpackManager.backpackInterface.addBackpack(aquaBPT1,
						backpack, EnumBackpackType.T1);
				aquaBackpackT2 = BackpackManager.backpackInterface.addBackpack(aquaBPT2,
						backpack, EnumBackpackType.T2);
				

				backpack.setup();

				CraftingManager
						.getInstance()
						.getRecipeList()
						.add(new ShapedOreRecipe(new ItemStack(aquaBackpackT1), new Object[] { "SWS", "FCF", "SWS",
								Character.valueOf('S'), Items.silk, Character.valueOf('W'),
								new ItemStack(Blocks.cloth, 1, OreDictionary.WILDCARD_VALUE),
								Character.valueOf('F'),
								new ItemStack(Fishery.fishyFood, 1, OreDictionary.WILDCARD_VALUE),
								Character.valueOf('C'), Blocks.chest }));

				ItemStack silk = ItemInterface.getItem("craftingMaterial");
				silk.setItemDamage(3);

				RecipeManagers.carpenterManager.addRecipe(200,
						FluidRegistry.getFluidStack("water", 1000),
						null,
						new ItemStack(aquaBackpackT2),
						new Object[] { "WDW", "WTW", "WWW", Character.valueOf('D'), Items.diamond,
									Character.valueOf('W'), silk, Character.valueOf('T'), aquaBackpackT1 });
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
	
	@Optional.Interface(iface = "api.storage.IBackpackDefinition", modid = "Forestry")
	public class AquaBackpack implements IBackpackDefinition {
		private final List items = new ArrayList(50);
		public void setup() {
			if (Modules.isActive(Modules.fishery)) {
				addValidItem(new ItemStack(Fishery.fishy, 1, OreDictionary.WILDCARD_VALUE));
				addValidItem(new ItemStack(Fishery.fishyFood, 1, OreDictionary.WILDCARD_VALUE));
				addValidItem(new ItemStack(Fishery.bait, 1, OreDictionary.WILDCARD_VALUE));
			}
			
			if(Modules.isActive(Modules.world)) {
				addValidItem(new ItemStack(WorldPlus.coral, 1, OreDictionary.WILDCARD_VALUE));
			}
			
			addValidItem(new ItemStack(Blocks.waterlily));
			addValidItem(new ItemStack(Items.fishRaw));
			addValidItem(new ItemStack(Items.dyePowder, 1, Dye.INK));
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
	} */
}
