package mariculture.magic.enchantments;

import mariculture.core.config.Enchantments;
import mariculture.core.helpers.KeyHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketTeleport;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class EnchantmentBlink extends EnchantmentJewelry {
    public static long LAST_BLINK = 0L;
    
    public EnchantmentBlink(int i, int weight, EnumEnchantmentType type) {
        super(i, weight, type);
        setName("blink");
        minLevel = 25;
        maxLevel = 40;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void sendPacket(EntityClientPlayerMP player) {        
        long currentTime = System.currentTimeMillis();
        long msSinceLastBlink = currentTime - LAST_BLINK;
        
        if (msSinceLastBlink < Enchantments.BLINK_MILLISECONDS) {
            return;
        }
        
        MovingObjectPosition lookAt = player.rayTrace(Enchantments.RAY_TRACE_DISTANCE, 1);
        if (lookAt != null && lookAt.typeOfHit == MovingObjectType.BLOCK && KeyHelper.isActivateKeyPressed()) {
            PacketHandler.sendToServer(new PacketTeleport(lookAt.blockX, lookAt.blockY + 1, lookAt.blockZ));
            LAST_BLINK = System.currentTimeMillis();
        }
    }
}
