package joshie.mariculture.modules.fishery.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import joshie.mariculture.api.fishing.Fishing.Size;
import joshie.mariculture.modules.fishery.FishingAPI;
import joshie.mariculture.modules.fishery.rod.FishingRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Locale;
import java.util.Random;

import static joshie.mariculture.core.lib.MaricultureInfo.MODID;

/** This loot condition checks the strength of the item the player is holding in their main hand **/
public class FishSize implements LootCondition {
    private final Size size;

    public FishSize(Size size) {
        this.size = size;
    }

    private boolean contains(Size[] sizes) {
        for (Size s: sizes) {
            if (s == size) return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean testCondition(Random rand, LootContext context) {
        EntityPlayer player = context.getKillerPlayer() instanceof EntityPlayer ? ((EntityPlayer)context.getKillerPlayer()) : null;
        if (player != null && player.getHeldItemMainhand() != null) {
            FishingRod rod = FishingAPI.INSTANCE.getFishingRodFromStack(player.getHeldItemMainhand());
            if (rod != null) {
                return contains(rod.getBestSizes()) ? rand.nextDouble() < 0.75D : rand.nextDouble() < 0.4D;
            }

            return false;
        }

        return false;
    }

    public static class Serializer extends LootCondition.Serializer<FishSize> {
        public Serializer() {
            super(new ResourceLocation(MODID, "fish_size"), FishSize.class);
        }

        public void serialize(JsonObject json, FishSize value, JsonSerializationContext context) {
            json.addProperty("size", value.size.name().toLowerCase(Locale.ENGLISH));
        }

        public FishSize deserialize(JsonObject json, JsonDeserializationContext context) {
            return new FishSize(Size.valueOf(JsonUtils.getString(json, "size").toUpperCase(Locale.ENGLISH)));
        }
    }
}
