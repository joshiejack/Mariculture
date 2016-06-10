package joshie.mariculture.modules.fishery.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

/** This loot condition checks the dimension **/
public class WorldDimension extends AbstractWorldLocation {
    private final int dimension;

    public WorldDimension(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean testCondition(World world, BlockPos pos) {
        return world.provider.getDimension() == dimension;
    }

    public static class Serializer extends LootCondition.Serializer<WorldDimension> {
        public Serializer() {
            super(new ResourceLocation(MODID, "dimension"), WorldDimension.class);
        }

        public void serialize(JsonObject json, WorldDimension value, JsonSerializationContext context) {
            json.addProperty("id", value.dimension);
        }

        public WorldDimension deserialize(JsonObject json, JsonDeserializationContext context) {
            return new WorldDimension(JsonUtils.getInt(json, "id", 0));
        }
    }
}
