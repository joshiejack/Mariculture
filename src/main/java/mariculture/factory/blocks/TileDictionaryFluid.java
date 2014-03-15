package mariculture.factory.blocks;

import java.util.HashMap;

import mariculture.core.blocks.base.TileStorageTank;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class TileDictionaryFluid extends TileStorageTank {
	public static final HashMap<FluidStack, FluidStack> recipes = new HashMap();
	public TileDictionaryFluid() {
		inventory = new ItemStack[3];
	}
}
