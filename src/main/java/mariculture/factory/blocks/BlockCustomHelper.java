package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import mariculture.factory.tile.TileCustom;
import mariculture.lib.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCustomHelper {
    private static IIcon null_icon = Blocks.stone.getIcon(0, 0);

    public static IIcon getBlockTexture(IBlockAccess block, int x, int y, int z, int side) {
        TileCustom custom = (TileCustom) block.getTileEntity(x, y, z);
        IIcon icon = null;
        if (custom != null) {
            Block theBlock = custom.theBlocks(side);
            int theMeta = custom.theBlockMetas(side);
            int theSide = custom.theBlockSides(side);
            icon = theBlock.getIcon(theSide, theMeta);
        }

        return icon == null ? null_icon : icon;
    }

    public static void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        if (stack.hasTagCompound()) {
            ((TileCustom) world.getTileEntity(x, y, z)).readData(stack.stackTagCompound);
            ((TileCustom) world.getTileEntity(x, y, z)).updateHardness();
            ((TileCustom) world.getTileEntity(x, y, z)).updateResistance();
        }
    }

    public static boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        TileCustom tile = (TileCustom) world.getTileEntity(x, y, z);
        if (tile != null) if (axis == ForgeDirection.EAST || axis == ForgeDirection.WEST || axis == ForgeDirection.NORTH || axis == ForgeDirection.SOUTH) {
            Block[] blocks = new Block[] { tile.theBlocks(0), tile.theBlocks(1), tile.theBlocks(4), tile.theBlocks(5), tile.theBlocks(3), tile.theBlocks(2) };
            int[] metas = new int[] { tile.theBlockMetas(0), tile.theBlockMetas(1), tile.theBlockMetas(4), tile.theBlockMetas(5), tile.theBlockMetas(3), tile.theBlockMetas(2) };
            int[] sides = new int[] { tile.theBlockSides(0), tile.theBlockSides(1), tile.theBlockSides(4), tile.theBlockSides(5), tile.theBlockSides(3), tile.theBlockSides(2) };
            tile.set(blocks, metas, sides, tile.name());
            return true;
        } else if (axis == ForgeDirection.DOWN || axis == ForgeDirection.UP) {
            Block[] blocks = new Block[] { tile.theBlocks(1), tile.theBlocks(0), tile.theBlocks(2), tile.theBlocks(3), tile.theBlocks(4), tile.theBlocks(5) };
            int[] metas = new int[] { tile.theBlockMetas(1), tile.theBlockMetas(0), tile.theBlockMetas(2), tile.theBlockMetas(3), tile.theBlockMetas(4), tile.theBlockMetas(5) };
            int[] sides = new int[] { tile.theBlockSides(1), tile.theBlockSides(0), tile.theBlockSides(2), tile.theBlockSides(3), tile.theBlockSides(4), tile.theBlockSides(5) };
            tile.set(blocks, metas, sides, tile.name());
            return true;
        }

        return false;
    }

    public static float getBlockHardness(World world, int x, int y, int z) {
        return world.getTileEntity(x, y, z) != null ? ((TileCustom) world.getTileEntity(x, y, z)).getHardness() : 1F;
    }

    public static float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        return world.getTileEntity(x, y, z) != null ? ((TileCustom) world.getTileEntity(x, y, z)).getResistance() : 1F;
    }

    public static boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z, int plan) {
        if (!player.capabilities.isCreativeMode) {
            TileCustom tile = (TileCustom) world.getTileEntity(x, y, z);
            ItemStack drop = PlansMeta.getBlockStack(plan);
            if (!drop.hasTagCompound()) {
                drop.setTagCompound(new NBTTagCompound());
            }

            if (tile != null) {
                tile.writeData(drop.stackTagCompound);
            }

            ItemHelper.spawnItem(world, x, y, z, drop);
        }

        return world.setBlockToAir(x, y, z);
    }

    public static ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, int plan) {
        TileCustom tile = (TileCustom) world.getTileEntity(x, y, z);
        ItemStack drop = PlansMeta.getBlockStack(plan);
        if (!drop.hasTagCompound()) {
            drop.setTagCompound(new NBTTagCompound());
        }

        if (tile != null) {
            tile.writeData(drop.stackTagCompound);
        }

        return drop;
    }
}
