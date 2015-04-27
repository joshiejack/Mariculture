package mariculture.plugins.minetweaker.util;

import java.util.Collection;
import java.util.Iterator;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class CollectionRemoveAction implements IUndoableAction {
    protected final String description;
    protected final Collection list;

    protected final FluidStack fluid;
    protected final ItemStack stack;
    protected Object recipe;

    public CollectionRemoveAction(String description, Collection list, ItemStack stack, FluidStack fluid) {
        this.list = list;
        this.stack = stack;
        this.description = description;
        this.fluid = fluid;
    }

    public CollectionRemoveAction(String description, Collection list, ItemStack stack) {
        this(description, list, stack, null);
    }

    public CollectionRemoveAction(String description, Collection list, FluidStack fluid) {
        this(description, list, null, fluid);
    }

    public CollectionRemoveAction(Collection list, ItemStack stack) {
        this(null, list, stack);
    }

    public CollectionRemoveAction(Collection list, FluidStack stack) {
        this(null, list, stack);
    }

    public CollectionRemoveAction(String description, Collection list) {
        this(description, list, null, null);
    }

    @Override
    public void apply() {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o == null) continue;
            if (matches(o)) {
                recipe = o;
                it.remove();
            }
        }
    }
    
    public abstract boolean matches(Object object);

    @Override
    public boolean canUndo() {
        return list != null;
    }

    @Override
    public void undo() {
        list.add(recipe);
    }

    public String getRecipeInfo() {
        return stack != null? stack.getDisplayName(): "Unknown Recipe";
    }

    @Override
    public String describe() {
        if (recipe instanceof ItemStack)
            return "Removing " + description + " Recipe for :" + ((ItemStack) recipe).getDisplayName();
        else if (recipe instanceof FluidStack)
            return "Removing " + description + " Recipe for :" + ((FluidStack) recipe).getFluid().getLocalizedName();
        else return "Removing " + description + " Recipe for :" + getRecipeInfo();
    }

    @Override
    public String describeUndo() {
        if (recipe instanceof ItemStack)
            return "Restoring " + description + " Recipe for :" + ((ItemStack) recipe).getDisplayName();
        else if (recipe instanceof FluidStack)
            return "Restoring " + description + " Recipe for :" + ((FluidStack) recipe).getFluid().getLocalizedName();
        else return "Restoring " + description + " Recipe for :" + getRecipeInfo();
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
