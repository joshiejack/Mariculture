package joshie.mariculture.modules.fishery.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import joshie.mariculture.api.fishing.FishingTrait;
import joshie.mariculture.api.fishing.rod.*;
import joshie.mariculture.modules.fishery.Fishery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static joshie.mariculture.api.fishing.rod.FishingHook.HOOKS;
import static joshie.mariculture.api.fishing.rod.FishingPole.POLES;
import static joshie.mariculture.api.fishing.rod.FishingReel.REELS;
import static joshie.mariculture.api.fishing.rod.FishingRod.NULL_FISHING_ROD;
import static joshie.mariculture.api.fishing.rod.FishingString.STRINGS;

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
            return TRAITS_CACHE.get(getTag(stack).getTagList(TRAITS, 8), () -> {
                List<FishingTrait> traits = new ArrayList<>();
                NBTTagList list = getTag(stack).getTagList(TRAITS, 8);
                for (int i = 0; i < list.tagCount(); i++) {
                    ResourceLocation location = new ResourceLocation(list.getStringTagAt(i));
                    FishingTrait trait = FishingTrait.TRAIT_REGISTRY.get(location);
                    if (trait != null) traits.add(trait);
                }

                return traits;
            });
        } catch (Exception e) { return EMPTY_LIST; }
    }

    @SuppressWarnings("ConstantConditions")
    public static ItemStack build(FishingPole pole, FishingReel reel, FishingString string, FishingHook hook) {
        FishingRod rod = new FishingRod(pole, reel, string, hook);
        ItemStack stack = new ItemStack(Fishery.FISHING_ROD);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger(DURABILITY, rod.getDurability());
        if (rod.isInvulnerable()) stack.getTagCompound().setBoolean(INVULNERABLE, true);
        stack.getTagCompound().setString("Pole", pole.toString());
        stack.getTagCompound().setString("Reel", reel.toString());
        stack.getTagCompound().setString("String", string.toString());
        stack.getTagCompound().setString("Hook", hook.toString());

        //Add the traits
        NBTTagList list = new NBTTagList();
        addFishingTraits(list, pole, reel, string, hook);

        stack.getTagCompound().setTag(TRAITS, list);
        return stack;
    }

    private static void addFishingTraits(NBTTagList list, FishingComponent... components) {
        for (FishingComponent component: components) {
            for (FishingTrait trait : component.getTraits()) {
                list.appendTag(new NBTTagString(trait.getResource().toString()));
            }
        }
    }

    @SuppressWarnings("ConstantCondtions")
    public static FishingRod getFishingRod(ItemStack stack) {
        if (!stack.getTagCompound().hasKey("Pole")) return NULL_FISHING_ROD;
        FishingPole pole = POLES.get(new ResourceLocation(stack.getTagCompound().getString("Pole")));
        FishingReel reel = REELS.get(new ResourceLocation(stack.getTagCompound().getString("Reel")));
        FishingString string = STRINGS.get(new ResourceLocation(stack.getTagCompound().getString("String")));
        FishingHook hook = HOOKS.get(new ResourceLocation(stack.getTagCompound().getString("Hook")));
        return new FishingRod(pole, reel, string, hook);
    }
}
