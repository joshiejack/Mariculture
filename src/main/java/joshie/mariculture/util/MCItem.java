package joshie.mariculture.util;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

public interface MCItem<T extends Item> extends MCRegistry {
    /** Register this block **/
    default T register() {
        String name = getUnlocalizedName().replace("item.", "").replace("tile.", "");
        ResourceLocation resource = new ResourceLocation(MODID, name.replace(".", "_"));
        Item item =  (Item) this;
        item.setRegistryName(resource);
        GameRegistry.register(item); //Register the item
        if (isClient()) {
            registerModels(item, name);
        }

        return (T) this;
    }
}
