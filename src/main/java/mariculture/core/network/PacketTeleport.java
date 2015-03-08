package mariculture.core.network;

import java.util.HashMap;
import java.util.UUID;

import mariculture.Mariculture;
import mariculture.core.config.Enchantments;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTeleport extends PacketCoords implements IMessageHandler<PacketCoords, IMessage> {
    public static final HashMap<UUID, Long> last_teleport = new HashMap();
    
    public long getLastBlink(EntityPlayerMP player) {
        UUID uuid = EntityPlayer.func_146094_a(player.getGameProfile());
        if (last_teleport.get(uuid) == null) {
            last_teleport.put(uuid, 0L);
        }
        
        return last_teleport.get(uuid);
    }
    
    public void setLastBlink(EntityPlayerMP player) {
        UUID uuid = EntityPlayer.func_146094_a(player.getGameProfile());
        last_teleport.put(uuid, System.currentTimeMillis());
    }

    public PacketTeleport() {}

    public PacketTeleport(int x, int y, int z) {
        super(x, y, z);
    }

    @Override
    public IMessage onMessage(PacketCoords message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        x = message.x;
        y = message.y;
        z = message.z;

        if (EnchantHelper.hasEnchantment(Magic.blink, player)) {
            if (player.worldObj.isAirBlock(x, y + 1, z) && player.worldObj.isAirBlock(x, y + 2, z)) {
                long currentTime = System.currentTimeMillis();
                long msSinceLastBlink = currentTime - getLastBlink(ctx.getServerHandler().playerEntity);
                
                if (msSinceLastBlink < Enchantments.BLINK_MILLISECONDS) {
                    return null;
                }
                
                int distanceX, distanceZ, distanceY;
                if (y + 1 > player.posY) {
                    distanceY = (int) (y + 1 - player.posY);
                } else {
                    distanceY = (int) player.posY - y + 1;
                }

                distanceY = distanceY * distanceY;

                if (x > player.posX) {
                    distanceX = (int) (x - player.posX);
                } else {
                    distanceX = (int) (player.posX - x);
                }

                distanceX = distanceX * distanceX;

                if (z > player.posZ) {
                    distanceZ = (int) (z - player.posZ);
                } else {
                    distanceZ = (int) (player.posZ - z);
                }

                distanceZ = distanceZ * distanceZ;

                int sum = distanceZ + distanceX + distanceY;
                int distance = (int) Math.cbrt(sum) + 6;
                int maxDistance = EnchantHelper.getEnchantStrength(Magic.blink, player) * 16;
                maxDistance = Math.min(maxDistance, Enchantments.RAY_TRACE_DISTANCE);

                if (distance <= maxDistance) {
                    player.worldObj.playSoundAtEntity(player, Mariculture.modid + ":blink", 1.0F, 1.0F);
                    player.setPositionAndUpdate(x, y, z);
                    player.worldObj.playSoundAtEntity(player, Mariculture.modid + ":blink", 1.0F, 1.0F);
                    EnchantHelper.damageItems(Magic.blink, player, 1);
                    setLastBlink(ctx.getServerHandler().playerEntity);
                }
            }
        }
        return null;
    }
}
