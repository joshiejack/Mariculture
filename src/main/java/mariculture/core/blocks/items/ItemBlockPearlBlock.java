package mariculture.core.blocks.items;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.MCTranslate;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockPearlBlock extends ItemBlockMariculture {
    public ItemBlockPearlBlock(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        return PearlColor.get(stack.getItemDamage());
    }

    public String getColor(ItemStack stack) {
        String translate = "pearl.color." + PearlColor.get(stack.getItemDamage());
        String str = MCTranslate.translate(translate + "." + field_150939_a.getUnlocalizedName());        
        if(str.equals("mariculture." + translate + "." + field_150939_a.getUnlocalizedName())) {
            return MCTranslate.translate(translate);
        } else return str;
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String format = MCTranslate.translate("pearl.format");
        format = format.replace("%C", getColor(stack));
        format = format.replace("%P", MCTranslate.translate("pearl"));
        String unlocalized = field_150939_a.getUnlocalizedName();
        if(unlocalized.contains("block")) {
            return format.replace("%B", MCTranslate.translate("pearl.block"));
        } else return format.replace("%B", MCTranslate.translate("pearl.brick"));
    }
}