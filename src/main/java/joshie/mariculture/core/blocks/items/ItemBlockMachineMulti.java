package joshie.mariculture.core.blocks.items;

import joshie.lib.util.Text;
import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.events.MaricultureEvents;
import joshie.mariculture.core.lib.MachineMultiMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMachineMulti extends ItemBlockMariculture {
    public ItemBlockMachineMulti(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        int meta = stack.getItemDamage();
        switch (meta) {
            case MachineMultiMeta.CRUCIBLE:
                return "crucible";
        }
        
        return MaricultureEvents.getItemName(field_150939_a, meta, "machinesDouble");
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = field_150939_a.getUnlocalizedName().replace("tile.", "").replace("_", ".");
        String name = getName(stack).replaceAll("(.)([A-Z])", "$1$2").toLowerCase();
        return Text.localize(unlocalized.replace("mariculture.", MaricultureEvents.getMod(stack.getItem(), stack.getItemDamage(), "mariculture") + ".") + "." + name);
    }
}
