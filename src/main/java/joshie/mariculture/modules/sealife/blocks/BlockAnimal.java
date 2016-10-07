package joshie.mariculture.modules.sealife.blocks;

import joshie.mariculture.core.lib.CreativeOrder;
import joshie.mariculture.core.util.block.BlockAquatic;
import joshie.mariculture.modules.sealife.blocks.BlockAnimal.Animal;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

import static joshie.mariculture.MClientProxy.NO_WATER;

public class BlockAnimal extends BlockAquatic<Animal, BlockAnimal> {
    public BlockAnimal() {
        super(Animal.class);
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return CreativeOrder.STARFISH;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(Item item) {
        ModelLoader.setCustomStateMapper(this, NO_WATER);

        for (Animal animal: values) {
            ModelLoader.setCustomModelResourceLocation(item, animal.ordinal(), new ModelResourceLocation(getRegistryName(), animal.getClass().getSimpleName() + "=" + animal.getName()));
        }
    }

    public enum Animal implements IStringSerializable {
        STARFISH;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
