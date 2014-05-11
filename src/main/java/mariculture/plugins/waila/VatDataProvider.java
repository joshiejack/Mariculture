package mariculture.plugins.waila;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mariculture.core.blocks.BlockDouble;
import mariculture.core.blocks.TileVat;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.Text;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class VatDataProvider implements IWailaDataProvider {
	public static void register(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new VatDataProvider(), BlockDouble.class);
	}
	
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
		if(accessor.getTileEntity() instanceof TileVat) {
			TileVat vat = (TileVat) accessor.getTileEntity();
			FluidStack tank1 = vat.getFluid((byte)1);
			FluidStack tank2 = vat.getFluid((byte)2);
			FluidStack tank3 = vat.getFluid((byte)3);
			String fluid1 = StringHelper.getFluidName(tank1) + " - " + StringHelper.getFluidQty(new ArrayList(), tank1, 0).get(0);
			String fluid2 = StringHelper.getFluidName(tank2) + " - " + StringHelper.getFluidQty(new ArrayList(), tank2, 0).get(0);
			String fluid3 = StringHelper.getFluidName(tank3) + " - " + StringHelper.getFluidQty(new ArrayList(), tank3, 0).get(0);
			currenttip.add(Text.translate("input") + " 1: " + fluid1);
			currenttip.add(Text.translate("input") + " 2: " + fluid2);
			currenttip.add(Text.translate("output") + " : " + fluid3);
			currenttip.add(Text.translate("maxCapacity") + ": " + (vat.facing == ForgeDirection.UNKNOWN? "" + vat.max_sml: "" + vat.max_sml) + "mB"); 
		}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack stack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}
}
