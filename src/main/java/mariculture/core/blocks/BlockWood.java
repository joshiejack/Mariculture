package mariculture.core.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mariculture.Mariculture;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.helpers.cofh.BlockHelper.RotationType;
import mariculture.core.lib.Modules;
import mariculture.core.lib.WoodMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockWood extends BlockDecorative {
	public Icon top;
	public Icon side;
	
	public BlockWood(int i) {
		super(i, Material.wood);
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
	
	        switch (side)
	        {
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
			BlockHelper.rotateType[this.blockID] = RotationType.LOG;
			BlockHelper.rotateVanillaBlockAlt(world, this.blockID, world.getBlockMetadata(x, y, z), x, y, z);
			return true;
		}
		
		return false;
    }
	
	public int damageDropped(int meta) {
		return (meta >= WoodMeta.POLISHED_LOG)? meta & 3: meta;
    }
	
	public Icon getIcon(int side, int meta) {
		if(meta >= WoodMeta.POLISHED_LOG) {
	        int k = meta & 12;
	        int l = meta & 3;
	        return k == 0 && (side == 1 || side == 0) ? this.getEndIcon(l) : (k == 4 && (side == 5 || side == 4) ? this.getEndIcon(l) : (k == 8 && (side == 2 || side == 3) ? this.getEndIcon(l) : this.getSideIcon(l)));
		}
		
		return super .getIcon(side, meta);
    }
	
	protected Icon getEndIcon(int meta) {
        return this.top;
    }
	
	protected Icon getSideIcon(int meta) {
        return this.side;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);
		top = iconRegister.registerIcon(Mariculture.modid + ":polishedLogTop");
		side = iconRegister.registerIcon(Mariculture.modid + ":polishedLogSide");
	}
}
