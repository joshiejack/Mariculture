package mariculture.core.blocks;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

public class BlockLimestoneSlabItem extends ItemSlab {
	private static Optional<BlockHalfSlab> singleSlab = Optional.absent();
	private static Optional<BlockHalfSlab> doubleSlab = Optional.absent();

	public static void setSlabs(final BlockHalfSlab singleSlab, final BlockHalfSlab doubleSlab) {
		BlockLimestoneSlabItem.singleSlab = Optional.of(singleSlab);
		BlockLimestoneSlabItem.doubleSlab = Optional.of(doubleSlab);
	}

	public BlockLimestoneSlabItem(final int id) {
		super(id, singleSlab.get(), doubleSlab.get(), id == doubleSlab.get().blockID);
	}

	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return singleSlab.get().getFullSlabName(stack.getItemDamage());
	}
}
