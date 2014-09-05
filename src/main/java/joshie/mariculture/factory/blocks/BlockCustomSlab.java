package joshie.mariculture.factory.blocks;

import java.util.List;
import java.util.Random;

import joshie.lib.util.IHasMetaBlock;
import joshie.mariculture.core.lib.PlansMeta;
import joshie.mariculture.factory.Factory;
import joshie.mariculture.factory.tile.TileCustom;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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

public class BlockCustomSlab extends BlockSlab implements IHasMetaBlock {
    public BlockCustomSlab(boolean isDouble) {
        super(isDouble, Material.piston);
        setLightOpacity(0);
        setCreativeTab(null);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return world.getBlock(x, y, z) == Factory.customSlabsDouble ? true : false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.stone.getIcon(side, meta);
    }

    @Override
    protected ItemStack createStackedBlock(int meta) {
        return new ItemStack(Factory.customSlabs, 2, meta & 7);
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

    private int getID() {
        return PlansMeta.SLABS;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileCustom();
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        //
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        return;
    }

    @Override
    public int getMetaCount() {
        return 1;
    }

    @Override
    public String func_150002_b(int var1) {
        return null;
    }

    @Override
    public Class<? extends ItemBlock> getItemClass() {
        return BlockItemCustom.class;
    }
}