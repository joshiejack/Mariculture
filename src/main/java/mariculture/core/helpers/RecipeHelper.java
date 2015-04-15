package mariculture.core.helpers;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.api.core.IFuelTickHandler;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting.RecipeBlockCasting;
import mariculture.api.core.RecipeCasting.RecipeIngotCasting;
import mariculture.api.core.RecipeCasting.RecipeNuggetCasting;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeVat;
import mariculture.core.Core;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHelper {
    /** Returns an ItemStack **/
    public static ItemStack asStack(Object object) {
        return object instanceof Block? new ItemStack((Block)object) : new ItemStack((Item)object);
    }
    
    /** Create an itemstack from this item and damage **/
    public static ItemStack asStack(Object object, int damage) {
        return object instanceof Block? new ItemStack((Block)object, 1, damage) : new ItemStack((Item)object, 1, damage);
    }
    
    /** Create and itemstack, from object, size and damage **/
    public static ItemStack asStack(Object object, int size, int damage) {
        return object instanceof Block? new ItemStack((Block)object, size, damage) : new ItemStack((Item)object, size, damage);
    }

    /** Returns an itemstack of the type specified with the size specified **/
    public static ItemStack asStack(ItemStack stack, int amount) {
        ItemStack ret = stack.copy();
        ret.stackSize = amount;
        return ret;
    }

    /** Returns and itemstack, changing the damage and amount **/
    public static ItemStack asStack(ItemStack stack, int dmg, int amount) {
        ItemStack ret = stack.copy();
        ret.setItemDamage(dmg);
        ret.stackSize = amount;
        return ret;
    }
    


    public static void addShaped(ItemStack result, Object[] input) {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, input));
    }

    public static void addShapeless(ItemStack result, Object[] input) {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(result, input));
    }

    public static void addSmelting(ItemStack output, ItemStack input, float xp) {
        GameRegistry.addSmelting(input, output, xp);
    }

    public static void addVatItemRecipe(Object input, String fluid, int vol, ItemStack output, int time) {
        MaricultureHandlers.vat.addRecipe(new RecipeVat(input, FluidRegistry.getFluidStack(fluid, vol), output, time));
    }

    public static void addVatItemRecipeResultFluid(ItemStack input, FluidStack inputFluid, FluidStack output, int time) {
        MaricultureHandlers.vat.addRecipe(new RecipeVat(input, inputFluid, output, time));
    }

    public static void addNuggetCasting(FluidStack fluid, ItemStack stack) {
        MaricultureHandlers.casting.addRecipe(new RecipeNuggetCasting(fluid, stack));
    }

    public static void addIngotCasting(FluidStack fluid, ItemStack stack) {
        MaricultureHandlers.casting.addRecipe(new RecipeIngotCasting(fluid, stack));
    }

    public static void addBlockCasting(FluidStack fluid, ItemStack stack) {
        MaricultureHandlers.casting.addRecipe(new RecipeBlockCasting(fluid, stack));
    }

    public static void addMetalCasting(String metal) {
        String nugget = "nugget" + metal;
        String ingot = "ingot" + metal;
        String block = "block" + metal;
        if (OreDictionary.getOres(nugget).size() > 0) {
            RecipeHelper.addNuggetCasting(Fluids.getFluidStack(metal.toLowerCase(), MetalRates.NUGGET), OreDictionary.getOres(nugget).get(0));
        }
        if (OreDictionary.getOres(ingot).size() > 0) {
            RecipeHelper.addIngotCasting(Fluids.getFluidStack(metal.toLowerCase(), MetalRates.INGOT), OreDictionary.getOres(ingot).get(0));
        }
        if (OreDictionary.getOres(block).size() > 0) {
            RecipeHelper.addBlockCasting(Fluids.getFluidStack(metal.toLowerCase(), MetalRates.BLOCK), OreDictionary.getOres(block).get(0));
        }
    }

    public static void addFluidAlloy(FluidStack fluid1, FluidStack fluid2, FluidStack result, int time) {
        MaricultureHandlers.vat.addRecipe(new RecipeVat(fluid1, fluid2, result, time));
    }

    public static void addFluidAlloyResultItem(FluidStack fluid1, FluidStack fluid2, ItemStack result, int time) {
        MaricultureHandlers.vat.addRecipe(new RecipeVat(fluid1, fluid2, result, time));
    }

    public static void addFluidAlloyResultItemNFluid(FluidStack fluid1, FluidStack fluid2, FluidStack newFluid, ItemStack result, int time) {
        MaricultureHandlers.vat.addRecipe(new RecipeVat(fluid1, fluid2, newFluid, result, time));
    }

    public static void addFluidAlloyNItemResultItem(FluidStack fluid1, FluidStack fluid2, ItemStack input, ItemStack result, int time) {
        MaricultureHandlers.vat.addRecipe(new RecipeVat(input, fluid1, fluid2, result, time));
    }

    public static void add4x4Recipe(ItemStack result, Block block, int meta) {
        addShaped(result, new Object[] { "##", "##", '#', new ItemStack(block, 1, meta) });
    }

    public static void add4x4Recipe(ItemStack result, Object input) {
        addShaped(result, new Object[] { "##", "##", '#', input });
    }

    public static void add9x9Recipe(ItemStack result, Object input) {
        addShaped(result, new Object[] { "###", "###", "###", '#', input });
    }

    public static void addUncraftingRecipe(ItemStack result, Object input) {
        addShapeless(result, new Object[] { input });
    }

    public static void addAnvilRecipe(ItemStack input, ItemStack output, int hits) {
        MaricultureHandlers.anvil.addRecipe(new RecipeAnvil(input, output, hits));
    }

    public static void addFishingRodRecipe(ItemStack output, Object mat) {
        addShaped(output, new Object[] { "  S", " SW", "S W", 'S', mat, 'W', Items.string });
    }

    public static void addMelting(ItemStack stack, int temp, FluidStack fluid) {
        MaricultureHandlers.crucible.addRecipe(new RecipeSmelter(stack, temp, fluid, null, 0));
    }

    public static void addMelting(ItemStack stack, int temp, FluidStack fluid, ItemStack output, int chance) {
        MaricultureHandlers.crucible.addRecipe(new RecipeSmelter(stack, temp, fluid, output, chance));
    }

    public static void addMelting(ItemStack stack, int temp, String fluid, int vol) {
        addMelting(stack, temp, FluidRegistry.getFluidStack(fluid, vol));
    }

    public static void add2x2Recipe(ItemStack result, Object input) {
        addShaped(result, new Object[] { "##", "##", '#', input });
    }

    public static void add3x3Recipe(ItemStack result, Object input) {
        addShaped(result, new Object[] { "###", "###", "###", '#', input });
    }

    /** @param output - ItemStack
     * @param item1 - 5 x this
     * @param item2 - 4 x this */
    public static void addCrossHatchRecipe(ItemStack output, Object item1, Object item2) {
        addShaped(output, new Object[] { "CAC", "ACA", "CAC", 'C', item1, 'A', item2 });
    }

    /** @param output - ItemStack
     * @param wheel - Outer Rim
     * @param spoke - Inner Piece */
    public static void addWheelRecipe(ItemStack output, Object wheel, Object spoke) {
        addShaped(output, new Object[] { " W ", "WSW", " W ", 'W', wheel, 'S', spoke });
    }

    /** Adds a fuel to be used by the smelter
     * 
     * @param obj - Can be FluidStack, ItemStack or String
     * @param fuelInfo
     */
    public static void addFuel(Object obj, FuelInfo fuelInfo) {
        MaricultureHandlers.crucible.addFuel(obj, fuelInfo);
    }
    
    public static void addTickHandlingFuel(Object obj, FuelInfo fuelInfo, IFuelTickHandler handler) {
    	MaricultureHandlers.crucible.addFuel(obj, fuelInfo);
    	MaricultureHandlers.crucible.addFuelHandler(obj, handler);
    }

    public static void addCrushRecipe(ItemStack stack, Object string, boolean needAnvil) {
        if (!needAnvil) {
            addShapeless(stack, new Object[] { string });
        }
        ItemStack result = null;
        if (string instanceof String) if (OreDictionary.getOres((String) string).size() > 0) {
            result = OreDictionary.getOres((String) string).get(0);
        }

        if (string instanceof ItemStack) {
            result = (ItemStack) string;
        }

        if (result != null) {
            if (!needAnvil) {
                stack.stackSize *= 2;
            }
            addAnvilRecipe(result, stack, 5);
        }
    }

    public static void addBleachRecipe(ItemStack input, ItemStack output, int time) {
        addVatItemRecipe(input, Fluids.getFluidName("quicklime"), 50, output, time);
    }

    public static void addSoakRecipe(ItemStack input, ItemStack output, int time) {
        addVatItemRecipe(input, "water", 100, output, time);
    }

    public static void addBookRecipe(ItemStack output, ItemStack input) {
        addShapeless(output, new Object[] { input, Items.book });
    }

    public static void addFishMelting(ItemStack stack, double volume, ItemStack product, int chance) {
        addMelting(stack, 180, Fluids.getFluidStack("fish_oil", (int) (volume * 1000)), product, chance);
    }

    public static void addFishSushi(ItemStack raw, int meal) {
        ItemStack kelp = new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP);
        if (OreDictionary.getOres("cropRice").size() > 0) {
            addShaped(new ItemStack(Core.food, (int) Math.ceil(meal / 1.5), FoodMeta.SUSHI), new Object[] { "RKR", "KFK", "RKR", 'R', kelp, 'F', raw, 'K', "cropRice" });
        } else {
            addShaped(new ItemStack(Core.food, (int) Math.ceil(meal / 1.5), FoodMeta.SUSHI), new Object[] { " K ", "KFK", " K ", 'K', kelp, 'F', raw });
        }
    }

    public static void addFishSoup(ItemStack raw, int meal) {
        ItemStack kelp = new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP);
        int number = (int) Math.ceil(meal / 2);
        int meta = number == 2 ? FoodMeta.MISO_SOUP_2 : number >= 3 ? FoodMeta.MISO_SOUP_3 : FoodMeta.MISO_SOUP_1;
        RecipeHelper.addShapeless(new ItemStack(Core.food, 1, meta), new Object[] { Items.bowl, kelp, raw, Blocks.brown_mushroom, Blocks.red_mushroom });
    }

    public static void addFishMeal(ItemStack raw, int meal) {
        addShapeless(new ItemStack(Core.materials, meal, MaterialsMeta.FISH_MEAL), new Object[] { raw });
    }

    public static ItemStack addUpgrade(int meta, Object[] input) {
        ItemStack result = new ItemStack(Core.upgrade, 1, meta);
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, input));
        return result;
    }
}
