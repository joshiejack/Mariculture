package mariculture.api.core.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IPVChargeable {
    public void fill(IFluidTank tank, ItemStack[] inventory, int special);
}
