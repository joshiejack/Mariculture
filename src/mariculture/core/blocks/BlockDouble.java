package mariculture.core.blocks;

import java.util.Random;

import mariculture.core.Mariculture;
import mariculture.core.blocks.base.TileMulti;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.network.Packet113RequestMaster;
import mariculture.diving.Diving;
import mariculture.diving.TileAirCompressor;
import mariculture.diving.TileAirCompressorPower;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDouble extends BlockMachine {
	private Icon[] icons;

	public BlockDouble(int i) {
		super(i, Material.iron);
	}
	
	public void updateMaster(World world, int x, int y, int z) {
		if(world.getBlockTileEntity(x, y, z) instanceof TileMulti) {
			((TileMulti) world.getBlockTileEntity(x, y, z)).setMaster();
		}
	}
	
	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		notify(world, x, y, z);
		updateMaster(world, x, y, z);
		super.onBlockExploded(world, x, y, z, explosion);
    }
	
	private void notify(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TilePressureVessel) {
			TilePressureVessel vessel = (TilePressureVessel) tile;
			if(vessel.isMaster()) {
				assignNewMaster(world, vessel.tiles);
			} else {
				notifyMasterOfDeletion(world, vessel.mstr.x, vessel.mstr.y, vessel.mstr.z);
			}
		}
	}

	private void notifyMasterOfDeletion(World world, int x, int y, int z) {
		TilePressureVessel vessel = (TilePressureVessel) world.getBlockTileEntity(x, y, z);
		if(vessel != null) {
			vessel.blocksInStructure--;
			for (int i = 0; i < vessel.tiles.length; i++) {
				int xCoord = vessel.tiles[i][0];
				int yCoord = vessel.tiles[i][1];
				int zCoord = vessel.tiles[i][2];
				if(x == xCoord && y == yCoord && z == zCoord) {
					vessel.tiles[i][0] = 0;
					vessel.tiles[i][1] = 0;
					vessel.tiles[i][2] = 0;
					return;
				}
			}
		}
	}

	private void assignNewMaster(World world, int[][] tiles) {
		int xCoord = 0;
		int yCoord = 0;
		int zCoord = 0;
		
		for (int i = 0; i < tiles.length; i++) {
			xCoord = tiles[i][0];
			yCoord = tiles[i][1];
			zCoord = tiles[i][2];
			if(xCoord == 0 && yCoord == 0 && zCoord == 0)
				continue;
			if(world.getBlockTileEntity(xCoord, yCoord, zCoord) != null &&
					world.getBlockTileEntity(xCoord, yCoord, zCoord) instanceof TilePressureVessel) {
				break;
			}
		}
		
		for (int i = 0; i < tiles.length; i++) {
			int x = tiles[i][0];
			int y = tiles[i][1];
			int z = tiles[i][2];
			if(x == 0 && y == 0 && z == 0)
				continue;
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null && tile instanceof TilePressureVessel) {
				TilePressureVessel vessel = (TilePressureVessel) tile;
				vessel.mstr.x = (yCoord != 0)? xCoord: x;
				vessel.mstr.y = (yCoord != 0)? yCoord: y;
				vessel.mstr.z = (yCoord != 0)? zCoord: z;
			}
		}
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		notify(world, x, y, z);
		updateMaster(world, x, y, z);
		return super.removeBlockByPlayer(world, player, x, y, z);
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int otherID) {
		updateMaster(world, x, y, z);
		super.onNeighborBlockChange(world, x, y, z, otherID);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case DoubleMeta.AIR_COMPRESSOR:
			return 5F;
		case DoubleMeta.AIR_COMPRESSOR_POWER:
			return 3F;
		case DoubleMeta.PRESSURE_VESSEL:
			return 6F;
		}

		return 3F;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) != DoubleMeta.PRESSURE_VESSEL)
			return true;
		
		int count = 0;

		if (world.getBlockId(x - 1, y, z) == this.blockID) {
			++count;
		}

		if (world.getBlockId(x + 1, y, z) == this.blockID) {
			++count;
		}

		if (world.getBlockId(x, y, z - 1) == this.blockID) {
			++count;
		}

		if (world.getBlockId(x, y, z + 1) == this.blockID) {
			++count;
		}

		return count > 1 ? false : (this.isThereSameBlock(world, x - 1, y, z) ? false : (this
				.isThereSameBlock(world, x + 1, y, z) ? false : (this.isThereSameBlock(world, x, y, z - 1) ? false : !this
				.isThereSameBlock(world, x, y, z + 1))));
	}

	private boolean isThereSameBlock(World world, int x, int y, int z) {
		return world.getBlockId(x, y, z) != this.blockID ? false : (world.getBlockId(x - 1, y, z) == this.blockID ? true : (world
				.getBlockId(x + 1, y, z) == this.blockID ? true : (world.getBlockId(x, y, z - 1) == this.blockID ? true : world
				.getBlockId(x, y, z + 1) == this.blockID)));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		updateMaster(world, x, y, z);
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int j, float f, float g, float t) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile == null || player.isSneaking()) {
			return false;
		}
		
		if (tile instanceof TileAirCompressor && player.getCurrentEquippedItem() != null && !world.isRemote) {
			TileMulti base = (TileMulti) tile;
			TileAirCompressor compressor = (base.mstr.built)? (TileAirCompressor)world.getBlockTileEntity(base.mstr.x, base.mstr.y, base.mstr.z): null;
			if (compressor != null && player.getCurrentEquippedItem().getItem() == Diving.scubaTank && compressor.currentAir > 0) {
				if (player.getCurrentEquippedItem().getItemDamage() > 0 && compressor.currentAir > 68) {
					giveOxygen(player.getCurrentEquippedItem(), compressor);
				}
			}
		}

		if (tile instanceof TileAirCompressorPower && player.getCurrentEquippedItem() != null && !world.isRemote) {
			Item currentItem = player.getCurrentEquippedItem().getItem();
			//TileAirCompressorPower power = (TileAirCompressorPower) (((TileAirCompressorPower) tile).getMasterBlock());			
			 /*if (power != null && currentItem instanceof IEnergyContainerItem) {
				int powerAdd = ((IEnergyContainerItem)currentItem).extractEnergy(player.getCurrentEquippedItem(), 1000, true);
				if (powerAdd >= 100) {
					power.addPower(powerAdd/100);
					((IEnergyContainerItem)currentItem).extractEnergy(player.getCurrentEquippedItem(), powerAdd, false);
				}
			} */
		}
		
		if (tile instanceof TilePressureVessel) {
			if(player instanceof EntityClientPlayerMP)
				((EntityClientPlayerMP) player).sendQueue.addToSendQueue(new Packet113RequestMaster(x, y, z).build());
			
			TileMulti base = (TileMulti) tile;
			
			System.out.println(base.mstr.x);
			
			TileMulti master = (base.mstr.built)? (TilePressureVessel)world.getBlockTileEntity(base.mstr.x, base.mstr.y, base.mstr.z): null;
			if (master != null) {
				player.openGui(Mariculture.instance, GuiIds.PRESSURE_VESSEL, world, master.xCoord, master.yCoord, master.zCoord);
				return true;
			}

			return false;
		}

		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		final int meta = block.getBlockMetadata(x, y, z);

		switch (meta) {
		case DoubleMeta.AIR_COMPRESSOR_POWER:
			setBlockBounds(0F, 0F, 0F, 1F, 0.15F, 1F);
			break;
		default:
			setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
		}
	}

	private void giveOxygen(ItemStack tank, TileAirCompressor compressor) {
		int newTank = tank.getItemDamage();
		int newCompressor = compressor.currentAir;

		int maxCanRepair = compressor.currentAir;
		int maxNeeds = tank.getItemDamage();

		if (maxCanRepair > 240) {
			maxCanRepair = 240;
		}

		if (maxNeeds <= maxCanRepair) {
			newTank = tank.getItemDamage() - maxNeeds;
			newCompressor = compressor.currentAir - maxNeeds;
		}

		if (maxNeeds > maxCanRepair) {
			newTank = tank.getItemDamage() - maxCanRepair;
			newCompressor = compressor.currentAir - maxCanRepair;
		}

		compressor.currentAir = newCompressor;

		tank.setItemDamage(newTank);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch (meta) {
		case DoubleMeta.AIR_COMPRESSOR:
			return new TileAirCompressor();
		case DoubleMeta.AIR_COMPRESSOR_POWER:
			return new TileAirCompressorPower();
		case DoubleMeta.PRESSURE_VESSEL:
			return new TilePressureVessel();

		}

		return null;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.BLOCK_DOUBLE;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int i, int j) {
		updateMaster(world, x, y, z);
		super.breakBlock(world, x, y, z, i, j);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return this.blockID;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case DoubleMeta.AIR_COMPRESSOR:
			return (Modules.diving.isActive());
		case DoubleMeta.AIR_COMPRESSOR_POWER:
			return (Modules.diving.isActive());
		case DoubleMeta.PRESSURE_VESSEL:
			return (Modules.factory.isActive());

		default:
			return true;
		}
	}
	
	@Override
	public int getMetaCount() {
		return DoubleMeta.COUNT;
	}
}
