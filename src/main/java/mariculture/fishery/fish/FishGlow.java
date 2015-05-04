package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletNether;
import static mariculture.core.lib.MCLib.glowstone;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.util.Fluids;
import mariculture.lib.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishGlow extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 57;
    }

    @Override
    public int getTemperatureTolerance() {
        return 37;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 0;
    }

    @Override
    public boolean isLavaFish() {
        return true;
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 20;
    }

    @Override
    public int getFertility() {
        return 450;
    }

    @Override
    public int getFoodConsumption() {
        return 3;
    }

    @Override
    public int getWaterRequired() {
        return 120;
    }

    @Override
    public Block getWater1() {
        return Blocks.lava;
    }

    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletNether, 15D);
        addProduct(dropletFlux, 10D);
        addProduct(glowstone, 7.5D);
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (coords.size() > 0) {
            int coordinate = world.rand.nextInt(coords.size());
            CachedCoords pos = coords.get(coordinate);
            ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[world.rand.nextInt(6)];
            Block block = world.getBlock(pos.x + dir.offsetX, pos.y + dir.offsetY, pos.z + dir.offsetZ);
            if (block == Blocks.stone) {
                world.setBlock(pos.x + dir.offsetX, pos.y + dir.offsetY, pos.z + dir.offsetZ, Blocks.glowstone);
            }
        }
    }

    @Override
    public double getFishOilVolume() {
        return 2.725D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(glowstone);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 1;
    }

    @Override
    public int getFishMealSize() {
        return 5;
    }

    @Override
    public int getFoodStat() {
        return 3;
    }

    @Override
    public float getFoodSaturation() {
        return 0.4F;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        ItemHelper.addToPlayerInventory(player, new ItemStack(glowstone));
    }

    @Override
    public int getLightValue() {
        return 15;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.isHellWorld;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isHigh(height) ? 50D : 25D;
    }
}
