package joshie.mariculture.core.handlers;

import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.network.PacketHandler;
import joshie.mariculture.core.network.PacketSyncMirror;
import joshie.mariculture.magic.Magic;
import joshie.mariculture.magic.MirrorHelper;
import joshie.mariculture.magic.ResurrectionTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class ServerFMLEvents {
    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (Modules.isActive(Modules.magic) && player instanceof EntityPlayerMP) {
            PacketHandler.sendToClient(new PacketSyncMirror(MirrorHelper.getInventoryForPlayer(player)), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (Modules.isActive(Modules.magic) && EnchantHelper.exists(Magic.resurrection)) {
            ResurrectionTracker.onPlayerRespawn(event.player);
        }
    }
}
