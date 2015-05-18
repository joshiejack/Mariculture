package mariculture.plugins.enchiridion;

import java.util.List;

import joshie.enchiridion.api.EnchiridionAPI;
import joshie.enchiridion.api.IRecipeHandler;
import joshie.enchiridion.designer.recipe.RecipeHandlerBase;
import joshie.enchiridion.designer.recipe.WrappedStack;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeVat;
import mariculture.core.helpers.FluidHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipeHandlerVat extends RecipeHandlerBase {
    private RecipeVat recipe;

    public RecipeHandlerVat() {}
    public RecipeHandlerVat(RecipeVat recipe) {
        this.recipe = recipe;
        stackList.add(new WrappedStack(recipe.outputItem, 112D, 20D, 1.2F));
        stackList.add(new WrappedStack(recipe.inputItem, 17D, 20D, 1.2F));
        if (recipe.inputItem != null) {
            if (recipe.inputItem instanceof String) {
                addToUnique((String)recipe.inputItem);
            } else {
                addToUnique(Item.itemRegistry.getNameForObject(((ItemStack) recipe.inputItem).getItem()));
                addToUnique(((ItemStack) recipe.inputItem).getItemDamage());
            }
        }
        
        if (recipe.inputFluid1 != null) {
            addToUnique(recipe.inputFluid1.getFluid().getUnlocalizedName());
        }
        
        if (recipe.inputFluid2 != null) {
            addToUnique(recipe.inputFluid2.getFluid().getUnlocalizedName());
        }
    }

    @Override
    public void addRecipes(ItemStack output, List<IRecipeHandler> list) {       
        for (RecipeVat recipe : MaricultureHandlers.vat.getRecipes()) {
            if (recipe == null) continue;
            ItemStack stack = recipe.outputItem;
            if (stack != null) {
                if (stack.isItemEqual(output)) {
                    list.add(new RecipeHandlerVat(recipe));
                }
            }
            
            FluidStack out = recipe.outputFluid;
            if (out == null) continue;
            else {
                FluidStack emptied = FluidContainerRegistry.getFluidForFilledItem(output);
                if (emptied != null && emptied.getFluid() == out.getFluid()) {
                    list.add(new RecipeHandlerVat(recipe));
                }
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "MaricultureVat";
    }

    @Override
    public double getHeight(double width) {
        return width / 2.5D;
    }

    @Override
    public double getWidth(double width) {
        return width;
    }

    @Override
    public float getSize(double width) {
        return (float) (width / 110D);
    }
    
    @Override
    public void addTooltip(List list) {
        super.addTooltip(list);
        
        if (list.size() == 0) {
            if (recipe.inputFluid1 != null && recipe.inputFluid2 == null) {
                if (EnchiridionAPI.draw.isOverArea(5D, 5D, 16, 14, 2.44F)) {
                    list.add(FluidHelper.getFluidName(recipe.inputFluid1));
                    FluidHelper.getFluidQty(list, recipe.inputFluid1, -1);
                }
            } else if (recipe.inputFluid1 != null && recipe.inputFluid2 != null) {
                if(EnchiridionAPI.draw.isOverArea(5D, 5D, 9, 14, 2.44F)) {
                    list.add(FluidHelper.getFluidName(recipe.inputFluid1));
                    FluidHelper.getFluidQty(list, recipe.inputFluid1, -1);
                } else if (EnchiridionAPI.draw.isOverArea(31D, 5D, 8, 14, 2.44F)) {
                    list.add(FluidHelper.getFluidName(recipe.inputFluid2));
                    FluidHelper.getFluidQty(list, recipe.inputFluid2, -1);
                }
            }
            
            if (recipe.outputFluid != null) {
                if(EnchiridionAPI.draw.isOverArea(98D, 5D, 16, 14, 2.44F)) {
                    list.add(FluidHelper.getFluidName(recipe.outputFluid));
                    FluidHelper.getFluidQty(list, recipe.outputFluid, -1);
                }
            }
        }
    }

    @Override
    protected void drawBackground() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        if (recipe.inputFluid1 != null && recipe.inputFluid2 == null) {
            EnchiridionAPI.draw.drawIcon(recipe.inputFluid1.getFluid().getIcon(), 5D, 5D, 16, 14, 2.44F);
        } else if (recipe.inputFluid1 != null && recipe.inputFluid2 != null) {
            EnchiridionAPI.draw.drawIcon(recipe.inputFluid1.getFluid().getIcon(), 5D, 5D, 9, 14, 2.44F);
            EnchiridionAPI.draw.drawIcon(recipe.inputFluid2.getFluid().getIcon(), 31D, 5D, 8, 14, 2.44F);
        }
        
        if (recipe.outputFluid != null) {
            EnchiridionAPI.draw.drawIcon(recipe.outputFluid.getFluid().getIcon(), 98D, 5D, 16, 14, 2.44F);
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        EnchiridionAPI.draw.drawTexturedRect(0D, 0D, 58, 0, 58, 62, 0.75F);
        EnchiridionAPI.draw.drawTexturedRect(95D, 0D, 58, 0, 58, 62, 0.75F);
        EnchiridionAPI.draw.drawTexturedRect(64D, 30D, 1, 63, 20, 14, 1F);
        EnchiridionAPI.draw.drawText("" + recipe.processTime + "s", 65D, 10D, 0xFF333333, 1F);
    }
}
