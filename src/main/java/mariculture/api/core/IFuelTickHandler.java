package mariculture.api.core;

import net.minecraft.tileentity.TileEntity;

public interface IFuelTickHandler {
	int onTemperatureIncrease(TileEntity crucible, int i);
}
