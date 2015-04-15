package maritech.extensions.blocks;

import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketHandler;
import maritech.extensions.modules.ExtensionDiving;
import maritech.network.PacketCompressor;
import maritech.tile.TileAirCompressor;
import maritech.tile.TilePressureVessel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;

public class ExtensionRenderedMachineMulti extends ExtensionBlocksBase {
    @Override
    public String getName(int meta, String name) {
        switch (meta) {
            case MachineRenderedMultiMeta.COMPRESSOR_BASE:
                return "airCompressor";
            case MachineRenderedMultiMeta.COMPRESSOR_TOP:
                return "airCompressorPower";
            case MachineRenderedMultiMeta.PRESSURE_VESSEL:
                return "pressureVessel";
        }

        return name;
    }

    @Override
    public String getMod(int meta, String name) {
        switch (meta) {
            case MachineRenderedMultiMeta.COMPRESSOR_BASE:
            case MachineRenderedMultiMeta.COMPRESSOR_TOP:
            case MachineRenderedMultiMeta.PRESSURE_VESSEL:
                return "maritech";
        }

        return name;
    }

    @Override
    public boolean isActive(int meta, boolean isActive) {
        switch (meta) {
            case MachineRenderedMultiMeta.COMPRESSOR_BASE:
                return Modules.isActive(Modules.diving);
            case MachineRenderedMultiMeta.COMPRESSOR_TOP:
                return Modules.isActive(Modules.diving);
            case MachineRenderedMultiMeta.PRESSURE_VESSEL:
                return Modules.isActive(Modules.factory);
        }

        return isActive;
    }

    @Override
    public float getHardness(int meta, float hardness) {
        switch (meta) {
            case MachineRenderedMultiMeta.COMPRESSOR_BASE:
                return 6F;
            case MachineRenderedMultiMeta.COMPRESSOR_TOP:
                return 4F;
            case MachineRenderedMultiMeta.PRESSURE_VESSEL:
                return 15F;
        }

        return hardness;
    }

    @Override
    public TileEntity getTileEntity(int meta, TileEntity tile) {
        switch (meta) {
            case MachineRenderedMultiMeta.COMPRESSOR_BASE:
                return new TileAirCompressor();
            case MachineRenderedMultiMeta.COMPRESSOR_TOP:
                return new TileAirCompressor();
            case MachineRenderedMultiMeta.PRESSURE_VESSEL:
                return new TilePressureVessel();
        }

        return tile;
    }

    @Override
    public boolean onRightClickBlock(World world, int x, int y, int z, EntityPlayer player) {
        TileEntity tile = world.getTileEntity(x, y, z);
        ItemStack heldItem = player.getCurrentEquippedItem();
        if (heldItem != null && tile instanceof TileAirCompressor) {
            TileAirCompressor te = (TileAirCompressor) ((TileAirCompressor) tile).getMaster();
            if (te != null) {
                int rf = heldItem.getItem() instanceof IEnergyContainerItem ? ((IEnergyContainerItem) heldItem.getItem()).extractEnergy(heldItem, 5000, true) : 0;
                if (rf > 0) {
                    int drain = te.receiveEnergy(ForgeDirection.UP, rf, true);
                    if (drain > 0) {
                        ((IEnergyContainerItem) heldItem.getItem()).extractEnergy(heldItem, drain, false);
                        te.receiveEnergy(ForgeDirection.UP, drain, false);
                    }

                    return true;
                }

                if (heldItem.getItem() == ExtensionDiving.scubaTank) {
                    boolean changed = false;
                    int loop = !player.isSneaking() ? 1000 : 1;
                    for (int i = 0; i < loop; i++) {
                        if (heldItem.getItemDamage() > 1 && te.storedAir > 0) {
                            heldItem.setItemDamage(heldItem.getItemDamage() - 1);
                            if (!world.isRemote) {
                                te.storedAir--;
                                changed = true;
                            }
                        }
                    }

                    if (changed) {
                        PacketHandler.sendAround(new PacketCompressor(te.xCoord, te.yCoord, te.zCoord, te.storedAir, te.getEnergyStored(ForgeDirection.UP)), te);
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
