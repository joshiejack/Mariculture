package mariculture.core.handlers;

import java.util.EnumSet;

import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.diving.DivingPackets;
import mariculture.magic.EnchantPacket;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class ClientUpdateHandler implements IScheduledTickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (Modules.diving.isActive()) {
			DivingPackets.updateClientRenders();
		}

		if (Modules.magic.isActive()) {
			EnchantPacket.updateActiveEnchantments();
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return "MaricultureRenderUpdates";
	}

	@Override
	public int nextTickSpacing() {
		return Extra.REFRESH_CLIENT_RATE;
	}

}
