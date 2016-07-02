package joshie.mariculture.modules.fishery.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.api.fishing.FishingTrait;
import joshie.mariculture.modules.fishery.Fishery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public final class FishingRodHelper {
    private static final Cache<NBTTagList, List<FishingTrait>> TRAITS_CACHE = CacheBuilder.newBuilder().build();
    private static final List<FishingTrait> EMPTY_LIST = new ArrayList<>();
    private static final String DURABILITY = "Durability";
    private static final String INVULNERABLE = "Invulnerable";
    private static final String TRAITS = "Traits";

    private static NBTTagCompound getTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        return stack.getTagCompound();
    }

    public static boolean isDamageable(ItemStack stack) {
        return !getTag(stack).hasKey(INVULNERABLE);
    }

    public static int getDurability(ItemStack stack) {
        return getTag(stack).getInteger(DURABILITY);
    }

    public static List<FishingTrait> getTraits(ItemStack stack) {
        try {
            return TRAITS_CACHE.get(getTag(stack).getTagList(TRAITS, 8), new Callable<List<FishingTrait>>() {
                @Override
                public List<FishingTrait> call() throws Exception {
                    List<FishingTrait> traits = new ArrayList<>();
                    NBTTagList list = getTag(stack).getTagList(TRAITS, 8);
                    for (int i = 0; i < list.tagCount(); i++) {
                        ResourceLocation location = new ResourceLocation(list.getStringTagAt(i));
                        FishingTrait trait = MaricultureAPI.fishing.getTraitFromResource(location);
                        if (trait != null) traits.add(trait);
                    }

                    return traits;
                }
            });
        } catch (Exception e) { return EMPTY_LIST; }
    }

    public static ItemStack build(int durability, boolean invulnerable, FishingTrait... traits) {
        ItemStack stack = new ItemStack(Fishery.FISHING_ROD);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger(DURABILITY, durability);
        if (invulnerable) stack.getTagCompound().setBoolean(INVULNERABLE, true);

        //Add the traits
        NBTTagList list = new NBTTagList();
        for (FishingTrait trait: traits) {
            list.appendTag(new NBTTagString(trait.getResource().toString()));
        }

        stack.getTagCompound().setTag(TRAITS, list);
        return stack;
    }
}
