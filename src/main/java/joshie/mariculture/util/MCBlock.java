package joshie.mariculture.util;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

public interface MCBlock<T extends Block> extends MCRegistry {
    /** Register this block **/
    default T register() {
        String name = getUnlocalizedName().replace("tile.", "");
        ResourceLocation resource = new ResourceLocation(MODID, name.replace(".", "_"));
        Block block = (Block) this;
        block.setRegistryName(resource);
        GameRegistry.register(block);
        new ItemBlockMC(this).register();
        return (T) this;
    }
}
