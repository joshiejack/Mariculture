package mariculture.core;

import static mariculture.core.lib.ItemLib.blockSnow;
import static mariculture.core.lib.ItemLib.clay;
import static mariculture.core.lib.ItemLib.dustMagnesium;
import static mariculture.core.lib.ItemLib.dustSalt;
import static mariculture.core.lib.ItemLib.glass;
import static mariculture.core.lib.ItemLib.glassPane;
import static mariculture.core.lib.ItemLib.ice;
import static mariculture.core.lib.ItemLib.limestone;
import static mariculture.core.lib.ItemLib.limestoneSmooth;
import static mariculture.core.lib.ItemLib.obsidian;
import static mariculture.core.lib.ItemLib.salt;
import static mariculture.core.lib.ItemLib.sand;
import static mariculture.core.lib.ItemLib.stone;
import static mariculture.core.util.Fluids.getFluidStack;
import static mariculture.core.util.Fluids.getTheFluid;
import static mariculture.core.util.Fluids.getTheName;

import java.util.ArrayList;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.helpers.ItemHelper;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.TransparentMeta;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesSmelting {
    public static int iron = 1538;
    public static int gold = 1064;
    public static int tin = 232;
    public static int copper = 1085;
    public static int silver = 962;
    public static int lead = 328;
    public static int magnesium = 650;
    public static int nickel = 1455;
    public static int bronze = 950;
    public static int steel = 1370;
    public static int aluminum = 660;
    public static int titanium = 1662;
    public static int electrum = 1000;
    public static int alloy = 1725;

    public static void addRecipe(String fluidName, Object[] item, int[] volume, int temperature) {
        addRecipe(fluidName, item, volume, temperature, null, 0);
    }

    public static void addRecipe(String fluidName, Object[] item, int[] volume, int temperature, ItemStack bonus, int chance) {
        Fluid fluid = FluidRegistry.getFluid(fluidName);
        if (fluid == null) return;
        for (int i = 0; i < item.length; i++) {
            Object o = item[i];
            ArrayList<ItemStack> stacks = new ArrayList();
            if (o instanceof String) {
                stacks = OreDictionary.getOres((String) o);
            } else if (o instanceof ItemStack) {
                stacks.add((ItemStack) o);
            } else if (o instanceof Block) {
                stacks.add(new ItemStack((Block) o));
            } else if (o instanceof Item) {
                stacks.add(new ItemStack((Item) o));
            }

            for (ItemStack stack : stacks)
                if (bonus == null || chance <= 0) {
                    RecipeHelper.addMelting(stack, temperature, new FluidStack(fluid, volume[i]));
                } else {
                    RecipeHelper.addMelting(stack, temperature, new FluidStack(fluid, volume[i]), bonus, chance);
                }
        }
    }

    public static void add() {
        addFuels();
        addMetalRecipes();
    }

    public static void postAdd() {
        ItemStack sulfur = fetchItem(new String[] { "dustSulfur", "crystalSulfur" });
        ItemStack salt = new ItemStack(Core.materials, 1, MaterialsMeta.DUST_SALT);
        ItemStack silicon = fetchItem(new String[] { "itemSilicon", "dustSiliconDioxide" });
        ItemStack platinum = fetchItem(new String[] { "dustPlatinum", "ingotPlatinum" });

        //Copperous Dust
        LinkedMetal[] coppers = new LinkedMetal[] { new LinkedMetal("ingotIron", getTheName("iron"), 4), new LinkedMetal("ingotSilver", getTheName("silver"), 7), new LinkedMetal("ingotGold", getTheName("gold"), 10), new LinkedMetal("ingotCobalt", getTheName("cobalt"), 15), new LinkedMetal("ingotNickel", getTheName("nickel"), 8), new LinkedMetal("ingotLead", getTheName("lead"), 7), new LinkedMetal("ingotTin", getTheName("tin"), 6) };
        addDust(MaterialsMeta.DUST_COPPEROUS, copper, sulfur, 10, coppers);

        //Golden Dust
        LinkedMetal[] golds = new LinkedMetal[] { new LinkedMetal("ingotElectrum", getTheName("electrum"), 3), new LinkedMetal("ingotSilver", getTheName("silver"), 7), new LinkedMetal("ingotGold", getTheName("gold"), 25) };
        addDust(MaterialsMeta.DUST_GOLDEN, gold, null, 0, golds);

        //Ironic Dust
        LinkedMetal[] irons = new LinkedMetal[] { new LinkedMetal("ingotAluminum", getTheName("aluminum"), 3), new LinkedMetal("ingotTin", getTheName("iron"), 8), new LinkedMetal("ingotCopper", getTheName("copper"), 6) };
        addDust(MaterialsMeta.DUST_IRONIC, iron, silicon, 6, irons);

        //Leader Dust
        LinkedMetal[] leads = new LinkedMetal[] { new LinkedMetal("ingotSilver", getTheName("silver"), 3), new LinkedMetal("ingotIron", getTheName("iron"), 6), new LinkedMetal("ingotCopper", getTheName("copper"), 8), new LinkedMetal("ingotTin", getTheName("tin"), 10) };
        addDust(MaterialsMeta.DUST_LEADER, lead, null, 0, leads);

        //Silvery Dust
        LinkedMetal[] silvers = new LinkedMetal[] { new LinkedMetal("ingotLead", getTheName("lead"), 2), new LinkedMetal("ingotElectrum", getTheName("electrum"), 4) };
        addDust(MaterialsMeta.DUST_SILVERY, silver, sulfur, 5, silvers);

        //Tinnic Dust
        LinkedMetal[] tins = new LinkedMetal[] { new LinkedMetal("ingotCopper", getTheName("copper"), 3), new LinkedMetal("ingotIron", getTheName("iron"), 6), new LinkedMetal("ingotLead", getTheName("lead"), 8) };
        addDust(MaterialsMeta.DUST_TINNIC, tin, sulfur, 7, tins);

        addMetal(getTheName("tin"), "Tin", tin, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_TINNIC), 10);
        addMetal(getTheName("copper"), "Copper", copper, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_COPPEROUS), 10);
        addMetal(getTheName("silver"), "Silver", silver, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_SILVERY), 10);
        addMetal(getTheName("lead"), "Lead", lead, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_LEADER), 10);
        addMetal(getTheName("nickel"), "Nickel", nickel, platinum, 10);
        addMetal(getTheName("bronze"), "Bronze", bronze, null, 0);
        addMetal(getTheName("steel"), "Steel", steel, null, 0);
        addMetal(getTheName("electrum"), "Electrum", electrum, null, 0);

        //Gold + Silver = Electrum
        if (OreDictionary.getOres("ingotElectrum").size() > 0 && OreDictionary.getOres("ingotSilver").size() > 0) {
            FluidStack moltenSilver =getFluidStack("silver", MetalRates.NUGGET);
            FluidStack moltenGold = getFluidStack("gold", MetalRates.NUGGET);
            FluidStack moltenElectrum = getFluidStack("electrum", MetalRates.NUGGET * 2);
            RecipeHelper.addFluidAlloy(moltenSilver, moltenGold, moltenElectrum, 1);
        }
    }

    private static class LinkedMetal {
        public String ingot;
        public String fluid;
        public Integer chance;

        public LinkedMetal(String ingot, String fluid, Integer chance) {
            this.ingot = ingot;
            this.fluid = fluid;
            this.chance = chance;
        }
    }

    private static void addDust(int meta, int temp, ItemStack bonus, int chance, LinkedMetal[] metals) {
        ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();
        ArrayList<Integer> chances = new ArrayList<Integer>();

        for (LinkedMetal metal : metals)
            if (OreDictionary.getOres(metal.ingot).size() > 0 && get(metal.fluid) != null) {
                fluids.add(get(metal.fluid));
                chances.add(metal.chance);
            }

        if (fluids.size() > 0) {
            MaricultureHandlers.crucible.addRecipe(new RecipeSmelter(new ItemStack(Core.materials, 1, meta), temp, fluids.toArray(new FluidStack[fluids.size()]), chances.toArray(new Integer[chances.size()]), bonus, chance));
        }
    }

    private static ItemStack fetchItem(String[] array) {
        for (String arr : array)
            if (OreDictionary.getOres(arr).size() > 0) return OreDictionary.getOres(arr).get(0);

        return null;
    }

    private static void addFuels() {
        RecipeHelper.addFuel("blockCoal", new FuelInfo(2000, 378, 10800));
        RecipeHelper.addFuel(new ItemStack(Items.coal, 1, 0), new FuelInfo(2000, 42, 1200));
        RecipeHelper.addFuel(new ItemStack(Items.coal, 1, 1), new FuelInfo(1600, 32, 900));
        RecipeHelper.addFuel("logWood", new FuelInfo(480, 12, 300));
        RecipeHelper.addFuel("plankWood", new FuelInfo(320, 8, 200));
        RecipeHelper.addFuel("stickWood", new FuelInfo(160, 4, 100));
        RecipeHelper.addFuel(getTheName("natural_gas"), new FuelInfo(2000, 35, 1200));
        RecipeHelper.addFuel("gascraft_naturalgas", new FuelInfo(2000, 35, 1000));
        RecipeHelper.addFuel("fuel", new FuelInfo(2000, 35, 1000));
        RecipeHelper.addFuel("pyrotheum", new FuelInfo(2000, 100, 100));
        RecipeHelper.addFuel("coal", new FuelInfo(2000, 40, 300));
        RecipeHelper.addFuel("biofuel", new FuelInfo(1800, 20, 2000));
        RecipeHelper.addFuel("oil", new FuelInfo(1750, 20, 400));
        RecipeHelper.addFuel("lava", new FuelInfo(1500, 20, 360));
        RecipeHelper.addFuel("biomass", new FuelInfo(1500, 10, 1800));
        RecipeHelper.addFuel("bioethanol", new FuelInfo(1500, 10, 1800));
        //Ender IO
        RecipeHelper.addFuel("hootch", new FuelInfo(1800, 35, 250));
        RecipeHelper.addFuel("fire_water", new FuelInfo(1900, 40, 500));
        RecipeHelper.addFuel("rocket_fuel", new FuelInfo(2000, 45, 750));
    }

    public static void addFullSet(String fluid, Object[] items, int temp, ItemStack output, int chance) {
        addRecipe(fluid, new Object[] { items[0] }, MetalRates.ORES, temp, output, chance);
        addRecipe(fluid, new Object[] { items[1], items[2], items[3], items[4] }, MetalRates.METALS, temp);
        addRecipe(fluid, new Object[] { items[5], items[6], items[7], items[8], items[9] }, MetalRates.TOOLS, temp, new ItemStack(Items.stick), 1);
        addRecipe(fluid, new Object[] { items[10], items[11], items[12], items[13] }, MetalRates.ARMOR, temp);
    }

    public static void addMetal(String fluid, String metal, int temp, ItemStack bonus, int chance) {
        addRecipe(fluid, new Object[] { "ore" + metal }, MetalRates.ORES, temp, bonus, chance);
        addRecipe(fluid, new Object[] { "nugget" + metal, "ingot" + metal, "block" + metal, "dust" + metal }, MetalRates.METALS, temp);

        if (OreDictionary.getOres("ingot" + metal).size() > 0) {
            RecipeHelper.addMetalCasting(metal);
        }
    }

    public static void addMetalRecipes() {
        addFullSet(getTheName("iron"), new Object[] { "oreIron", "nuggetIron", "ingotIron", "blockIron", "dustIron", Items.iron_pickaxe, Items.iron_shovel, Items.iron_axe, Items.iron_sword, Items.iron_hoe, Items.iron_helmet, Items.iron_chestplate, Items.iron_leggings, Items.iron_boots }, iron, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_IRONIC), 10);
        RecipeHelper.addMetalCasting("Iron");

        addFullSet(getTheName("gold"), new Object[] { "oreGold", "nugetGold", "ingotGold", "blockGold", "dustGold", Items.golden_pickaxe, Items.golden_shovel, Items.golden_axe, Items.golden_sword, Items.golden_hoe, Items.golden_helmet, Items.golden_chestplate, Items.golden_leggings, Items.golden_boots }, gold, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_GOLDEN), 10);
        RecipeHelper.addMetalCasting("Gold");

        addMetal(getTheName("aluminum"), "Aluminum", aluminum, new ItemStack(clay), 5);
        addMetal(getTheName("rutile"), "Rutile", titanium, limestone, 2);
        addMetal(getTheName("titanium"), "Titanium", titanium, limestone, 2);
        addMetal(getTheName("magnesium"), "Magnesium", magnesium, new ItemStack(stone), 2);

        FluidStack moltenRutile = getFluidStack("rutile", MetalRates.INGOT);
        FluidStack moltenMagnesium = getFluidStack("magnesium", MetalRates.INGOT);
        FluidStack moltenTitanium = getFluidStack("titanium", MetalRates.INGOT);
        RecipeHelper.addFluidAlloy(moltenRutile, moltenMagnesium, moltenTitanium, 6);
        RecipeHelper.addMelting(dustMagnesium, magnesium, get(getTheName("magnesium")), salt, 1);

        //Gold Back
        RecipeHelper.addMelting(new ItemStack(Blocks.light_weighted_pressure_plate), gold, gold(MetalRates.INGOT * 2));
        RecipeHelper.addMelting(new ItemStack(Items.clock), gold, gold(MetalRates.INGOT * 4), new ItemStack(Items.redstone), 2);
        RecipeHelper.addMelting(new ItemStack(Items.golden_horse_armor), gold, gold(MetalRates.INGOT * 6), new ItemStack(Items.saddle), 4);

        //Iron Back
        RecipeHelper.addMelting(new ItemStack(Items.bucket), iron, getTheName("iron"), MetalRates.INGOT * 3);
        RecipeHelper.addMelting(new ItemStack(Items.iron_door), iron, getTheName("iron"), MetalRates.INGOT * 6);
        RecipeHelper.addMelting(new ItemStack(Blocks.iron_bars), iron, getTheName("iron"), (int) (MetalRates.INGOT * 0.25));
        RecipeHelper.addMelting(new ItemStack(Items.shears), iron, getTheName("iron"), MetalRates.INGOT * 2);
        RecipeHelper.addMelting(new ItemStack(Blocks.anvil, 1, 0), iron, getTheName("iron"), MetalRates.INGOT * 31);
        RecipeHelper.addMelting(new ItemStack(Blocks.anvil, 1, 1), iron, getTheName("iron"), MetalRates.INGOT * 22);
        RecipeHelper.addMelting(new ItemStack(Blocks.anvil, 1, 2), iron, getTheName("iron"), MetalRates.INGOT * 13);
        RecipeHelper.addMelting(new ItemStack(Blocks.heavy_weighted_pressure_plate), iron, getTheName("iron"), MetalRates.INGOT * 2);
        RecipeHelper.addMelting(new ItemStack(Items.compass), iron, iron(MetalRates.INGOT * 4), new ItemStack(Items.redstone), 2);
        RecipeHelper.addMelting(new ItemStack(Blocks.hopper), iron, iron(MetalRates.INGOT * 5), new ItemStack(Blocks.chest), 2);
        RecipeHelper.addMelting(new ItemStack(Items.flint_and_steel), iron, iron(MetalRates.INGOT));
        RecipeHelper.addMelting(new ItemStack(Items.iron_horse_armor), iron, iron(MetalRates.INGOT * 6), new ItemStack(Items.saddle), 4);

        //Glass, Ice, Snow, Plastic, Obisidian
        RecipeHelper.addBlockCasting(get(getTheName("glass"), 1000), new ItemStack(glass));
        RecipeHelper.addMelting(new ItemStack(sand), 1000, getTheName("glass"), 1000);
        RecipeHelper.addMelting(new ItemStack(glass), 900, getTheName("glass"), 1000);
        RecipeHelper.addMelting(new ItemStack(glassPane), 500, getTheName("glass"), 375);
        RecipeHelper.addMelting(new ItemStack(ice), 1, "water", 1000);
        RecipeHelper.addMelting(new ItemStack(blockSnow), 1, "water", 1000);

        RecipeHelper.addVatItemRecipe(new ItemStack(glass, 32), getTheName("natural_gas"), 25000, new ItemStack(Core.transparent, 32, TransparentMeta.PLASTIC), 60);
        RecipeHelper.addVatItemRecipe(new ItemStack(glass), getTheName("natural_gas"), 1000, new ItemStack(Core.transparent, 1, TransparentMeta.PLASTIC), 5);
        if (getTheFluid("bioethanol") != null) {
            RecipeHelper.addVatItemRecipe(new ItemStack(glass, 32), getTheName("bioethanol"), 30000, new ItemStack(Core.transparent, 32, TransparentMeta.PLASTIC), 100);
            RecipeHelper.addVatItemRecipe(new ItemStack(glass), getTheName("bioethanol"), 1500, new ItemStack(Core.transparent, 1, TransparentMeta.PLASTIC), 10);
        }

        RecipeHelper.addFluidAlloyResultItem(get("water", 1000), get("lava", 1000), new ItemStack(obsidian), 10);

        //8 Parts Quicklime + 5 Parts Water = Unknown Metal Dust + 3 Parts Water (Takes 10 seconds)
        RecipeHelper.addFluidAlloyResultItemNFluid(get("water", 3000), get(getTheName("quicklime"), 4000), get("water", 2000), dustMagnesium, 10);

        ArrayList<ItemStack> added = new ArrayList();
        for (ItemStack stack : OreDictionary.getOres("blockLimestone")) {
            if (stack == null || stack.getItem() == null) {
                continue;
            }
            RecipeHelper.addMelting(stack, 825, get(getTheName("quicklime"), 900));
            added.add(stack);
        }

        for (ItemStack stack : OreDictionary.getOres("limestone")) {
            boolean exists = false;
            if (stack == null || stack.getItem() == null) {
                continue;
            }
            for (ItemStack check : added)
                if (ItemHelper.areEqual(stack, check)) {
                    exists = true;
                }

            if (!exists) {
                RecipeHelper.addMelting(stack, 825, get(getTheName("quicklime"), 900));
            }
        }

        RecipeHelper.addMelting(limestoneSmooth, 825, get(getTheName("quicklime"), 1000));
        RecipeHelper.addMelting(dustSalt, 801, get(getTheName("salt"), 20));
        RecipeHelper.addFluidAlloyNItemResultItem(get(getTheName("aluminum"), MetalRates.NUGGET * 64), get(getTheName("quicklime"), 20000), new ItemStack(glass, 64), new ItemStack(Core.glass, 64, GlassMeta.HEAT), 90);
        RecipeHelper.addFluidAlloyNItemResultItem(get(getTheName("aluminum"), MetalRates.NUGGET), get(getTheName("quicklime"), 450), new ItemStack(glass), new ItemStack(Core.glass, 1, GlassMeta.HEAT), 5);
    }

    public static FluidStack gold(int vol) {
        return getFluidStack("gold", vol);
    }

    public static FluidStack iron(int vol) {
        return getFluidStack("iron", vol);
    }

    public static FluidStack get(String name, int vol) {
        return FluidRegistry.getFluidStack(name, vol);
    }

    public static FluidStack get(String name) {
        return FluidRegistry.getFluidStack(name, MetalRates.INGOT);
    }
}
