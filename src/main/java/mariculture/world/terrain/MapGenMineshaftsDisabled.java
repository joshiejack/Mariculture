package mariculture.world.terrain;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.StructureMineshaftStart;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenMineshaftsDisabled extends MapGenMineshaft {
    private double field_82673_e = 0.01D;

    public MapGenMineshaftsDisabled() {}

    @Override
    public String func_143025_a() {
        return "Mineshaft";
    }

    public MapGenMineshaftsDisabled(Map map) {
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            if (((String) entry.getKey()).equals("chance")) {
                field_82673_e = MathHelper.parseDoubleWithDefault((String) entry.getValue(), field_82673_e);
            }
        }
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        if (rand.nextDouble() < field_82673_e && rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ))) return MaricultureHandlers.environment.getSalinity(worldObj, chunkX * 16, chunkZ * 16) != Salinity.SALINE;
        else return false;
    }

    @Override
    protected StructureStart getStructureStart(int par1, int par2) {
        return new StructureMineshaftStart(worldObj, rand, par1, par2);
    }
}
