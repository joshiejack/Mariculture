package mariculture.core.handlers;

import java.util.EnumSet;

import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.magic.EnchantPacket;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class PlayerUpdatesHandler implements IScheduledTickHandler {
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (Modules.magic.isActive()) {
			EntityPlayer player = (EntityPlayer) tickData[0];
			EnchantPacket.activateEnchants(player);
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "MariculturePlayerUpdates";
	}

	@Override
	public int nextTickSpacing() {
		return Extra.REFRESH_CLIENT_RATE;
	}
}
