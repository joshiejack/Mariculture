package mariculture.core.util;

import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import net.minecraft.client.entity.EntityClientPlayerMP;

public interface IRedstoneControlled {
	//Toggle Mode is only called client side
	public void toggleMode(EntityClientPlayerMP player);
	public RedstoneMode getMode();
	public void setMode(RedstoneMode mode);
}
