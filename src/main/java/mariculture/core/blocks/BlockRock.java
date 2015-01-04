package mariculture.core.blocks;

import mariculture.core.blocks.base.BlockMCBaseMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RockMeta;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockRock extends BlockMCBaseMeta {
    public BlockRock() {
        super(Material.rock);
    }

    @Override
    public String getToolType(int meta) {
        return "pickaxe";
    }

    @Override
    public int getToolLevel(int meta) {
        switch (meta) {
            case RockMeta.CORAL_ROCK:
                return 1;
            case RockMeta.COPPER:
                return 0;
            case RockMeta.BAUXITE:
                return 1;
            case RockMeta.RUTILE:
                return 2;
            case RockMeta.BASE_BRICK:
                return 2;
            default:
                return 1;
        }
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case RockMeta.CORAL_ROCK:
                return 5F;
            case RockMeta.COPPER:
                return 2F;
            case RockMeta.BAUXITE:
                return 3F;
            case RockMeta.RUTILE:
                return 8F;
            case RockMeta.BASE_BRICK:
                return 10F;
            default:
                return 4F;
        }
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        switch (world.getBlockMetadata(x, y, z)) {
            case RockMeta.CORAL_ROCK:
                return 15F;
            case RockMeta.COPPER:
                return 3F;
            case RockMeta.BAUXITE:
                return 3F;
            case RockMeta.RUTILE:
                return 1F;
            case RockMeta.BASE_BRICK:
                return 30F;
            default:
                return 5F;
        }
    }

    @Override
    public boolean isActive(int meta) {
        return meta == RockMeta.CORAL_ROCK ? Modules.isActive(Modules.worldplus) : true;
    }

    @Override
    public int getMetaCount() {
        return RockMeta.COUNT;
    }
}
