package mariculture.world.decorate;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAncientSand extends WorldGenerator {
    Block block;
    private int radius, meta;

    public WorldGenAncientSand(Block block, int meta, int radius) {
        this.block = block;
        this.meta = meta;
        this.radius = radius;
    }

    @Override
    public boolean generate(World world, Random par2Random, int par3, int par4, int par5) {
        if (world.getBlock(par3, par4, par5).getMaterial() != Material.water) return false;
        else {
            int l = par2Random.nextInt(radius - 2) + 2;
            byte b0 = 2;

            for (int i1 = par3 - l; i1 <= par3 + l; ++i1) {
                for (int j1 = par5 - l; j1 <= par5 + l; ++j1) {
                    int k1 = i1 - par3;
                    int l1 = j1 - par5;

                    if (k1 * k1 + l1 * l1 <= l * l) {
                        for (int i2 = par4 - b0; i2 <= par4 + b0; ++i2) {
                            Block block = world.getBlock(i1, i2, j1);

                            if (block == Blocks.dirt || block == Blocks.grass || block == Blocks.sand || block == Blocks.gravel) {
                                world.setBlock(i1, i2, j1, this.block, meta, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
