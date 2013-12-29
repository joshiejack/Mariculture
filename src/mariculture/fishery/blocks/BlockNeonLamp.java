package mariculture.fishery.blocks;

import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureRegistry;
import mariculture.core.Mariculture;
import mariculture.core.blocks.BlockDecorative;
import mariculture.core.lib.PearlColor;
import mariculture.fishery.Fishery;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNeonLamp extends BlockDecorative {
	private final boolean powered;

	public BlockNeonLamp(int i, boolean powered) {
		super(i, Material.glass);
		this.powered = powered;

		if (!powered) {
			this.setLightValue(1.0F);
		}

		this.setHardness(1F);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, blockID, 4);
			} else if (!this.powered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, Fishery.lampsOff.blockID, world.getBlockMetadata(x, y, z), 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int side) {
		if (!world.isRemote) {
			if (this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, blockID, 4);
			} else if (!this.powered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, Fishery.lampsOff.blockID, world.getBlockMetadata(x, y, z), 2);
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote && this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.setBlock(x, y, z, Fishery.lampsOn.blockID, world.getBlockMetadata(x, y, z), 2);
		}
	}

	@Override
	public int idDropped(int id, Random random, int par3) {
		return Fishery.lampsOn.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int x, int y, int z) {
		return Fishery.lampsOn.blockID;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
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
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public float getBlockBrightness(IBlockAccess block, int x, int y, int z) {
		if (block.getBlockId(x, y, z) == Fishery.lampsOn.blockID) {
			return 15F;
		}

		return block.getBrightness(x, y, z, getLightValue(block, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		icons = new Icon[PearlColor.COUNT];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = register.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)) + i);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTabs, List list) {
		if (id == Fishery.lampsOn.blockID) {
			for (int meta = 0; meta < PearlColor.COUNT; ++meta) {
				if (!list.contains(new ItemStack(id, 1, meta))) {
					list.add(new ItemStack(id, 1, meta));
				}
			}
		}
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register("neonLamp." + getName(new ItemStack(this.blockID, 1, j)), new ItemStack(
					this.blockID, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return PearlColor.COUNT;
	}
}
