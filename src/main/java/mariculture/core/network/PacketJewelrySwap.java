package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.magic.MirrorData;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
	public void handleClientSide(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		ItemStack[] mirror = MirrorData.getInventoryForPlayer(player);
		ItemStack stack = player.inventory.mainInventory[slot];
		if(stack != null) {
			int type = ((ItemJewelry)stack.getItem()).getType();
			ItemStack inMirror = mirror[type];
			mirror[type] = stack;
			player.setCurrentItemOrArmor(0, inMirror);
			MirrorData.save(player, mirror);
		}
	}

}
