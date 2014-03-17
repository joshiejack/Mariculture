package mariculture.core.lib;

public class MachineSpeeds {
	public static int sawmill;
	public static int crucible;
	public static int incubator;
	public static int autofisher;
	public static int feeder;
	public static int net;
	
	public static int getSawmillSpeed() {
		return Extra.DEBUG_ON? 10: sawmill;
	}

	public static int getCrucibleSpeed() {
		return Extra.DEBUG_ON? 10: crucible;
	}

	public static int getIncubatorSpeed() {
		return Extra.DEBUG_ON? 10: incubator;
	}

	public static int getAutofisherSpeed() {
		return Extra.DEBUG_ON? 10: autofisher;
	}

	public static int getFeederSpeed() {
		return Extra.DEBUG_ON? 10: feeder;
	}

	public static int getNetSpeed() {
		return Extra.DEBUG_ON? 10: net;
	}
}
