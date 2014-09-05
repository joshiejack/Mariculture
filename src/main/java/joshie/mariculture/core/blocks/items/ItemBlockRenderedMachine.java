package joshie.mariculture.core.blocks.items;

import joshie.lib.util.Text;
import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.events.MaricultureEvents;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockRenderedMachine extends ItemBlockMariculture {
    public ItemBlockRenderedMachine(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        int meta = stack.getItemDamage();
        switch (meta) {
            case MachineRenderedMeta.AIR_PUMP:
                return "airpump";
            case MachineRenderedMeta.FISH_FEEDER:
                return "feeder";
            case MachineRenderedMeta.GEYSER:
                return "geyser";
            case MachineRenderedMeta.ANVIL:
                return "anvil";
            case MachineRenderedMeta.INGOT_CASTER:
                return "ingotCaster";
            case MachineRenderedMeta.BLOCK_CASTER:
                return "blockCaster";
            case MachineRenderedMeta.NUGGET_CASTER:
                return "nuggetCaster";
            case MachineRenderedMeta.AUTO_HAMMER:
                return "autohammer";
        }

        return MaricultureEvents.getItemName(field_150939_a, meta, "renderedBlocks");
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = field_150939_a.getUnlocalizedName().replace("tile.", "").replace("_", ".");
        String name = getName(stack).replaceAll("(.)([A-Z])", "$1$2").toLowerCase();
        return Text.localize(unlocalized.replace("mariculture.", MaricultureEvents.getMod(stack.getItem(), stack.getItemDamage(), "mariculture") + ".") + "." + name);
    }
}
