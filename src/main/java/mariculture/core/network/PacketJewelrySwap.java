package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.magic.MirrorHelper;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketJewelrySwap implements IMessage, IMessageHandler<PacketJewelrySwap, IMessage> {
	int slot;
	public PacketJewelrySwap(){}
	public PacketJewelrySwap(int slot) {
		this.slot = slot;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(slot);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.slot = buffer.readInt();
	}

	@Override
	public IMessage onMessage(PacketJewelrySwap message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		ItemStack[] mirror = MirrorHelper.getInventoryForPlayer(player);
		ItemStack stack = player.inventory.mainInventory[message.slot];
		if(stack != null) {
			int type = ((ItemJewelry)stack.getItem()).getType().ordinal();
			ItemStack inMirror = mirror[type];
			mirror[type] = stack;
			player.setCurrentItemOrArmor(0, inMirror);
			MirrorHelper.save(player, mirror);
			PacketHandler.sendToClient(new PacketSyncMirror(MirrorHelper.getInventoryForPlayer(player)), (EntityPlayerMP) player);
		}
		
		return null;
	}
}
