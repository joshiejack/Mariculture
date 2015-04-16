package mariculture.plugins.waila;

import java.util.ArrayList;
import java.util.List;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.tile.TileTankBlock;
import mariculture.core.tile.TileVoidBottle;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CopperTankDataProvider implements IWailaDataProvider {
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getTileEntity() instanceof TileTankBlock && !(accessor.getTileEntity() instanceof TileVoidBottle)) {
            TileTankBlock tank = (TileTankBlock) accessor.getTileEntity();
            FluidStack tank1 = tank.getFluid();
            String fluid1 = FluidHelper.getFluidName(tank1) + " - " + FluidHelper.getFluidQty(new ArrayList(), tank1, 0).get(0);
            currenttip.add(fluid1 + " / " + tank.getCapacity() + "mB");
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
