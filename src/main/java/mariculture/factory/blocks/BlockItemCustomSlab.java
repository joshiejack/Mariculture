package mariculture.factory.blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.google.common.base.Optional;

public class BlockItemCustomSlab extends BlockItemCustomSlabBase {
	private static Optional<BlockHalfSlab> singleSlab = Optional.absent();
	private static Optional<BlockHalfSlab> doubleSlab = Optional.absent();

	public static void setSlabs(BlockHalfSlab singleSlab, BlockHalfSlab doubleSlab) {
		BlockItemCustomSlab.singleSlab = Optional.of(singleSlab);
		BlockItemCustomSlab.doubleSlab = Optional.of(doubleSlab);
	}

	public BlockItemCustomSlab(int id) {
		super(id, singleSlab.get(), doubleSlab.get(), id == doubleSlab.get().blockID);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.hasKey("Name")) {
				return stack.stackTagCompound.getString("Name");
			}
		}

		return StatCollector.translateToLocal(stack.getUnlocalizedName());
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		BlockItemCustom.addTextureInfo(stack, player, list, bool);
	}
}
