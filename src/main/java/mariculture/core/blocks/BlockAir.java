package mariculture.core.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockDecorative;
import mariculture.core.lib.AirMeta;
import mariculture.core.util.Rand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAir extends BlockDecorative {
    public static ArrayList<Block> flammables = new ArrayList();
    public IIcon theIcon;

    static {
        flammables.add(Blocks.fire);
        flammables.add(Blocks.torch);
        flammables.add(Blocks.lava);
    }

    public BlockAir() {
        super(Material.air);
    }

    @Override
    public String getToolType(int meta) {
        return null;
    }

    @Override
    public int getToolLevel(int meta) {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isAir(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public boolean trySetFire(World world, int x, int y, int z) {
        if (world instanceof World) if (world.isAirBlock(x, y + 1, z)) {
            world.setBlock(x, y + 1, z, Blocks.fire);
        } else if (world.isAirBlock(x, y - 1, z)) {
            world.setBlock(x, y - 1, z, Blocks.fire);
        } else if (world.isAirBlock(x - 1, y, z)) {
            world.setBlock(x - 1, y, z, Blocks.fire);
        } else if (world.isAirBlock(x, y, z - 1)) {
            world.setBlock(x, y, z - 1, Blocks.fire);
        } else if (world.isAirBlock(x + 1, y, z)) {
            world.setBlock(x + 1, y, z, Blocks.fire);
        } else if (world.isAirBlock(x, y, z + 1)) {
            world.setBlock(x, y, z + 1, Blocks.fire);
        }

        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    Block aBlock = world.getBlock(x + i, y + j, z + k);
                    if (flammables.contains(aBlock)) {
                        trySetFire(world, x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean canCollideCheck(int meta, boolean par2) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return isNaturalGas(world, x, y, z) ? false : side == 1 ? true : super.shouldSideBeRendered(world, x, y, z, side);
    }

    private boolean isNaturalGas(IBlockAccess world, int x, int y, int z) {
        return world.getBlock(x, y, z) == Core.air && world.getBlockMetadata(x, y, z) == AirMeta.NATURAL_GAS;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5, 2, true));
            player.addPotionEffect(new PotionEffect(Potion.weakness.id, 5, 0, true));
            if (Rand.nextInt(500)) {
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 60, 0, true));
                player.addPotionEffect(new PotionEffect(Potion.weakness.id, 40, 1, true));
            } else if (Rand.nextInt(1000)) {
                player.addPotionEffect(new PotionEffect(Potion.poison.id, 30, 0, true));
            }
        }
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return true;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 300;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 1000;
    }

    @Override
    public int getMetaCount() {
        return AirMeta.COUNT;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return theIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        theIcon = iconRegister.registerIcon(Mariculture.modid + ":air");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creative, List list) {
        return;
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return null;
    }

    @Override
    public boolean isActive(int meta) {
        return true;
    }
}
