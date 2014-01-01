package mariculture.core.network;

import mariculture.core.gui.ContainerMariculture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class Packets {
	public static void updateTile(TileEntity tile, int size, Packet packet) {
		PacketDispatcher.sendPacketToAllAround(tile.xCoord, tile.yCoord, tile.zCoord, size, tile.worldObj.provider.dimensionId, packet);
	}
	
	public static void updatePlayer(EntityPlayer player, int size, Packet packet) {
		PacketDispatcher.sendPacketToAllAround(player.posX, player.posY, player.posZ, size, player.worldObj.provider.dimensionId, packet);
	}
	
	public static void updateGUI(EntityPlayer player, ContainerMariculture container, int id, int val) {
		PacketDispatcher.sendPacketToPlayer(new Packet104GUI(container.windowId, id, val).build(), (Player) player);
	}

	public static void init() {
		PacketRegistry.register(new Packet101Sponge());
		PacketRegistry.register(new Packet102AirPump());
		PacketRegistry.register(new Packet103Oyster());
		PacketRegistry.register(new Packet104GUI());
		PacketRegistry.register(new Packet105OneRing());
		PacketRegistry.register(new Packet106SwapJewelry());
		PacketRegistry.register(new Packet107FLUDD());
		PacketRegistry.register(new Packet108Teleport());
		PacketRegistry.register(new Packet109DamageJewelry());
		PacketRegistry.register(new Packet110CustomTileUpdate());
		PacketRegistry.register(new Packet111UpdateEnchants());
		PacketRegistry.register(new Packet112Enchant());
		PacketRegistry.register(new Packet113RequestMaster());
		PacketRegistry.register(new Packet114RedstoneControlled());
		PacketRegistry.register(new Packet115EjectSetting());
		PacketRegistry.register(new Packet116GUIClick());
	}
}
