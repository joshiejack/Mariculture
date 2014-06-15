package mariculture.core.network;

import mariculture.Mariculture;
import mariculture.core.config.Machines.MachineSettings;
import mariculture.core.util.IFaceable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
    private static int id;
    private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Mariculture.modid);

    private static void registerPacket(Class clazz, Side side) {
        INSTANCE.registerMessage(clazz, clazz, id++, side);
    }

    public static void init() {
        registerPacket(PacketAirPump.class, Side.CLIENT);
        registerPacket(PacketCompressor.class, Side.CLIENT);
        registerPacket(PacketFluidSync.class, Side.CLIENT);
        registerPacket(PacketInventorySync.class, Side.CLIENT);
        registerPacket(PacketMultiInit.class, Side.CLIENT);
        registerPacket(PacketOrientationSync.class, Side.CLIENT);
        registerPacket(PacketRenderRefresh.class, Side.CLIENT);
        registerPacket(PacketSyncMirror.class, Side.CLIENT);
        registerPacket(PacketTurbine.class, Side.CLIENT);

        registerPacket(PacketClick.class, Side.SERVER);
        registerPacket(PacketDamageJewelry.class, Side.SERVER);
        registerPacket(PacketEnchant.class, Side.SERVER);
        registerPacket(PacketJewelrySwap.class, Side.SERVER);
        registerPacket(PacketTeleport.class, Side.SERVER);

        //Dual Way Packets
        registerPacket(PacketFLUDD.class, Side.CLIENT);
        registerPacket(PacketFLUDD.class, Side.SERVER);
        registerPacket(PacketSponge.class, Side.CLIENT);
        registerPacket(PacketSponge.class, Side.SERVER);

        //New Packets
        registerPacket(PacketCrack.class, Side.CLIENT);
    }

    public static void sendToClient(IMessage packet, EntityPlayerMP player) {
        INSTANCE.sendTo(packet, player);
    }

    public static void sendAround(IMessage packet, int dim, double x, double y, double z) {
        INSTANCE.sendToAllAround(packet, new TargetPoint(dim, x, y, z, MachineSettings.PACKET_DISTANCE));
    }

    public static void sendToServer(IMessage packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void sendAround(IMessage packet, TileEntity tile) {
        sendAround(packet, tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public static void syncInventory(TileEntity tile, ItemStack[] inventory) {
        sendAround(new PacketInventorySync(tile.xCoord, tile.yCoord, tile.zCoord, inventory), tile);
    }

    public static void syncFluids(TileEntity tile, FluidStack fluid) {
        syncFluidTank(tile, fluid, (byte) 0);
    }

    public static void syncFluidTank(TileEntity tile, FluidStack fluid, byte tank) {
        sendAround(new PacketFluidSync(tile.xCoord, tile.yCoord, tile.zCoord, fluid, tank), tile);
    }

    public static void syncMultiBlock(TileEntity master, TileEntity slave, ForgeDirection dir) {
        sendAround(new PacketMultiInit(slave.xCoord, slave.yCoord, slave.zCoord, master.xCoord, master.yCoord, master.zCoord, dir), slave);
    }

    public static void breakMultiBlock(TileEntity slave) {
        sendAround(new PacketMultiInit(slave.xCoord, slave.yCoord, slave.zCoord, 0, -1, 0, ForgeDirection.UNKNOWN), slave);
    }

    public static void updateOrientation(TileEntity tile) {
        IFaceable faceable = (IFaceable) tile;
        sendAround(new PacketOrientationSync(tile.xCoord, tile.yCoord, tile.zCoord, faceable.getFacing()), tile);
    }

    public static void updateRender(TileEntity tile) {
        sendAround(new PacketRenderRefresh(tile.xCoord, tile.yCoord, tile.zCoord), tile);
    }
}
