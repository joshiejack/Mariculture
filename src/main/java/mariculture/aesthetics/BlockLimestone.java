package mariculture.aesthetics;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.blocks.BlockDecorative;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLimestone extends BlockDecorative {
	public BlockLimestone(int id) {
		super(id, Material.rock);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch(world.getBlockMetadata(x, y, z)) {
			case LimestoneMeta.SMALL_BRICK: return 1.75F;
			default: return 1.5F;
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if(meta >= LimestoneMeta.PILLAR_1 && meta <= LimestoneMeta.PILLAR_3) {
			return side == 0 || side == 1? LimestoneMeta.PILLAR_1: side == 2 || side == 3? LimestoneMeta.PILLAR_2: LimestoneMeta.PILLAR_3;
		} else if (meta >= LimestoneMeta.PEDESTAL_1) {
			if(hitY >= 0.85F) return LimestoneMeta.PEDESTAL_1;
			else if(hitY <= 0.15F) return LimestoneMeta.PEDESTAL_2;
			else return side + LimestoneMeta.PEDESTAL_1;
		} else return meta;
    }
	
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= LimestoneMeta.PILLAR_1 && meta < LimestoneMeta.PILLAR_3) {
			world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
		} else if(meta == LimestoneMeta.PILLAR_3) {
			world.setBlockMetadataWithNotify(x, y, z, LimestoneMeta.PILLAR_1, 2);
		} else if(meta >= LimestoneMeta.PEDESTAL_1 && meta < LimestoneMeta.PEDESTAL_6) {
			world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
		} else if(meta == LimestoneMeta.PEDESTAL_6) {
			world.setBlockMetadataWithNotify(x, y, z, LimestoneMeta.PEDESTAL_1, 2);
		}
		
		return false;
    }
	
	public Icon getIcon(int side, int meta) {
		if(meta == LimestoneMeta.PILLAR_1) return side == 0 || side == 1 ? icons[LimestoneMeta.PILLAR_2]: icons[LimestoneMeta.PILLAR_3];
		if(meta == LimestoneMeta.PILLAR_2) return (side == 0 || side == 1) ? icons[LimestoneMeta.PILLAR_3]: (side == 4 || side == 5)? icons[LimestoneMeta.PILLAR_1]: icons[LimestoneMeta.PILLAR_2];
		if(meta == LimestoneMeta.PILLAR_3) return (side == 4 || side == 5) ? icons[LimestoneMeta.PILLAR_2]: icons[LimestoneMeta.PILLAR_1];
		if(meta == LimestoneMeta.PEDESTAL_1) return (side == 0)? icons[LimestoneMeta.PILLAR_2]: (side == 1)? Core.ores.getIcon(0, OresMeta.LIMESTONE_CHISELED): icons[LimestoneMeta.PEDESTAL_2];
		if(meta == LimestoneMeta.PEDESTAL_2) return (side == 1)? icons[LimestoneMeta.PILLAR_2]: (side == 0)? Core.ores.getIcon(0, OresMeta.LIMESTONE_CHISELED): icons[LimestoneMeta.PEDESTAL_1];
		if(meta == LimestoneMeta.PEDESTAL_3) return (side == 3)? icons[LimestoneMeta.PILLAR_2]: (side == 2)? Core.ores.getIcon(0, OresMeta.LIMESTONE_CHISELED): (side == 0 || side == 1)? icons[LimestoneMeta.PEDESTAL_2] : (side == 5)? icons[LimestoneMeta.PEDESTAL_4]: icons[LimestoneMeta.PEDESTAL_3];
		if(meta == LimestoneMeta.PEDESTAL_4) return (side == 2)? icons[LimestoneMeta.PILLAR_2]: (side == 3)? Core.ores.getIcon(0, OresMeta.LIMESTONE_CHISELED): (side == 0 || side == 1)? icons[LimestoneMeta.PEDESTAL_1] : (side == 5)? icons[LimestoneMeta.PEDESTAL_3]: icons[LimestoneMeta.PEDESTAL_4];
		if(meta == LimestoneMeta.PEDESTAL_5) return (side == 5)? icons[LimestoneMeta.PILLAR_2]: (side == 4)? Core.ores.getIcon(0, OresMeta.LIMESTONE_CHISELED): (side == 2)? icons[LimestoneMeta.PEDESTAL_4]: icons[LimestoneMeta.PEDESTAL_3];
		if(meta == LimestoneMeta.PEDESTAL_6) return (side == 4)? icons[LimestoneMeta.PILLAR_2]: (side == 5)? Core.ores.getIcon(0, OresMeta.LIMESTONE_CHISELED): (side == 2)? icons[LimestoneMeta.PEDESTAL_3]: icons[LimestoneMeta.PEDESTAL_4];
		if(meta == LimestoneMeta.CHISELED) return (side < 2)? Core.ores.getIcon(0, OresMeta.LIMESTONE_CHISELED): super.getIcon(side, meta);
		return super.getIcon(side, meta);
    }
	
	@Override
	protected boolean canSilkHarvest() {
		return false;
    }
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta < LimestoneMeta.PILLAR_1) return super.getPickBlock(target, world, x, y, z);
		if(meta < LimestoneMeta.PEDESTAL_1) return new ItemStack(this, 1, LimestoneMeta.PILLAR_1);
		return new ItemStack(this, 1, LimestoneMeta.PEDESTAL_1);
    }
	
	@Override
	public boolean isActive(int meta) {
		return !(meta == LimestoneMeta.PILLAR_2 || meta == LimestoneMeta.PILLAR_3 || meta > LimestoneMeta.PEDESTAL_1);
	}
	
	@Override
	public int getMetaCount() {
		return LimestoneMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount() - 2];

		for (int i = 0; i < icons.length; i++) {
			String name = getName(new ItemStack(this, 1, i));
			if(name.length() > 0) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "limestone" + name.substring(0, 1).toUpperCase() + name.substring(1));
			} else {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "limestoneRaw");
			}
		}
	}
}
