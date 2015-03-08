package mariculture.core.util;

import net.minecraftforge.common.util.ForgeDirection;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;

public interface IEjectable extends IHasClickableButton {
    public EjectSetting getEjectSetting();

    public void setEjectSetting(EjectSetting setting);

    public EjectSetting getEjectType();

    //Whether the block can eject liquids to this side
	public boolean canEject(ForgeDirection dir);
}
