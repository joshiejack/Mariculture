package mariculture.core.util;

import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;

public interface IRedstoneControlled {
	public RedstoneMode getRSMode();
	public void setRSMode(RedstoneMode mode);
}
