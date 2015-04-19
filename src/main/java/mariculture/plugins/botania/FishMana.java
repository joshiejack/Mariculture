package mariculture.plugins.botania;

import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletMagic;
import static mariculture.core.lib.MCLib.dropletRegen;

import java.util.ArrayList;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaCollector;
import vazkii.botania.common.item.ModItems;

public class FishMana extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 10;
    }

    @Override
    public int getTemperatureTolerance() {
        return 50;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.BRACKISH;
    }

    @Override
    public int getSalinityTolerance() {
        return 2;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 15;
    }

    @Override
    public int getFertility() {
        return 3000;
    }

    @Override
    public int getBaseProductivity() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 100;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletMagic, 20D);
        addProduct(dropletRegen, 15D);
        addProduct(dropletAir, 13D);
        addProduct(dropletAqua, 10D);
        addProduct(new ItemStack(ModItems.manaResource, 1, 10), 5D);
    }
    
    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        TileEntity tile = world.getTileEntity(x, y + 1, z);
        if (tile instanceof IManaCollector) {
            IManaCollector collector = (IManaCollector) tile;
            if(!collector.isFull()) {
                int manaval = Math.min(5000, collector.getMaxMana() - collector.getCurrentMana());
                collector.recieveMana(manaval);
            }
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 15D;
    }
}
