package joshie.mariculture.core.util.item;

import joshie.mariculture.core.util.MCRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static joshie.mariculture.core.lib.MaricultureInfo.MODID;

public interface MCItem<T extends Item> extends MCRegistry {
    /** Register this block **/
    default T register(String name) {
        Item item =  (Item) this;
        item.setUnlocalizedName(name.replace("_", "."));
        item.setRegistryName(new ResourceLocation(MODID, name));
        GameRegistry.register(item);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            registerModels(item);
        }

        return (T) this;
    }

    /** Return a stack version of this **/
    default ItemStack getStack(int amount) {
        return new ItemStack((T) this, 1, amount);
    }

    /** Return this without any data **/
    default ItemStack getStack() {
        return new ItemStack((T) this);
    }
}
