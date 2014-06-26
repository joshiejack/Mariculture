package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.dropletEnder;
import static mariculture.core.lib.ItemLib.enderPearl;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.util.Rand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class FishEnder extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 0, 5 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH };
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 25;
    }

    @Override
    public int getFertility() {
        return 75;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 140;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEnder, 7.5D);
        addProduct(enderPearl, 5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.450D;
    }

    @Override
    public int getFishMealSize() {
        return 2;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        if (!world.isRemote) {
            world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
            int x = (int) (player.posX + Rand.rand.nextInt(64) - 32);
            int z = (int) (player.posZ + Rand.rand.nextInt(64) - 32);
            if (BlockHelper.chunkExists(world, x, z)) {
                int y = world.getTopSolidOrLiquidBlock(x, z);

                if (world.getBlock(x, y, z).getMaterial() != Material.lava) {
                    world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
                    player.setPositionAndUpdate(x, y, z);
                    world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
                }
            }
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.dimensionId == 1;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 66D;
    }
}
