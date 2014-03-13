package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.helpers.cofh.BlockHelper.RotationType;
import mariculture.core.lib.Modules;
import mariculture.core.lib.WoodMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWood extends BlockDecorative {
	public IIcon top;
	public IIcon side;
	
	public BlockWood() {
		super(Material.wood);
	}
	
	@Override
	public String getToolType(int meta) {
		return "axe";
	}

	@Override
	public int getToolLevel(int meta) {
		switch(meta) {
			case WoodMeta.BASE_WOOD: 		return 1;
			case WoodMeta.POLISHED_LOG: 	return 2;
			case WoodMeta.POLISHED_PLANK: 	return 2;
			default:						return 0;
		}
	}
	
	@Override
	public boolean isActive(int meta) {
		switch(meta) {
		case WoodMeta.BASE_WOOD:
			return true;
		case WoodMeta.POLISHED_LOG:
			return Modules.fishery.isActive();
		case WoodMeta.POLISHED_PLANK:
			return Modules.fishery.isActive();
		default:
			return true;
		}
	}
	
	@Override
	public int getMetaCount() {
		return WoodMeta.COUNT;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if(meta >= WoodMeta.POLISHED_LOG) {
	        int j1 = meta & 3;
	        byte b0 = 0;
	
	        switch (side) {
	            case 0:
	            case 1:
	                b0 = 0;
	                break;
	            case 2:
	            case 3:
	                b0 = 8;
	                break;
	            case 4:
	            case 5:
	                b0 = 4;
	        }
	        
	        return j1 | b0;
		}
		
		return meta;
    }
	
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		if(world.getBlockMetadata(x, y, z) >= WoodMeta.POLISHED_LOG) {
			BlockHelper.rotateType[Block.getIdFromBlock(this)] = RotationType.LOG;
			BlockHelper.rotateVanillaBlockAlt(world, Block.getIdFromBlock(this), world.getBlockMetadata(x, y, z), x, y, z);
			return true;
		}
		
		return false;
    }
	
	public int damageDropped(int meta) {
		return (meta >= WoodMeta.POLISHED_LOG)? meta & 3: meta;
    }
	
	public IIcon getIcon(int side, int meta) {
		if(meta >= WoodMeta.POLISHED_LOG) {
	        int k = meta & 12;
	        int l = meta & 3;
	        return k == 0 && (side == 1 || side == 0) ? this.getEndIcon(l) : (k == 4 && (side == 5 || side == 4) ? this.getEndIcon(l) : (k == 8 && (side == 2 || side == 3) ? this.getEndIcon(l) : this.getSideIcon(l)));
		}
		
		return super .getIcon(side, meta);
    }
	
	protected IIcon getEndIcon(int meta) {
        return this.top;
    }
	
	protected IIcon getSideIcon(int meta) {
        return this.side;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		top = iconRegister.registerIcon(Mariculture.modid + ":polishedLogTop");
		side = iconRegister.registerIcon(Mariculture.modid + ":polishedLogSide");
	}
}
