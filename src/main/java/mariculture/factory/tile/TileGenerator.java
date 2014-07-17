package mariculture.factory.tile;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;

public class TileGenerator extends TileEntity implements IEnergyConnection {
    private ArrayList<TileTurbineWater> turbines = new ArrayList();
    
    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }
    
    @Override
    public void updateEntity() {
        
    }
}
