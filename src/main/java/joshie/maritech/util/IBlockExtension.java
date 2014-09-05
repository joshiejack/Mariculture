package joshie.maritech.util;

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

public interface IBlockExtension extends IItemExtension {
    public String getName(int meta, String name);
    public boolean isActive(int meta, boolean isActive);
    boolean isValidTab(CreativeTabs tab, int meta, boolean isValid);
    public String getToolType(int meta, String tooltype);
    public int getToolLevel(int meta, int level);
    public float getHardness(int meta, float hardness);
    public TileEntity getTileEntity(int meta, TileEntity tile);
    public boolean onRightClickBlock(World world, int x, int y, int z, EntityPlayer player);
    
    public void onTilePlaced(ItemStack stack, TileEntity tile, EntityLivingBase entity, int direction);
    public boolean onBlockBroken(int meta, World world, int x, int y, int z);
}
