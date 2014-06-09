package mariculture.core.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.MachineRenderedMeta;
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
            case MachineRenderedMeta.TURBINE_WATER:
                return "turbine";
            case MachineRenderedMeta.FLUDD_STAND:
                return "fludd";
            case MachineRenderedMeta.TURBINE_GAS:
                return "turbineGas";
            case MachineRenderedMeta.GEYSER:
                return "geyser";
            case MachineRenderedMeta.TURBINE_HAND:
                return "turbineHand";
            case MachineRenderedMeta.ANVIL:
                return "anvil";
            case MachineRenderedMeta.INGOT_CASTER:
                return "ingotCaster";
            case MachineRenderedMeta.BLOCK_CASTER:
                return "blockCaster";
            case MachineRenderedMeta.NUGGET_CASTER:
                return "nuggetCaster";
            default:
                return "renderedBlocks";
        }
    }
}
