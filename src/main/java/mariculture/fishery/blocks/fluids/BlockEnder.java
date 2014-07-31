package mariculture.fishery.blocks.fluids;

import mariculture.core.blocks.base.BlockFluid;
import mariculture.core.helpers.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockEnder extends BlockFluid {
    public BlockEnder(Fluid fluid, Material material) {
        super(fluid, material);
        quantaPerBlock = 6;
        quantaPerBlockFloat = 6;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!world.isRemote) {
            world.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.0F, 1.0F);
            int x2 = (int) (entity.posX + world.rand.nextInt(64) - 32);
            int z2 = (int) (entity.posZ + world.rand.nextInt(64) - 32);
            if (BlockHelper.chunkExists(world, x2, z2)) {
                int y2 = world.getTopSolidOrLiquidBlock(x2, z2);

                if (world.getBlock(x2, y2, z2).getMaterial() != Material.lava) {
                    world.playSoundEffect(x2, y2, z2, "mob.endermen.portal", 1.0F, 1.0F);
                    entity.setPosition(x2, y2, z2);
                    world.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.0F, 1.0F);
                }
            }
        }
    }
}
