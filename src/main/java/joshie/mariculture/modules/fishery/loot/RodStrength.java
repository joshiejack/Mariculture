package joshie.mariculture.modules.fishery.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.api.fishing.rod.FishingRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

import static joshie.mariculture.core.lib.MaricultureInfo.MODID;

/** This loot condition checks the strength of the item the player is holding in their main hand **/
public class RodStrength implements LootCondition {
    private final int strength;

    public RodStrength(int strength) {
        this.strength = strength;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean testCondition(Random rand, LootContext context) {
        EntityPlayer player = context.getKillerPlayer() instanceof EntityPlayer ? ((EntityPlayer)context.getKillerPlayer()) : null;
        if (player != null && player.getHeldItemMainhand() != null) {
            FishingRod rod = MaricultureAPI.fishing.getFishingRodFromStack(player.getHeldItemMainhand());
            if (rod != null) {
                return rod.getStrength() >= strength;
            }

            return false;
        }

        return false;
    }

    public static class Serializer extends LootCondition.Serializer<RodStrength> {
        public Serializer() {
            super(new ResourceLocation(MODID, "rod_strength"), RodStrength.class);
        }

        public void serialize(JsonObject json, RodStrength value, JsonSerializationContext context) {
            json.addProperty("strength", value.strength);
        }

        public RodStrength deserialize(JsonObject json, JsonDeserializationContext context) {
            return new RodStrength(JsonUtils.getInt(json, "strength", 1));
        }
    }
}
