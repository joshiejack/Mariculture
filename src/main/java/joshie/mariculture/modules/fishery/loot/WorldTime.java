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

/** This loot condition checks the world time **/
public class WorldTime extends AbstractWorldLocation {
    private final int minTime;
    private final int maxTime;

    public WorldTime(int minTime, int maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    @Override
    public boolean testCondition(World world, BlockPos pos) {
        return world.getWorldTime() >= minTime && world.getWorldTime() <= maxTime;
    }

    public static class Serializer extends LootCondition.Serializer<WorldTime> {
        public Serializer() {
            super(new ResourceLocation(MODID, "time"), WorldTime.class);
        }

        public void serialize(JsonObject json, WorldTime value, JsonSerializationContext context) {
            json.addProperty("min", value.minTime);
            json.addProperty("max", value.maxTime);
        }

        public WorldTime deserialize(JsonObject json, JsonDeserializationContext context) {
            return new WorldTime(JsonUtils.getInt(json, "min", 0), JsonUtils.getInt(json, "max", 23999));
        }
    }
}
