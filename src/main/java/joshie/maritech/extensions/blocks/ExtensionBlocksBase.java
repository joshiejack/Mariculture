package joshie.maritech.extensions.blocks;

import joshie.maritech.extensions.items.ExtensionItemsBase;
import joshie.maritech.util.IBlockExtension;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExtensionBlocksBase extends ExtensionItemsBase implements IBlockExtension {
    @Override
    public boolean isActive(int meta, boolean isActive) {
        return isActive;
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta, boolean isValid) {
        return isValid;
    }

    @Override
    public String getToolType(int meta, String tooltype) {
        return tooltype;
    }

    @Override
    public int getToolLevel(int meta, int level) {
        return level;
    }

    @Override
    public float getHardness(int meta, float hardness) {
        return hardness;
    }

    @Override
    public TileEntity getTileEntity(int meta, TileEntity tile) {
        return tile;
    }

    @Override
    public boolean onRightClickBlock(World world, int x, int y, int z, EntityPlayer player) {
        return false;
    }

    @Override
    public void onTilePlaced(ItemStack stack, TileEntity tile, EntityLivingBase entity, int direction) {
        
    }

    @Override
    public boolean onBlockBroken(int meta, World world, int x, int y, int z) {
        return false;
    }
}
