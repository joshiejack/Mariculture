package joshie.mariculture.core.helpers.cofh;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Contains various helper functions to assist with {@link Block} and Block-related manipulation and interaction.
 * 
 * @author King Lemming
 * 
 */
public final class BlockHelper {

    private BlockHelper() {

    }

    public static byte[] rotateType = new byte[4096];
    public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
    public static final byte[] SIDE_LEFT = { 4, 5, 5, 4, 2, 3 };
    public static final byte[] SIDE_RIGHT = { 5, 4, 4, 5, 3, 2 };
    public static final byte[] SIDE_OPPOSITE = { 1, 0, 3, 2, 5, 4 };

    public static final class RotationType {
        public static final int PREVENT = -1;
        public static final int FOUR_WAY = 1;
        public static final int SIX_WAY = 2;
        public static final int RAIL = 3;
        public static final int PUMPKIN = 4;
        public static final int STAIRS = 5;
        public static final int REDSTONE = 6;
        public static final int LOG = 7;
        public static final int SLAB = 8;
        public static final int CHEST = 9;
        public static final int LEVER = 10;
        public static final int SIGN = 11;
    }

    static {
        rotateType[Block.getIdFromBlock(Blocks.log)] = RotationType.LOG;
        rotateType[Block.getIdFromBlock(Blocks.log2)] = RotationType.LOG;
        rotateType[Block.getIdFromBlock(Blocks.dispenser)] = RotationType.SIX_WAY;
        rotateType[Block.getIdFromBlock(Blocks.bed)] = RotationType.PREVENT;
        rotateType[Block.getIdFromBlock(Blocks.golden_rail)] = RotationType.RAIL;
        rotateType[Block.getIdFromBlock(Blocks.detector_rail)] = RotationType.RAIL;
        rotateType[Block.getIdFromBlock(Blocks.sticky_piston)] = RotationType.SIX_WAY;
        rotateType[Block.getIdFromBlock(Blocks.piston)] = RotationType.SIX_WAY;
        rotateType[Block.getIdFromBlock(Blocks.stone_slab)] = RotationType.SLAB;
        rotateType[Block.getIdFromBlock(Blocks.oak_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.chest)] = RotationType.CHEST;
        rotateType[Block.getIdFromBlock(Blocks.furnace)] = RotationType.FOUR_WAY;
        rotateType[Block.getIdFromBlock(Blocks.standing_sign)] = RotationType.SIGN;
        rotateType[Block.getIdFromBlock(Blocks.rail)] = RotationType.RAIL;
        rotateType[Block.getIdFromBlock(Blocks.stone_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.lever)] = RotationType.LEVER;
        rotateType[Block.getIdFromBlock(Blocks.pumpkin)] = RotationType.PUMPKIN;
        rotateType[Block.getIdFromBlock(Blocks.lit_pumpkin)] = RotationType.PUMPKIN;
        rotateType[Block.getIdFromBlock(Blocks.powered_repeater)] = RotationType.REDSTONE;
        rotateType[Block.getIdFromBlock(Blocks.unpowered_repeater)] = RotationType.REDSTONE;
        rotateType[Block.getIdFromBlock(Blocks.brick_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.stone_brick_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.nether_brick_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.wooden_slab)] = RotationType.SLAB;
        rotateType[Block.getIdFromBlock(Blocks.sandstone_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.ender_chest)] = RotationType.FOUR_WAY;
        rotateType[Block.getIdFromBlock(Blocks.spruce_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.birch_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.jungle_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.quartz_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.hopper)] = RotationType.SIX_WAY;
        rotateType[Block.getIdFromBlock(Blocks.activator_rail)] = RotationType.RAIL;
        rotateType[Block.getIdFromBlock(Blocks.dropper)] = RotationType.SIX_WAY;
        rotateType[Block.getIdFromBlock(Blocks.acacia_stairs)] = RotationType.STAIRS;
        rotateType[Block.getIdFromBlock(Blocks.dark_oak_stairs)] = RotationType.STAIRS;
    }

    private static MovingObjectPosition getCurrentMovingObjectPosition(EntityPlayer player) {
        double distance = player.capabilities.isCreativeMode ? 5.0F : 4.5F;
        Vec3 posVec = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
        Vec3 lookVec = player.getLook(1);
        posVec.yCoord += player.getEyeHeight();
        lookVec = posVec.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
        return player.worldObj.rayTraceBlocks(posVec, lookVec);
    }

    /* Safe Tile Entity Retrieval */
    public static TileEntity getAdjacentTileEntity(World world, int x, int y, int z, ForgeDirection dir) {

        return world == null ? null : world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
    }

    public static TileEntity getAdjacentTileEntity(TileEntity refTile, ForgeDirection dir) {

        return refTile == null ? null : getAdjacentTileEntity(refTile.getWorldObj(), refTile.xCoord, refTile.yCoord, refTile.zCoord, dir);
    }

    /* COORDINATE TRANSFORM */
    public static int[] getAdjacentCoordinatesForSide(int x, int y, int z, int side) {

        return new int[] { x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2] };
    }

    /* BLOCK ROTATION */
    public static boolean canRotate(Block block) {
        return rotateType[Block.getIdFromBlock(block)] != 0;
    }

    public static int rotateVanillaBlock(World world, int bId, int bMeta, int x, int y, int z) {

        switch (rotateType[bId]) {
            case RotationType.FOUR_WAY:
                return SIDE_LEFT[bMeta];
            case RotationType.SIX_WAY:
                if (bMeta < 6) return ++bMeta % 6;
                return bMeta;
            case RotationType.RAIL:
                if (bMeta < 2) return ++bMeta % 2;
                return bMeta;
            case RotationType.PUMPKIN:
                return ++bMeta % 4;
            case RotationType.STAIRS:
                return ++bMeta % 8;
            case RotationType.REDSTONE:
                int upper = bMeta & 0xC;
                int lower = bMeta & 0x3;
                return upper + ++lower % 4;
            case RotationType.LOG:
                return (bMeta + 4) % 12;
            case RotationType.SLAB:
                return (bMeta + 8) % 16;
            case RotationType.CHEST:
                int coords[] = new int[3];
                for (int i = 2; i < 6; i++) {
                    coords = getAdjacentCoordinatesForSide(x, y, z, i);
                    if (world.getBlock(coords[0], coords[1], coords[2]) == Blocks.chest) {
                        world.setBlockMetadataWithNotify(coords[0], coords[1], coords[2], SIDE_OPPOSITE[bMeta], 1);
                        return SIDE_OPPOSITE[bMeta];
                    }
                }
                return SIDE_LEFT[bMeta];
            case RotationType.LEVER:
                int shift = 0;
                if (bMeta > 7) {
                    bMeta -= 8;
                    shift = 8;
                }
                if (bMeta == 5) return 6 + shift;
                else if (bMeta == 6) return 5 + shift;
                else if (bMeta == 7) return 0 + shift;
                else if (bMeta == 0) return 7 + shift;
                return bMeta + shift;
            case RotationType.SIGN:
                return ++bMeta % 16;
            case RotationType.PREVENT:
            default:
                return bMeta;
        }
    }

    public static int rotateVanillaBlockAlt(World world, int bId, int bMeta, int x, int y, int z) {

        switch (rotateType[bId]) {
            case RotationType.FOUR_WAY:
                return SIDE_RIGHT[bMeta];
            case RotationType.SIX_WAY:
                if (bMeta < 6) return (bMeta + 5) % 6;
                return bMeta;
            case RotationType.RAIL:
                if (bMeta < 2) return ++bMeta % 2;
                return bMeta;
            case RotationType.PUMPKIN:
                return (bMeta + 3) % 4;
            case RotationType.STAIRS:
                return (bMeta + 7) % 8;
            case RotationType.REDSTONE:
                int upper = bMeta & 0xC;
                int lower = bMeta & 0x3;
                return upper + (lower + 3) % 4;
            case RotationType.LOG:
                return (bMeta + 8) % 12;
            case RotationType.SLAB:
                return (bMeta + 8) % 16;
            case RotationType.CHEST:
                int coords[] = new int[3];
                for (int i = 2; i < 6; i++) {
                    coords = getAdjacentCoordinatesForSide(x, y, z, i);
                    if (world.getBlock(coords[0], coords[1], coords[2]) == Blocks.chest) {
                        world.setBlockMetadataWithNotify(coords[0], coords[1], coords[2], SIDE_OPPOSITE[bMeta], 1);
                        return SIDE_OPPOSITE[bMeta];
                    }
                }
                return SIDE_RIGHT[bMeta];
            case RotationType.LEVER:
                int shift = 0;
                if (bMeta > 7) {
                    bMeta -= 8;
                    shift = 8;
                }
                if (bMeta == 5) return 6 + shift;
                else if (bMeta == 6) return 5 + shift;
                else if (bMeta == 7) return 0 + shift;
                else if (bMeta == 0) return 7 + shift;
            case RotationType.SIGN:
                return ++bMeta % 16;
            case RotationType.PREVENT:
            default:
                return bMeta;
        }
    }
}
