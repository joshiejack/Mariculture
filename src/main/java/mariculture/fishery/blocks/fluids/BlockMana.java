package mariculture.fishery.blocks.fluids;

import mariculture.core.blocks.base.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockMana extends BlockFluid {
    public BlockMana(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 6;
        quantaPerBlockFloat = 6;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityPlayer && !world.isRemote) {
            EntityPlayer player = (EntityPlayer) entity;
            ChunkCoordinates cord = player.getBedLocation(world.provider.dimensionId);
            if(cord != null && player.getDistance(cord.posX, cord.posY, cord.posZ) >= 8) {
                world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
                player.setPositionAndUpdate(cord.posX, cord.posY, cord.posZ);
                world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
            }
        }
    }
}
