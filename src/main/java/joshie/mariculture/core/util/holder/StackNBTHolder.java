package joshie.mariculture.core.util.holder;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StackNBTHolder extends StackHolder {
    private NBTTagCompound tag;

    private StackNBTHolder(ItemStack stack) {
        super(stack.getItem(), stack.getItemDamage());
        this.tag = stack.getTagCompound();
    }

    public static StackNBTHolder of(ItemStack stack) {
        return new StackNBTHolder(stack);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StackNBTHolder that = (StackNBTHolder) o;
        return tag != null ? tag.equals(that.tag) : that.tag == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
