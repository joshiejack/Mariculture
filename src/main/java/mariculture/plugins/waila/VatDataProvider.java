package mariculture.plugins.waila;

import java.util.ArrayList;
import java.util.List;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.tile.TileVat;
import mariculture.core.util.MCTranslate;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class VatDataProvider implements IWailaDataProvider {
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
        if (accessor.getTileEntity() instanceof TileVat) {
            TileVat vat = (TileVat) accessor.getTileEntity();
            FluidStack tank1 = vat.getFluid((byte) 1);
            FluidStack tank2 = vat.getFluid((byte) 2);
            FluidStack tank3 = vat.getFluid((byte) 3);
            String fluid1 = FluidHelper.getFluidName(tank1) + " - " + FluidHelper.getFluidQty(new ArrayList(), tank1, 0).get(0);
            String fluid2 = FluidHelper.getFluidName(tank2) + " - " + FluidHelper.getFluidQty(new ArrayList(), tank2, 0).get(0);
            String fluid3 = FluidHelper.getFluidName(tank3) + " - " + FluidHelper.getFluidQty(new ArrayList(), tank3, 0).get(0);
            currenttip.add(MCTranslate.translate("input") + " 1: " + fluid1);
            currenttip.add(MCTranslate.translate("input") + " 2: " + fluid2);
            currenttip.add(MCTranslate.translate("output") + " : " + fluid3);
            currenttip.add(MCTranslate.translate("maxCapacity") + ": " + (vat.facing == ForgeDirection.UNKNOWN ? "" + vat.max_sml : "" + vat.max_lrg) + "mB");
            TileVat master = (TileVat) (vat.getMaster() != null ? vat.getMaster() : vat);
            if (master.timeNeeded > 0) {
                double percentage = (master.timeRemaining / master.timeNeeded) * 100;
                currenttip.add(MCTranslate.translate("progress") + ": " + percentage + "%");
            }
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}
