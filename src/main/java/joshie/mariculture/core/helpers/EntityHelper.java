package joshie.mariculture.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.List;

public class EntityHelper {
    /** Returns if the entity is covered in enough water
     * @param entity    the entity
     * @return if it's considered as in water */
    public static boolean isInWater(Entity entity) {
        double eyes = entity.posY + (double)entity.getEyeHeight() - 0.65;
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_float(MathHelper.floor_double(eyes));
        int k = MathHelper.floor_double(entity.posZ);

        BlockPos pos = new BlockPos(i, j, k);
        IBlockState state = entity.worldObj.getBlockState(pos);
        Block block = state.getBlock();

        if (state.getMaterial() == Material.WATER) {
            double filled = 1.0f; //If it's not a liquid assume it's a solid block
            if (block instanceof IFluidBlock) {
                filled = ((IFluidBlock)block).getFilledPercentage(entity.worldObj, pos);
            } else if (block instanceof BlockLiquid) {
                filled = BlockLiquid.getLiquidHeightPercent(block.getMetaFromState(state));
            }

            if (filled < 0) {
                filled *= -1;
                return eyes > pos.getY() + 1 + (1 - filled);
            } else {
                return eyes < pos.getY() + 1 + filled;
            }
        } else return false;
    }

    /** Gets the facing based on the way the entity is looking
     * @param entity    the entity
     * @return the facing*/
    public static EnumFacing getFacingFromEntity(EntityLivingBase entity) {
        int direction = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        EnumFacing dir = EnumFacing.NORTH;
        if (direction == 0) return EnumFacing.NORTH;
        if (direction == 1) return EnumFacing.EAST;
        if (direction == 2) return EnumFacing.SOUTH;
        if (direction == 3) return EnumFacing.WEST;
        return dir;
    }

    /** Returns true if the player is wearing thie armour **/
    public static boolean hasArmor(EntityPlayer player, EntityEquipmentSlot slot, Item item) {
        ItemStack stack = player.getItemStackFromSlot(slot);
        return stack != null && stack.getItem() == item;
    }

    /** Returns the entities around this area **/
    public static <T extends Entity> List<T> getEntities(Class<? extends T> t, World world, BlockPos pos, double size, double ySize) {
        return world.getEntitiesWithinAABB(t, new AxisAlignedBB(pos.getX() - 0.5F, pos.getY() - 0.5F, pos.getZ() - 0.5F, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F).expand(size, ySize, size));
    }
}
