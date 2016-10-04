package joshie.mariculture.modules.aquaculture;

import joshie.mariculture.core.util.holder.StackHolder;
import joshie.mariculture.core.util.annotation.MCApiImpl;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@MCApiImpl("aquaculture")
public class AquacultureAPI implements joshie.mariculture.api.aquaculture.Aquaculture {
    public static final AquacultureAPI INSTANCE = new AquacultureAPI();

    private final Set<StackHolder> SANDS = new HashSet<>();
    private final Map<StackHolder, String> TEXTURES = new HashMap<>();

    public AquacultureAPI() {
        registerSand(new ItemStack(Blocks.SAND, 1, 0), "minecraft:blocks/sand");
        registerSand(new ItemStack(Blocks.SAND, 1, 1), "minecraft:blocks/red_sand");
    }

    public boolean isSand(ItemStack stack) {
        return SANDS.contains(StackHolder.of(stack));
    }

    public String getTexture(ItemStack stack) {
        return TEXTURES.get(StackHolder.of(stack));
    }

    @Override
    public void registerSand(ItemStack stack, String texture) {
        StackHolder holder = StackHolder.of(stack);
        SANDS.add(holder);
        TEXTURES.put(holder, texture);
    }
}
