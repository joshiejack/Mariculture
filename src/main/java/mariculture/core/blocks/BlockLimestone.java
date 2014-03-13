package mariculture.core.blocks;

import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.helpers.cofh.BlockHelper.RotationType;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.WoodMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLimestone extends BlockDecorative {
	public BlockLimestone() {
		super(Material.rock);
		setResistance(5F);
	}
	
	@Override
	public String getToolType(int meta) {
		return "pickaxe";
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch(world.getBlockMetadata(x, y, z)) {
			case LimestoneMeta.RAW: 		return 0.75F;
			case LimestoneMeta.SMOOTH: 		return 1F;
			case LimestoneMeta.BRICK: 		return 1.5F;
			case LimestoneMeta.SMALL_BRICK: return 1.75F;
			case LimestoneMeta.THIN_BRICK:	return 2F;
			case LimestoneMeta.BORDERED: 	return 1.5F;
			case LimestoneMeta.CHISELED:	return 1.5F;
			default: return 1.5F;
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if(meta >= LimestoneMeta.PILLAR_1 && meta <= LimestoneMeta.PILLAR_3) {
			return side == 0 || side == 1? LimestoneMeta.PILLAR_1: side == 2 || side == 3? LimestoneMeta.PILLAR_2: LimestoneMeta.PILLAR_3;
		} else return (meta >= LimestoneMeta.PEDESTAL_1)? side + LimestoneMeta.PEDESTAL_1: meta;
    }
	
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= LimestoneMeta.PILLAR_1 && meta <= LimestoneMeta.PILLAR_3) {
			BlockHelper.rotateType[Block.getIdFromBlock(this)] = RotationType.LOG;
			BlockHelper.rotateVanillaBlockAlt(world, Block.getIdFromBlock(this), world.getBlockMetadata(x, y, z), x, y, z);
			return true;
		}
		
		return false;
    }
	
	public IIcon getIcon(int side, int meta) {
		if(meta == LimestoneMeta.PILLAR_1) return side == 0 || side == 1 ? icons[LimestoneMeta.PILLAR_2]: icons[LimestoneMeta.PILLAR_3];
		if(meta == LimestoneMeta.PILLAR_2) return (side == 0 || side == 1) ? icons[LimestoneMeta.PILLAR_3]: (side == 4 || side == 5)? icons[LimestoneMeta.PILLAR_1]: icons[LimestoneMeta.PILLAR_2];
		if(meta == LimestoneMeta.PILLAR_3) return (side == 4 || side == 5) ? icons[LimestoneMeta.PILLAR_2]: icons[LimestoneMeta.PILLAR_1];
		return super.getIcon(side, meta);
    }
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < LimestoneMeta.PILLAR_1) return super.getPickBlock(target, world, x, y, z);
		if(meta < LimestoneMeta.PEDESTAL_1) return new ItemStack(this, 1, LimestoneMeta.PILLAR_1);
		return new ItemStack(this, 1, LimestoneMeta.PILLAR_3);
    }
	
	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
        return false;
    }
	
	@Override
	public boolean isActive(int meta) {
		return !(meta == LimestoneMeta.PILLAR_2 || meta == LimestoneMeta.PILLAR_3 || meta > LimestoneMeta.PEDESTAL_1);
	}
	
	@Override
	public int getMetaCount() {
		return LimestoneMeta.COUNT;
	}
}
