package joshie.mariculture.modules.hardcore;

import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.api.diving.CapabilityWaterBreathing.IDisableHardcoreDiving;
import joshie.mariculture.api.diving.WaterBreathingChecker;
import joshie.mariculture.core.util.annotation.MCEvents;
import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.modules.diving.DivingAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import scala.reflect.internal.util.WeakHashSet;

import static joshie.mariculture.api.diving.CapabilityWaterBreathing.BREATHING_CAPABILITY;
import static joshie.mariculture.core.helpers.ConfigHelper.getBoolean;
import static joshie.mariculture.core.helpers.ConfigHelper.getInteger;
import static net.minecraft.block.material.Material.WATER;
import static net.minecraft.init.MobEffects.WATER_BREATHING;
import static net.minecraft.util.EnumFacing.DOWN;

@MCLoader(modules = "diving")
@MCEvents(modules = "hardcore-airDecreaseSpeed")
public class AirDecreaseSpeed {
    @SuppressWarnings("ConstantConditions")
    public static void preInit() {
        MaricultureAPI.diving.registerWaterbreathingListener(player -> {
            if (player.isPotionActive(WATER_BREATHING)) return true;
            for (ItemStack armor : player.getArmorInventoryList()) {
                if(armor != null && armor.hasCapability(BREATHING_CAPABILITY, DOWN)) {
                    IDisableHardcoreDiving hardcoreDiving = armor.getCapability(BREATHING_CAPABILITY, DOWN);
                    if (hardcoreDiving.canBreatheUnderwater(player, armor)) {
                        return true;
                    }
                }
            }

            return false;
        });
    }

    private final WeakHashSet<EntityPlayer> disabled = new WeakHashSet<>();

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        if (event.player.isInsideOfMaterial(WATER)) {
            EntityPlayer player = event.player;
            if (event.player.worldObj.getTotalWorldTime() % 30 == 0) {
                updateDisabledSet(player);
            }

            if (!disabled.contains(player)) {
                if (DROWN_AIR_SPEED >= 1) {
                    int air = player.getAir() - DROWN_AIR_SPEED;
                    if (air < 0) air = 0; //Fix the air
                    player.setAir(air);
                }

                if (DROWN_DAMAGE_SPEED >= 1 && player.getAir() == 0) player.attackEntityFrom(DamageSource.drown, DROWN_DAMAGE_SPEED);
            } else if (DROWN_DAMAGE_ALWAYS && DROWN_DAMAGE_SPEED >= 1 && player.getAir() == 0) player.attackEntityFrom(DamageSource.drown, DROWN_DAMAGE_SPEED);
        }
    }

    //Helper Methods
    private void updateDisabledSet(EntityPlayer player) {
        if (canBreatheUnderwater(player)) disabled.add(player);
        else disabled.remove(player);
    }

    private boolean canBreatheUnderwater(EntityPlayer player) {
        for (WaterBreathingChecker listener: DivingAPI.INSTANCE.getListeners()) {
            if (listener.canBreatheUnderwater(player)) {
                return true;
            }
        }

        return false;
    }

    private static boolean DROWN_DAMAGE_ALWAYS;
    private static int DROWN_DAMAGE_SPEED;
    private static int DROWN_AIR_SPEED;

    public static void configure() {
        DROWN_DAMAGE_ALWAYS = getBoolean("Enable bonus drowning damage with water breathing equipment", false);
        DROWN_DAMAGE_SPEED = getInteger("Damage bonus when drowning", 1);
        DROWN_AIR_SPEED = getInteger("Rate for losing extra air", 1);
    }
}
