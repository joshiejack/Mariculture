package mariculture.core.network;

import mariculture.Mariculture;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.lib.Extra;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class Packets {	
	public static TargetPoint getTarget(TileEntity tile) {
		return new TargetPoint(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, Extra.PACKET_DISTANCE);
	}
	
	public static TargetPoint getTarget(EntityPlayer player) {
		return new TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, Extra.PACKET_DISTANCE);
	}
	
	public static void updateAround(TileEntity tile, AbstractPacket packet) {
		Mariculture.packets.sendToAllAround(packet, getTarget(tile));
	}
	
	public static void updateGUI(EntityPlayer player, ContainerMariculture container, int id, int val) {
		Mariculture.packets.sendTo(new PacketGUI(container.windowId, id, val), (EntityPlayerMP) player);
	}

	public static void syncInventory(TileEntity tile, ItemStack[] inventory) {
		updateAround(tile, new PacketInventorySync(tile.xCoord, tile.yCoord, tile.zCoord, inventory));
	}

	public static void syncFluids(TileEntity tile, FluidStack fluid) {
		syncFluidTank(tile, fluid, (byte) 0);
	}
	
	public static void syncFluidTank(TileEntity tile, FluidStack fluid, byte tank) {
		updateAround(tile, new PacketFluidSync(tile.xCoord, tile.yCoord, tile.zCoord, fluid, tank));
	}

	public static void updateRender(TileEntity tile) {
		updateAround(tile, new PacketRenderRefresh(tile.xCoord, tile.yCoord, tile.zCoord));
	}
}
