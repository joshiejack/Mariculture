package mariculture.core.handlers;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.Modules;
import mariculture.magic.Magic;
import mariculture.magic.ResurrectionTracker;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTrackerHandler implements IPlayerTracker {
	@Override
	public void onPlayerLogin(EntityPlayer player) {	

	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		if(Modules.magic.isActive() && EnchantHelper.exists(Magic.resurrection)) {
			ResurrectionTracker.onPlayerRespawn(player);
		}
	}
}
