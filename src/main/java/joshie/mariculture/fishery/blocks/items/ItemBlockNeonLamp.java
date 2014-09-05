package joshie.mariculture.fishery.blocks.items;

import joshie.lib.util.Text;
import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.PearlColor;
import joshie.mariculture.core.util.MCTranslate;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockNeonLamp extends ItemBlockMariculture {
    public ItemBlockNeonLamp(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case PearlColor.WHITE:
                return "white";
            case PearlColor.GREEN:
                return "green";
            case PearlColor.YELLOW:
                return "yellow";
            case PearlColor.ORANGE:
                return "orange";
            case PearlColor.RED:
                return "red";
            case PearlColor.GOLD:
                return "gold";
            case PearlColor.BROWN:
                return "brown";
            case PearlColor.PURPLE:
                return "purple";
            case PearlColor.BLUE:
                return "blue";
            case PearlColor.BLACK:
                return "black";
            case PearlColor.PINK:
                return "pink";
            case PearlColor.SILVER:
                return "silver";
            default:
                return "lamp";
        }
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String format = MCTranslate.translate("lamps.format");
        format = format.replace("%C", MCTranslate.translate("pearl.color." + PearlColor.get(stack.getItemDamage())));
        format = format.replace("%N", MCTranslate.translate("lamps.neon"));
        return format.replace("%L", MCTranslate.translate("lamps.lamp"));
    }
}