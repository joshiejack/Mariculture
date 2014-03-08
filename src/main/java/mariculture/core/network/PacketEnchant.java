package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.magic.gui.ContainerMirror;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PacketEnchant extends AbstractPacket {
	public int windowID;
	public int level;

	public PacketEnchant() {}
	public PacketEnchant(int windowID, int level) {
		this.windowID = windowID;
		this.level = level;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(windowID);
		buffer.writeInt(level);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		windowID = buffer.readInt();
		level = buffer.readInt();
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		if (player.openContainer.windowId == windowID && player.openContainer.isPlayerNotUsingContainer(player)) {
			ContainerMirror mirror = (ContainerMirror) player.openContainer;
			mirror.windowId = windowID;
			mirror.enchantItem(player, level);
			mirror.detectAndSendChanges();
		}
	}
}
