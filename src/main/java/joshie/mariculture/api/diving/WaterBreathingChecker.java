package joshie.mariculture.api.diving;

import net.minecraft.entity.player.EntityPlayer;

/** This is used to check if a player has the ability to breathe underwater,
 *  The default implementation in mariculture simply checks the armour slots,
 *  and if there is a waterbreathing potion active. You can register your own
 *  listener, so for example the bauble slots could also be checked. (This is,
 *  included in Mariculture by default but this is what enables that functionality */
public interface WaterBreathingChecker {
    /** @param player    the player
     *  @return whether this player is capable of breathing underwater */
    boolean canBreatheUnderwater(EntityPlayer player);
}
