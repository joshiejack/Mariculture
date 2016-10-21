package joshie.mariculture.modules.diving.tile;

import joshie.mariculture.api.diving.AirPumpTicker;
import joshie.mariculture.core.helpers.EntityHelper;
import joshie.mariculture.core.util.capabilities.MCEnergyStorage;
import joshie.mariculture.core.util.interfaces.Faceable;
import joshie.mariculture.core.util.tile.TileMC;
import joshie.mariculture.modules.diving.Diving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileAirPump extends TileMC implements Faceable, ITickable {
    private final MCEnergyStorage storage = new MCEnergyStorage(1000, 100, 10);
    public static final List<AirPumpTicker> tickers = new ArrayList<>();
    private EnumFacing facing;
    static {
        tickers.add((world, pos, energy) -> {
            int amount = 0;
            for (EntityPlayer player: EntityHelper.getEntities(EntityPlayer.class, world, pos, 64, 128)) {
                if (player.getAir() < 300) {
                    if (EntityHelper.hasArmor(player, EntityEquipmentSlot.HEAD, Diving.HELMET) &&
                            EntityHelper.hasArmor(player, EntityEquipmentSlot.CHEST, Diving.AIR_SUPPLY)) {
                        if (energy - 10 >= 0) {
                            player.setAir(300);
                            energy -= 10;
                            amount += 10;
                        }
                    }
                }
            }

            return amount;
        });
    }

    public boolean onActivated(EntityPlayer player) {
        return storage.receiveEnergy(25, false) > 0;
    }

    @Override
    public void update() {
        if (worldObj.isRemote) return;
        if (storage.getEnergyStored() > 0 && worldObj.getTotalWorldTime() %20 == 0) {
            for (AirPumpTicker ticker: tickers) {
                if (worldObj.getTotalWorldTime() %ticker.getTicks() == 0) {
                    int consume = ticker.onAirPumpTick(worldObj, pos, storage.getEnergyStored());
                    if (consume >= 0) storage.extractEnergy(consume, false);
                }
            }
        }
    }

    @Override
    public EnumFacing getFacing() {
        return facing;
    }

    @Override
    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == ENERGY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (facing != null && capability == ENERGY)
            return (T) storage;
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)  {
        super.readFromNBT(compound);
        storage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        storage.writeToNBT(compound);
        return super.writeToNBT(compound);
    }
}
