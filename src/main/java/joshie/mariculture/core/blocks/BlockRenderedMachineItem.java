package joshie.mariculture.core.blocks;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockRenderedMachineItem extends ItemBlockMariculture {
    public BlockRenderedMachineItem(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case MachineRenderedMeta.AIR_PUMP:
                return "airpump";
            case MachineRenderedMeta.FISH_FEEDER:
                return "feeder";
            case MachineRenderedMeta.FLUDD_STAND:
                return "fludd";
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
            case MachineRenderedMeta.ROTOR_COPPER:
                return "rotorCopper";
            case MachineRenderedMeta.ROTOR_ALUMINUM:
                return "rotorAluminum";
            case MachineRenderedMeta.ROTOR_TITANIUM:
                return "rotorTitanium";
            default:
                return "renderedBlocks";
        }
    }
}
