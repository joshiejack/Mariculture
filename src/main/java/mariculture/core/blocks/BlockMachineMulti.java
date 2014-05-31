package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFunctionalMulti;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RockMeta;
import mariculture.core.tile.TileCrucible;
import mariculture.fishery.tile.TileIncubator;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachineMulti extends BlockFunctionalMulti {
	private IIcon[] crucibleIcons;
	private IIcon[] incubatorIcons;
	
	public BlockMachineMulti() {
		super(Material.piston);
	}
	
	@Override
	public String getToolType(int meta) {
		return "pickaxe";
	}

	@Override
	public int getToolLevel(int meta) {
		return 2;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
			case MachineMultiMeta.CRUCIBLE: 	return 6F;
			case MachineMultiMeta.INCUBATOR_BASE: return 10F;
			case MachineMultiMeta.INCUBATOR_TOP: return 5F;
			default:							return 5F;
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return side < 2 && meta == MachineMultiMeta.CRUCIBLE? Core.rocks.getIcon(side, RockMeta.BASE_BRICK): super.getIcon(side, meta);
	}
	
	@Override
	public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
		if(side > 1) {
			TileEntity tile = block.getTileEntity(x, y, z);
			if(tile instanceof TileCrucible) {
				TileCrucible crucible = (TileCrucible) tile;
				if(crucible.master == null) return getIcon(side, MachineMultiMeta.CRUCIBLE);
				else {
					if(crucible.isMaster()) return crucibleIcons[1];
					else return crucibleIcons[0];
				}
			} else if (tile instanceof TileIncubator && block.getBlockMetadata(x, y, z) != MachineMultiMeta.INCUBATOR_BASE) {
				TileIncubator incubator = (TileIncubator) tile;
				if(incubator.master == null) return getIcon(side, MachineMultiMeta.INCUBATOR_TOP);
				else {
					if(incubator.facing == ForgeDirection.DOWN) return incubatorIcons[0];
					else return incubatorIcons[1];
				}
			}
		}
		
		return super.getIcon(block, x, y, z, side);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch (meta) {
			case MachineMultiMeta.CRUCIBLE: 	return new TileCrucible();
			case MachineMultiMeta.INCUBATOR_BASE: return new TileIncubator();
			case MachineMultiMeta.INCUBATOR_TOP: return new TileIncubator();
			default:							return null;
		}
	}
	
	@Override
	public boolean isActive(int meta) {
		switch (meta) {
			case MachineMultiMeta.CRUCIBLE: 	return true;
			case MachineMultiMeta.INCUBATOR_BASE: return Modules.isActive(Modules.fishery);
			case MachineMultiMeta.INCUBATOR_TOP: return Modules.isActive(Modules.fishery);
			default:							return true;
		}
	}
	
	@Override
	public boolean isValidTab(CreativeTabs tab, int meta) {
		switch (meta) {
			case MachineMultiMeta.INCUBATOR_BASE: return tab == MaricultureTab.tabFishery;
			case MachineMultiMeta.INCUBATOR_TOP:  return tab == MaricultureTab.tabFishery;
			default:							  return tab == MaricultureTab.tabFactory;
		}
	}

	@Override
	public int getMetaCount() {
		return MachineMultiMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		
		//Extra Icons for the blocks
		incubatorIcons = new IIcon[2];
		incubatorIcons[0] = iconRegister.registerIcon(Mariculture.modid + ":incubatorBottom");
		incubatorIcons[1] = iconRegister.registerIcon(Mariculture.modid + ":incubatorTopTop");

		crucibleIcons = new IIcon[2];
		crucibleIcons[0] = iconRegister.registerIcon(Mariculture.modid + ":crucibleTop");
		crucibleIcons[1] = iconRegister.registerIcon(Mariculture.modid + ":crucibleBottom");
	}
}
