package mariculture.core.util;

import mariculture.core.gui.feature.FeatureEject.EjectSetting;

public interface IEjectable {
	public EjectSetting getEjectSetting();
	public void setEjectSetting(EjectSetting setting);
	public EjectSetting getEjectType();
}
