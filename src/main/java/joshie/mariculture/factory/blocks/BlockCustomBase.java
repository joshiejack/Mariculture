package joshie.mariculture.factory.blocks;

import java.util.Random;

import joshie.mariculture.core.blocks.base.BlockFunctional;
import joshie.mariculture.factory.tile.TileCustom;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomBase extends BlockFunctional {
    BlockCustomBase(Material material) {
        super(material);
        setCreativeTab(null);
    }

    @Override
    public String getToolType(int meta) {
        return "axe";
    }

    @Override
    public int getToolLevel(int meta) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.stone.getIcon(side, meta);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
        return BlockCustomHelper.getBlockTexture(block, x, y, z, side);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        BlockCustomHelper.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        return BlockCustomHelper.rotateBlock(world, x, y, z, axis);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        return BlockCustomHelper.getBlockHardness(world, x, y, z);
    }

    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        return BlockCustomHelper.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public int quantityDropped(Random rand) {
        return 0;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        return BlockCustomHelper.removeBlockByPlayer(world, player, x, y, z, getID());
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return BlockCustomHelper.getPickBlock(target, world, x, y, z, getID());
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileCustom();
    }

    public int getID() {
        return 0;
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        //
    }

    @Override
    public int getMetaCount() {
        return 1;
    }

    @Override
    public boolean isActive(int meta) {
        return true;
    }

    @Override
    public boolean isValidTab(CreativeTabs tab, int meta) {
        return false;
    }

    @Override
    public Class<? extends ItemBlock> getItemClass() {
        return BlockItemCustom.class;
    }
}
