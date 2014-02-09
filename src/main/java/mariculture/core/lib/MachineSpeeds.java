package mariculture.core.lib;

public class MachineSpeeds {
	public static int sawmill;
	public static int liquifier;
	public static int settler;
	public static int incubator;
	public static int autofisher;
	public static int feeder;
	public static int net;
	public static int dictionary;
	public static int oven;
	
	public static int getSawmillSpeed() {
		return Extra.DEBUG_ON? 10: sawmill;
	}

	public static int getLiquifierSpeed() {
		return Extra.DEBUG_ON? 10: liquifier;
	}

	public static int getSettlerSpeed() {
		return Extra.DEBUG_ON? 10: settler;
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

	public static int getDictionarySpeed() {
		return Extra.DEBUG_ON? 1: dictionary;
	}

	public static int getOvenSpeed() {
		return Extra.DEBUG_ON? 1: oven;
	}
}
