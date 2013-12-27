package mariculture.core.blocks;

import java.util.Random;

import mariculture.core.Mariculture;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.diving.Diving;
import mariculture.diving.TileAirCompressor;
import mariculture.diving.TileAirCompressorPower;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;

public class BlockDouble extends BlockMachine {
	private Icon[] icons;

	public BlockDouble(int i) {
		super(i, Material.iron);
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
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int j, float f, float g, float t) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile == null || player.isSneaking()) {
			return false;
		}
		
		if (tile instanceof TileAirCompressor && player.getCurrentEquippedItem() != null && !world.isRemote) {
			TileAirCompressor compressor = (TileAirCompressor) (((TileAirCompressor) tile).getMasterBlock());
			if (compressor != null && player.getCurrentEquippedItem().getItem() == Diving.scubaTank && compressor.currentAir > 0) {
				if (player.getCurrentEquippedItem().getItemDamage() > 0 && compressor.currentAir > 68) {
					giveOxygen(player.getCurrentEquippedItem(), compressor);
				}
			}
		}

		if (tile instanceof TileAirCompressorPower && player.getCurrentEquippedItem() != null && !world.isRemote) {
			Item currentItem = player.getCurrentEquippedItem().getItem();
			TileAirCompressorPower power = (TileAirCompressorPower) (((TileAirCompressorPower) tile).getMasterBlock());			
			if (power != null && currentItem instanceof IEnergyContainerItem) {
				int powerAdd = ((IEnergyContainerItem)currentItem).extractEnergy(player.getCurrentEquippedItem(), 1000, true);
				if (powerAdd >= 100) {
					power.addPower(powerAdd/100);
					((IEnergyContainerItem)currentItem).extractEnergy(player.getCurrentEquippedItem(), powerAdd, false);
				}
			}
		}

		if (tile instanceof TilePressureVessel) {
			TilePressureVessel vessel = (TilePressureVessel) (((TilePressureVessel) tile).getMasterBlock());
			if (vessel != null && vessel.isFormed()) {
				player.openGui(Mariculture.instance, GuiIds.PRESSURE_VESSEL, world, vessel.xCoord, vessel.yCoord, vessel.zCoord);
				return true;
			}
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
