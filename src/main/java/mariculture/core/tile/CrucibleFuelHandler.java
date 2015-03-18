package mariculture.core.tile;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.IFuelTickHandler;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleFuelHandler {
	String burning;
    TileCrucible crucible;
    int usedHeat;
    int tick;
    FuelInfo info;
    IFuelTickHandler tickHandler;
    
    public CrucibleFuelHandler() {}
    public CrucibleFuelHandler(TileCrucible crucible) {
        this.crucible = crucible;
    }

    void read(NBTTagCompound nbt) {
        if (nbt.getBoolean("HasHandler")) {
            info = new FuelInfo();
            info.read(nbt);
            burning = nbt.getString("BurningObject");
            tickHandler = MaricultureHandlers.crucible.getFuelTickHandler(this.burning);
            if (tickHandler == null) {
            	tickHandler = dummyTickHandler;
            }
        }
    }

    void write(NBTTagCompound nbt) {
        if (info != null) {
            nbt.setBoolean("HasHandler", true);
            info.write(nbt);
            nbt.setString("BurningObject", burning);
        }
    }
    
    /** Do fuck all tick handler **/
    static IFuelTickHandler dummyTickHandler = new IFuelTickHandler() {
    	@Override
    	public int onTemperatureIncrease(TileEntity tile, int original) {
    		return original;
    	}
    };

    void set(Object burning, FuelInfo info) {
        this.info = info;
        tick = 0;
        usedHeat = 0;
        
        if (burning instanceof String) {
        	this.burning = (String) burning;
        } else if (burning instanceof ItemStack) {
        	this.burning = OreDicHelper.convert(burning);
        } else if (burning instanceof FluidStack) {
        	this.burning = getName((FluidStack)burning);
        } else this.burning = null;
        
        this.tickHandler = MaricultureHandlers.crucible.getFuelTickHandler(this.burning);
        if (this.tickHandler == null) {
        	this.tickHandler = dummyTickHandler;
        }
    }
    
    public String getName(FluidStack fluid) {
        if (fluid.getFluid() == null) return "null";
        return fluid.getFluid().getName();
    }

    private int getMaxTempPer(int purity) {
        return info.maxTempPer * (purity + 1);
    }

    int tick(int temp, int purity) {
        int realUsed = usedHeat * 2000 / crucible.MAX_TEMP;
        int realTemp = temp * 2000 / crucible.MAX_TEMP;

        tick++;

        if (realUsed < getMaxTempPer(purity) && realTemp < info.maxTemp) {
        	int jump = tickHandler.onTemperatureIncrease(crucible, crucible.heat / 3 + 1);
            temp += jump;
            usedHeat += jump;
        }

        if (realUsed >= getMaxTempPer(purity) || tick >= info.ticksPer) {
            info = null;
            if (crucible.canFuel()) {
                crucible.fuelHandler.set(crucible.getNext(), crucible.getInfo());
            } else {
                crucible.fuelHandler.set(null, null);
            }
        }

        return temp;
    }
}
