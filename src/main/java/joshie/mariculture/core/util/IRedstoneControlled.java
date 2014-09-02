package joshie.mariculture.core.util;

import joshie.mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;

public interface IRedstoneControlled extends IHasClickableButton {
    public RedstoneMode getRSMode();

    public void setRSMode(RedstoneMode mode);
}
