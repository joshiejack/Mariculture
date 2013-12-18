package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.BlockStep;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLimestoneSlab extends BlockStep implements IItemRegistry {
	public enum BlockType {
		ROUGH(0), BRICK(1), SMOOTH(2), CHISELED(3);

		private int meta;

		BlockType(int metadata) {
			this.meta = metadata;
		}

		public int metadata() {
			return meta;
		}

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	private static int singleSlabID = 0;

	public BlockLimestoneSlab(int id, boolean isDouble) {
		super(id, isDouble);
		if (!isDouble) {
			singleSlabID = blockID;
		}
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(soundStoneFootstep);
		setLightOpacity(0);
		setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, final int meta) {
		final int k = meta & 7;

		if (this.isDoubleSlab && (meta & 8) != 0) {
			side = 1;
		}

		return k == 3 ? (side != 1 && side != 0 ? this.blockIcon : Core.oreBlocks.getIcon(side, OresMeta.LIMESTONE_CHISELED))
				: (k == 2 ? Core.oreBlocks.getIcon(side, OresMeta.LIMESTONE_SMOOTH) : (k == 1 ? Core.oreBlocks.getIcon(side,
						OresMeta.LIMESTONE_BRICK) : (k == 0 ? Core.oreBlocks.getIcon(side, OresMeta.LIMESTONE) : this.blockIcon)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("mariculture:smoothSlabSide");
	}

	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(singleSlabID, 2, meta & 7);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return singleSlabID;
	}

	@Override
	public String getFullSlabName(int meta) {
		String blockStepType;
		switch (meta & 7) {
		case 1:
			blockStepType = BlockType.BRICK.toString();
			break;
		case 2:
			blockStepType = BlockType.SMOOTH.toString();
			break;
		case 3:
			blockStepType = BlockType.CHISELED.toString();
			break;
		default:
			blockStepType = BlockType.ROUGH.toString();
		}

		return super.getUnlocalizedName() + "." + blockStepType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int blockID, CreativeTabs tab, List itemList) {
		if (blockID == singleSlabID) {
			for (BlockType type : BlockType.values()) {
				itemList.add(new ItemStack(blockID, 1, type.metadata()));
			}
		}
	}
	
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.blockID, 1, j)), new ItemStack(this.blockID, 1, j));
		}
	}

	public int getMetaCount() {
		return 4;
	}

	@Override
	public String getName(ItemStack stack) {
		return getFullSlabName(stack.getItemDamage()).substring(5);
	}
}