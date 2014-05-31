package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.core.helpers.ClientHelper;
import mariculture.factory.tile.TileTurbineBase;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTurbine extends PacketCoords implements IMessageHandler<PacketTurbine, IMessage> {
	boolean isAnimating;
	public PacketTurbine(){}
	public PacketTurbine(int x, int y, int z, boolean isAnimating) {
		super(x, y, z);
		this.isAnimating = isAnimating;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		super.toBytes(buffer);
		buffer.writeBoolean(isAnimating);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		super.fromBytes(buffer);
		isAnimating = buffer.readBoolean();
	}

	@Override
	public IMessage onMessage(PacketTurbine message, MessageContext ctx) {
		EntityPlayer player = ClientHelper.getPlayer();
		if(player.worldObj.getTileEntity(message.x, message.y, message.z) instanceof TileTurbineBase) {
			((TileTurbineBase)player.worldObj.getTileEntity(message.x, message.y, message.z)).isAnimating = isAnimating;
		}
		
		return null;
	}
}
