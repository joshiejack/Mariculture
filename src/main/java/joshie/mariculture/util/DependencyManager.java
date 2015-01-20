package joshie.mariculture.util;

import joshie.mariculture.lib.MaricultureInfo;

public class DependencyManager {
	//Temporarily Disable the requirement for enchiridion2, until it is actually needed :P
	public static boolean hasDependencies() {
		return PenguinCoreManager.isPresent(MaricultureInfo.PENGUINCORE_VERSION) /*&& Enchiridion2Manager.isPresent(MaricultureInfo.ENCHIRIDION_VERSION)*/;
	}
}
