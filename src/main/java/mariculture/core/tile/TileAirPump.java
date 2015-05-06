package mariculture.core.tile;

import java.util.List;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.config.Machines.Client;
import mariculture.core.config.Machines.Ticks;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketAirPump;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileStorageTank;
import mariculture.core.util.Fluids;
import mariculture.core.util.IFaceable;
import mariculture.core.util.Tank;
import mariculture.diving.Diving;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;

public class TileAirPump extends TileStorageTank implements IEnergyReceiver, IFaceable {
    protected BlockTransferHelper helper;
    protected EnergyStorage storage = new EnergyStorage(2000);
    public ForgeDirection orientation = ForgeDirection.WEST;
    public boolean isAnimating;
    private double wheelAngle = 0;

    public TileAirPump() {
        tank = new Tank(16000);
        inventory = new ItemStack[1];
    }

    private int tick;

    public boolean onTick(int i) {
        return tick % i == 0;
    }

    public void supplyWithAir(int value, double x, double y, double z) {
        List playerList = worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getTileEntity(xCoord, yCoord, zCoord).getBlockType().getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord).expand(x, y, z));
        if (!playerList.isEmpty()) {
            for (int i = 0; i < playerList.size(); i++) {
                EntityPlayer player = (EntityPlayer) playerList.get(i);
                if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, Diving.divingTop) && PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.divingHelmet)) {
                    if (player.isInsideOfMaterial(Material.water)) {
                        if (value == 300) {
                            player.setAir(300);
                        } else {
                            player.setAir(player.getAir() + 35);
                        }
                    }
                }
            }
        }
    }

    public boolean suckUpGas() {
        for (int x = xCoord - 6; x < xCoord + 7; x++) {
            for (int z = zCoord - 6; z < zCoord + 7; z++) {
                for (int y = yCoord; y < yCoord + 10; y++) {
                    if (isNaturalGas(x, y, z)) {
                        if (fill(ForgeDirection.UP, Fluids.getFluidStack("natural_gas", 1000), false) >= 1000) {
                            if (!worldObj.isRemote) {
                                fill(ForgeDirection.UP, Fluids.getFluidStack("natural_gas", 1000), true);
                                return worldObj.setBlockToAir(x, y, z); //When we have collected one block, exit the loop
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isNaturalGas(int x, int y, int z) {
        return worldObj.getBlock(x, y, z) == Core.air && worldObj.getBlockMetadata(x, y, z) == AirMeta.NATURAL_GAS;
    }

    public double getWheelAngle() {
        return wheelAngle;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (isAnimating) {
            if ((worldObj.isRemote && Client.PUMP_ANIM) || !worldObj.isRemote) {
                wheelAngle += 0.1;

                if (wheelAngle > 6.2198) {
                    wheelAngle = 0;
                    isAnimating = false;
                }
            }
        }

        if (!worldObj.isRemote) {
            if (getEnergyStored(ForgeDirection.UP) > 0) {
                tick++;

                //Every 300 ticks supply air to players, if we have 100RF or more in the buffer
                if ((Modules.isActive(Modules.diving)) && onTick(Ticks.PUMP_TICK_TIMER)) {
                    if (storage.getEnergyStored() >= 100) {
                        supplyWithAir(300, 40.0D, 64.0D, 40.0D);

                        //Extract 100 RF everytime we supply air
                        storage.extractEnergy(100, false);
                        updateAnimation();
                    }
                }

                if (MaricultureHandlers.HIGH_TECH_ENABLED) {
                    //If we have a redstone signal, and every second we should try to collect gas
                    if (onTick(Ticks.PUMP_GAS_TIMER)) {
                        if (storage.getEnergyStored() >= 100) {
                            if (suckUpGas()) {
                                ejectFluids();
                            }

                            //Extract 10 RF everytime we extract gas from the air
                            storage.extractEnergy(100, false);
                            updateAnimation();
                        }

                        //Attempt to eject gas every 5 seconds
                        if (onTick(Ticks.PUMP_GAS_EJECT_TIMER)) {
                            ejectFluids();
                        }
                    }
                }
            }
        }
    }

    private void ejectFluids() {
        if (helper == null) helper = new BlockTransferHelper(this);
        helper.ejectFluid(new int[] { 8000, 4000, 2000, 1000, 100, 20, 1 });
    }

    private void updateAnimation() {
        //If we aren't animating, start animating and send a packet
        if (!isAnimating) {
            isAnimating = true;
            if (!worldObj.isRemote) {
                PacketHandler.sendAround(new PacketAirPump(xCoord, yCoord, zCoord), this);
            }
        }
    }

    @Override
    public boolean rotate() {
        setFacing(BlockHelper.rotate(orientation, 4));
        return true;
    }

    @Override
    public ForgeDirection getFacing() {
        return orientation;
    }

    @Override
    public void setFacing(ForgeDirection dir) {
        orientation = dir;
        if (!worldObj.isRemote) {
            PacketHandler.updateOrientation(this);
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        storage.writeToNBT(nbt);
        nbt.setInteger("Orientation", orientation.ordinal());
    }
}
