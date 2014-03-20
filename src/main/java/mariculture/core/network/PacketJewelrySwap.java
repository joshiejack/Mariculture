package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.Mariculture;
import mariculture.magic.MirrorData;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;

public class PacketJewelrySwap extends AbstractPacket {
	int slot;
	public PacketJewelrySwap(){}
	public PacketJewelrySwap(int slot) {
		this.slot = slot;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(slot);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.slot = buffer.readInt();
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		ItemStack[] mirror = MirrorData.getInventoryForPlayer(player);
		ItemStack stack = player.inventory.mainInventory[slot];
		if(stack != null) {
			int type = ((ItemJewelry)stack.getItem()).getType().ordinal();
			ItemStack inMirror = mirror[type];
			mirror[type] = stack;
			player.setCurrentItemOrArmor(0, inMirror);
			MirrorData.save(player, mirror);
			Mariculture.packets.sendTo(new PacketSyncMirror(MirrorData.getInventoryForPlayer(player)), (EntityPlayerMP) player);
		}
	}

}
