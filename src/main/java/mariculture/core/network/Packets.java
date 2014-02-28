package mariculture.core.network;

import mariculture.Mariculture;
import mariculture.core.gui.ContainerMariculture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class Packets {
	public static void updateTile(TileEntity tile, int size, Packet packet) {
		//TODO: PacketDispatcher.sendPacketToAllAround(tile.xCoord, tile.yCoord, tile.zCoord, size, tile.worldObj.provider.dimensionId, packet);
	}
	
	public static void updatePlayer(EntityPlayer player, int size, Packet packet) {
		//TODO: PacketDispatcher.sendPacketToAllAround(player.posX, player.posY, player.posZ, size, player.worldObj.provider.dimensionId, packet);
	}
	
	public static void updateAround(TileEntity tile, AbstractPacket packet) {
		Mariculture.packets.sendToAllAround(packet, new TargetPoint(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, 176));
	}
	
	public static void updateGUI(EntityPlayer player, ContainerMariculture container, int id, int val) {
		Mariculture.packets.sendTo(new PacketGUI(container.windowId, id, val), (EntityPlayerMP) player);
	}

	public static void syncInventory(TileEntity tile, ItemStack[] inventory) {
		updateAround(tile, new PacketInventorySync(tile.xCoord, tile.yCoord, tile.zCoord, inventory));
	}
}
