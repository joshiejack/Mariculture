package mariculture.core.blocks;

import mariculture.core.blocks.base.TileMultiInvTank;
import mariculture.core.network.Packets;
import mariculture.factory.blocks.Tank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class TileForge extends TileMultiInvTank {
	public int blocksInStructure = 1;
	private int machineTick = 0;
	
	public TileForge() {
		inventory = new ItemStack[1];
		tank = new Tank(getTankCapacity(0));
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	public int getTankCapacity(int storage) {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return blocksInStructure * tankRate;
	}
	
	@Override
	public Packet getDescriptionPacket() {		
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
	}
	
	@Override
	public void updateAll() {
		machineTick++;
		
		if(onTick(30)) {
			Packets.updateTile(this, 32, getDescriptionPacket());
		}
	}
}
