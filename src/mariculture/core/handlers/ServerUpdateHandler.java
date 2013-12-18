package mariculture.core.handlers;

import java.util.EnumSet;

import mariculture.core.lib.Modules;
import mariculture.fishery.fish.FishSalmon;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class ServerUpdateHandler implements IScheduledTickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (Modules.fishery.isActive()) {
			FishSalmon.cook();
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
		return "MaricultureServerUpdates";
	}

	@Override
	public int nextTickSpacing() {
		return 200;
	}

}
