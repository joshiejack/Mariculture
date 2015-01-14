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

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String translate = MCTranslate.translate("pearl.color." + PearlColor.get(stack.getItemDamage()) + "." + field_150939_a.getUnlocalizedName());
        if(!translate.equals("mariculture.pearl.color." + PearlColor.get(stack.getItemDamage()) + "." + field_150939_a.getUnlocalizedName())) {
            return translate;
        }
        
        String format = MCTranslate.translate("pearl.format");
        format = format.replace("%C", MCTranslate.translate("pearl.color." + PearlColor.get(stack.getItemDamage())));
        format = format.replace("%P", MCTranslate.translate("pearl"));
        String unlocalized = field_150939_a.getUnlocalizedName();
        if(unlocalized.contains("block")) {
            return format.replace("%B", MCTranslate.translate("pearl.block"));
        } else return format.replace("%B", MCTranslate.translate("pearl.brick"));
    }
}