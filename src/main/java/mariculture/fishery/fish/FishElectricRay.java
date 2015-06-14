package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletRegen;
import static mariculture.core.lib.MCLib.dropletWater;

import java.util.ArrayList;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.util.Fluids;
import mariculture.lib.helpers.PowerHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyReceiver;

public class FishElectricRay extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 14;
    }

    @Override
    public int getTemperatureTolerance() {
        return 19;
    }

    @Override
    public Salinity getSalinityBase() {
        return BRACKISH;
    }

    @Override
    public int getSalinityTolerance() {
        return 1;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 20;
    }

    @Override
    public int getFertility() {
        return 4000;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 175;
    }
    
    @Override
    public Block getWater1() {
        return Fluids.getFluidBlock("ender");
    }

    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 5D);
        addProduct(dropletRegen, 10D);
        addProduct(dropletFlux, 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 6.675D;
    }

    @Override
    public int getFishMealSize() {
        return 5;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ));
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(512) == 0) {
            world.addWeatherEffect(new EntityLightningBolt(world, x + world.rand.nextInt(5) - world.rand.nextInt(10), y, z + world.rand.nextInt(5) - world.rand.nextInt(10)));
        }
        
        for (ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS) {
            IEnergyReceiver handler = PowerHelper.isEnergyHandler(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if (handler.canConnectEnergy(ForgeDirection.DOWN)) {
                handler.receiveEnergy(ForgeDirection.UP, 200, false);
            }
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 8D;
    }
}
