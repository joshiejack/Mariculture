package mariculture.core.util;

import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import net.minecraft.client.entity.EntityClientPlayerMP;

public interface IRedstoneControlled {
	public RedstoneMode getRSMode();
	public void setRSMode(RedstoneMode mode);
}
