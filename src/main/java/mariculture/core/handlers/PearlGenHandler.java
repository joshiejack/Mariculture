package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mariculture.core.Core;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;

public class PearlGenHandler {
    private static ArrayList<GeneratedPearls> pearls = new ArrayList<GeneratedPearls>();
    public static HashMap<String, IIcon> textures = new HashMap();

    public static void addPearl(ItemStack item, int rarity) {
        addPearl(item, Core.pearlBlock, item.getItemDamage(), rarity);
    }

    public static void addPearl(ItemStack item, Block block, int meta, int rarity) {
        addPearl(item, rarity, 1, 1);
        String name = Item.itemRegistry.getNameForObject(item.getItem()) + item.getItemDamage();
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT && item.getItem() != Items.ender_pearl) {
            textures.put(name, block.getIcon(0, meta));
        }
    }

    public static IIcon getTexture(ItemStack stack) {
        String name = Item.itemRegistry.getNameForObject(stack.getItem()) + stack.getItemDamage();
        return textures.get(name);
    }

    private static float addPearl(ItemStack item, int rarity, int minCount, int maxCount) {
        for (GeneratedPearls loot : pearls) {
            if (loot.equals(item, minCount, maxCount)) return loot.itemWeight += rarity;
        }

        pearls.add(new GeneratedPearls(rarity, item, minCount, maxCount));
        return rarity;
    }

    public static ItemStack getRandomPearl(Random rand) {
        GeneratedPearls ret = (GeneratedPearls) WeightedRandom.getRandomItem(rand, pearls);
        if (ret != null) return ret.generateStack(rand);
        return null;
    }

    private static class GeneratedPearls extends WeightedRandomChestContent {
        private ItemStack itemStack;
        private int minCount = 1;
        private int maxCount = 1;

        private GeneratedPearls(int weight, ItemStack item, int min, int max) {
            super(item, weight, max, max);
            itemStack = item;
            minCount = min;
            maxCount = max;
        }

        private ItemStack generateStack(Random rand) {
            ItemStack ret = itemStack.copy();
            ret.stackSize = minCount + rand.nextInt(maxCount - minCount + 1);

            return ret;
        }

        private boolean equals(ItemStack item, int min, int max) {
            return min == minCount && max == maxCount && item.isItemEqual(itemStack);
        }

        public boolean equals(ItemStack item) {
            return item.isItemEqual(itemStack);
        }
    }
}
