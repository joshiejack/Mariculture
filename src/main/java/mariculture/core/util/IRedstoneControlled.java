package mariculture.core.util;

import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;

public interface IRedstoneControlled extends IHasClickableButton {
	public RedstoneMode getRSMode();
	public void setRSMode(RedstoneMode mode);
}
