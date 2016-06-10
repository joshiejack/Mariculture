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

/** This loot condition checks the height of the bobber **/
public class WorldHeight extends AbstractWorldLocation {
    private final int minY;
    private final int maxY;

    public WorldHeight(int minY, int maxY) {
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public boolean testCondition(World world, BlockPos pos) {
        return pos.getY() >= minY && pos.getY() <= maxY;
    }

    public static class Serializer extends LootCondition.Serializer<WorldHeight> {
        public Serializer() {
            super(new ResourceLocation(MODID, "height"), WorldHeight.class);
        }

        public void serialize(JsonObject json, WorldHeight value, JsonSerializationContext context) {
            json.addProperty("min", value.minY);
            json.addProperty("max", value.maxY);
        }

        public WorldHeight deserialize(JsonObject json, JsonDeserializationContext context) {
            return new WorldHeight(JsonUtils.getInt(json, "min", 0), JsonUtils.getInt(json, "max", 256));
        }
    }
}
