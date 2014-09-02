package joshie.mariculture.plugins;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginHelper {
    protected String name;

    public PluginHelper(String str) {
        this.name = str;
    }

    public String getName() {
        return name;
    }

    protected Block getBlock(String item) {
        return GameRegistry.findBlock(name, item);
    }

    protected Item getItem(String item) {
        return GameRegistry.findItem(name, item);
    }

    public ItemStack getStack(String item, int size) {
        return GameRegistry.findItemStack(name, item, size);
    }

    public ItemStack getItem(String str, int meta) {
        return new ItemStack(getItem(str), 1, meta);
    }

    private ItemStack getWildItem(String str) {
        return getItem(str, OreDictionary.WILDCARD_VALUE);
    }
}
