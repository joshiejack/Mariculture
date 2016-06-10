package joshie.mariculture.modules.fishery.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.api.fishing.Fishing.Salinity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

/** This loot condition checks the salinity of the biome **/
public class SalinityType extends AbstractWorldLocation {
    private final Salinity salinity;

    public SalinityType(Salinity salinity) {
        this.salinity = salinity;
    }

    @Override
    public boolean testCondition(World world, BlockPos pos) {
        return MaricultureAPI.fishing.getSalinityForBiome(world.getBiome(pos)) == salinity;
    }

    public static class Serializer extends LootCondition.Serializer<SalinityType> {
        public Serializer() {
            super(new ResourceLocation(MODID, "salinity"), SalinityType.class);
        }

        public void serialize(JsonObject json, SalinityType value, JsonSerializationContext context) {
            json.addProperty("type", value.salinity.name().toLowerCase());
        }

        public SalinityType deserialize(JsonObject json, JsonDeserializationContext context) {
            return new SalinityType(getType(JsonUtils.getString(json, "salinity", "saline")));
        }

        private Salinity getType(String string) {
            return Salinity.valueOf(string.toUpperCase());
        }
    }
}
