package mariculture.core.blocks.items;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.LimestoneMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockLimestone extends ItemBlockMariculture {
    public ItemBlockLimestone(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        if (meta < LimestoneMeta.PILLAR_1) return super.getUnlocalizedName(stack);
        else if (meta < LimestoneMeta.PEDESTAL_1) return "tile.limestone.pillar";
        else return "tile.limestone.pedestal";
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case LimestoneMeta.RAW:
                return "raw";
            case LimestoneMeta.SMOOTH:
                return "smooth";
            case LimestoneMeta.BRICK:
                return "brick";
            case LimestoneMeta.SMALL_BRICK:
                return "brickSmall";
            case LimestoneMeta.THIN_BRICK:
                return "brickThin";
            case LimestoneMeta.BORDERED:
                return "bordered";
            case LimestoneMeta.CHISELED:
                return "chiseled";
            case LimestoneMeta.PILLAR_1:
                return "pillar1";
            case LimestoneMeta.PILLAR_2:
                return "pillar2";
            case LimestoneMeta.PILLAR_3:
                return "pillar3";
            case LimestoneMeta.PEDESTAL_1:
                return "pedestal1";
            case LimestoneMeta.PEDESTAL_2:
                return "pedestal2";
            case LimestoneMeta.PEDESTAL_3:
                return "pedestal3";
            case LimestoneMeta.PEDESTAL_4:
                return "pedestal4";
            case LimestoneMeta.PEDESTAL_5:
                return "pedestal5";
            case LimestoneMeta.PEDESTAL_6:
                return "pedestal6";
            default:
                return "raw";
        }
    }
}
