package mariculture.factory.blocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.blocks.TileTankMachine;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.PacketIds;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import buildcraft.api.core.Position;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileTurbineWater extends TileTurbineBase {	
	@Override
	public boolean canUseLiquid() {
		return tank.getFluidID() == Core.highPressureWater.getID();
	}

	@Override
	public int maxEnergyStored() {
		return 250;
	}

	@Override
	public int maxEnergyExtracted() {
		return 20;
	}
}