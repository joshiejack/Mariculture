package mariculture.plugins;

import static mariculture.core.lib.MCLib.chest;
import static mariculture.core.lib.MCLib.diamond;
import static mariculture.core.lib.MCLib.fish;
import static mariculture.core.lib.MCLib.fishMeal;
import static mariculture.core.lib.MCLib.ink;
import static mariculture.core.lib.MCLib.lily;
import static mariculture.core.lib.MCLib.oyster;
import static mariculture.core.lib.MCLib.pearls;
import static mariculture.core.lib.MCLib.string;
import static mariculture.core.lib.MCLib.vanillaFish;
import static mariculture.core.lib.MCLib.wool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.DropletMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.Fluids;
import mariculture.core.util.MCTranslate;
import mariculture.core.util.RecipeItem;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import mariculture.world.WorldPlus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FermenterFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.EnumBackpackType;
import forestry.api.storage.IBackpackDefinition;

public class PluginForestry extends Plugin {
    public PluginForestry(String name) {
        super(name);
    }

    public static AquaBackpack backpack;
    public static Item aquaBackpackT1;
    public static Item aquaBackpackT2;

    @Override
    public void preInit() {
        Fluids.add("bioethanol", FluidRegistry.getFluid("bioethanol"), 1000, true);
        backpack = new AquaBackpack();
    }

    public void addBee(String str, int num) {
        try {
            ItemStack bee = new ItemStack(GameRegistry.findItem("Forestry", str));
            if (bee != null) {
                RecipeHelper.addShapeless(new ItemStack(Fishery.bait, num, BaitMeta.BEE), new Object[] { bee });
            }
        } catch (Exception e) {
            LogHandler.log(Level.INFO, "Mariculture failed to add forestry bees, as convertible to bee bait");
        }
    }

    @Override
    public void init() {
        aquaBackpackT1 = BackpackManager.backpackInterface.addBackpack(backpack, EnumBackpackType.T1);
        aquaBackpackT2 = BackpackManager.backpackInterface.addBackpack(backpack, EnumBackpackType.T2);
        GameRegistry.registerItem(aquaBackpackT1, "aquaBackpackT1");
        GameRegistry.registerItem(aquaBackpackT2, "aquaBackpackT2");
        
        backpack.addValidItem(oyster);
        backpack.addValidItem(ink);
        backpack.addValidItem(pearls);
        backpack.addValidItem(fish);
        backpack.addValidItem(new ItemStack(lily));
        if (Modules.isActive(Modules.fishery)) {
            addBee("beeDroneGE", 1);
            addBee("beePrincessGE", 5);
            addBee("beeQueenGE", 7);
            backpack.addValidItem(fishMeal);
            backpack.addValidItem(new ItemStack(Fishery.net));
            backpack.addValidItem(new ItemStack(Fishery.fishy));
            backpack.addValidItem(new ItemStack(Fishery.bait, 1, OreDictionary.WILDCARD_VALUE));
            for (int i = 0; i < DropletMeta.COUNT; i++) {
                backpack.addValidItem(new ItemStack(Core.materials, 1, i));
            }

            FuelManager.bronzeEngineFuel.put(Fluids.getFluid("fish_oil"), new EngineBronzeFuel(Fluids.getFluid("fish_oil"), 1, 7500, 1));
            for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
                FishSpecies fish = species.getValue();
                if (fish.getFishOilVolume() > 0 && fish.getLiquifiedProduct() != null && fish.getLiquifiedProductChance() > 0) {
                    RecipeManagers.squeezerManager.addRecipe(fish.getLifeSpan(), new ItemStack[] { new ItemStack(vanillaFish, 1, fish.getID()) }, Fluids.getFluidStack("fish_oil", (int) fish.getFishOilVolume() * FluidContainerRegistry.BUCKET_VOLUME), fish.getLiquifiedProduct(), fish.getLiquifiedProductChance());
                }
            }
        }

        RecipeHelper.addShaped(new ItemStack(aquaBackpackT1), new Object[] { "SWS", "FCF", "SWS", 'S', string, 'W', wool, 'F', "fish", 'C', chest });
        if (Modules.isActive(Modules.worldplus)) {
            backpack.addValidItem(new ItemStack(WorldPlus.plantGrowable, 1, OreDictionary.WILDCARD_VALUE));
            backpack.addValidItem(new ItemStack(WorldPlus.plantStatic, 1, OreDictionary.WILDCARD_VALUE));
            FuelManager.fermenterFuel.put(new ItemStack(WorldPlus.plantStatic, 1, CoralMeta.KELP), new FermenterFuel(new ItemStack(WorldPlus.plantStatic, 1, CoralMeta.KELP), 150, 1));
        }

        try {
            ItemStack crafting = new ItemStack(GameRegistry.findItem("Forestry", "craftingMaterial"), 1, 3);
            RecipeManagers.carpenterManager.addRecipe(200, FluidRegistry.getFluidStack("water", 1000), null, new ItemStack(aquaBackpackT2), new Object[] { "WDW", "WTW", "WWW", 'D', diamond, 'W', crafting, 'T', aquaBackpackT1 });
        } catch (Exception e) {
            LogHandler.log(Level.INFO, "Mariculture was unsuccesful at adding the recipe for the woven aquatic backpack");
        }

    }

    @Override
    public void postInit() {

    }

    public class AquaBackpack implements IBackpackDefinition {
        private ArrayList<ItemStack> items = new ArrayList();

        @Override
        public String getKey() {
            return "AQUA";
        }

        @Override
        public int getPrimaryColour() {
            return 4301985;
        }

        @Override
        public int getSecondaryColour() {
            return 1736058;
        }

        @Override
        public void addValidItem(ItemStack validItem) {
            if (!items.contains(validItem)) {
                items.add(validItem);
            }
        }

        @Override
        public boolean isValidItem(EntityPlayer player, ItemStack itemstack) {
            for (ItemStack stack : items)
                if (RecipeItem.equals(itemstack, stack)) return true;
            return false;
        }

        @Override
        public String getName() {
            return MCTranslate.translate("aquabackpack");
        }

        @Override
        public String getName(ItemStack backpack) {
            return getName();
        }

        @Override
        public void addValidItems(List<ItemStack> validItems) {
            items.addAll(validItems);
        }
    }
}
