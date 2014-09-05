package joshie.mariculture.core.blocks.items;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.events.MaricultureEvents;
import joshie.mariculture.core.lib.MachineRenderedMultiMeta;
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
            case MachineRenderedMultiMeta.COMPRESSOR_BASE:
                return "airCompressor";
            case MachineRenderedMultiMeta.COMPRESSOR_TOP:
                return "airCompressorPower";
            case MachineRenderedMultiMeta.PRESSURE_VESSEL:
                return "pressureVessel";
            case MachineRenderedMultiMeta.VAT:
                return "vat";
            case MachineRenderedMultiMeta.SIFTER:
                return "sifter";
        }

        return MaricultureEvents.getItemName(field_150939_a, meta, "doubleBlocks");
    }
}
