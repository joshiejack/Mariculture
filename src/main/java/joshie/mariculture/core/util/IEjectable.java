package joshie.mariculture.core.util;

import joshie.mariculture.core.gui.feature.FeatureEject.EjectSetting;

public interface IEjectable extends IHasClickableButton {
    public EjectSetting getEjectSetting();

    public void setEjectSetting(EjectSetting setting);

    public EjectSetting getEjectType();
}
