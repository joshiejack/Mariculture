package joshie.mariculture.core.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StackHolder {
    private final Item item;
    private final int damage;

    private StackHolder(Item item, int damage) {
        this.item = item;
        this.damage = damage;
    }

    public static StackHolder of(ItemStack stack) {
        return new StackHolder(stack.getItem(), stack.getItemDamage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StackHolder that = (StackHolder) o;
        if (damage != that.damage) return false;
        return item != null ? item.equals(that.item) : that.item == null;

    }

    @Override
    public int hashCode() {
        int result = item != null ? item.hashCode() : 0;
        result = 31 * result + damage;
        return result;
    }
}
