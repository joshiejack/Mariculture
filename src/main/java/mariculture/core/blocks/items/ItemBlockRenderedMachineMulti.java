package mariculture.core.blocks.items;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.events.MaricultureEvents;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.lib.util.Text;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockRenderedMachineMulti extends ItemBlockMariculture {
    public ItemBlockRenderedMachineMulti(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        int meta = stack.getItemDamage();
        switch (meta) {
            case MachineRenderedMultiMeta.VAT:
                return "vat";
            case MachineRenderedMultiMeta.SIFTER:
                return "sifter";
        }

        return MaricultureEvents.getItemName(field_150939_a, meta, "renderedDoubleBlocks");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = field_150939_a.getUnlocalizedName().replace("tile.", "").replace("_", ".");
        String name = getName(stack).replaceAll("(.)([A-Z])", "$1$2").toLowerCase();
        return Text.localize(unlocalized.replace("mariculture.", MaricultureEvents.getMod(stack.getItem(), stack.getItemDamage(), "mariculture") + ".") + "." + name);
    }
}
