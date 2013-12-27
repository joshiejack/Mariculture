package mariculture.core.lib;

public class MachineSpeeds {
	public static int sawmill = 0;
	public static int liquifier = 0;
	public static int settler = 0;
	public static int incubator = 0;
	public static int autofisher = 0;
	public static int feeder = 0;
	public static int net = 0;
	public static int dictionary = 0;
	
	public static int getSawmillSpeed() {
		if (Extra.DEBUG_ON) {
			return 10;
		}

		return sawmill;
	}

	public static int getLiquifierSpeed() {
		if (Extra.DEBUG_ON) {
			return 10;
		}

		return liquifier;
	}

	public static int getSettlerSpeed() {
		if (Extra.DEBUG_ON) {
			return 10;
		}

		return settler;
	}

	public static int getIncubatorSpeed() {
		if (Extra.DEBUG_ON) {
			return 10;
		}

		return incubator;
	}

	public static int getAutofisherSpeed() {
		if (Extra.DEBUG_ON) {
			return 10;
		}

		return autofisher;
	}

	public static int getFeederSpeed() {
		if (Extra.DEBUG_ON) {
			return 10;
		}

		return feeder;
	}

	public static int getNetSpeed() {
		if (Extra.DEBUG_ON) {
			return 10;
		}

		return net;
	}

	public static int getDictionarySpeed() {
		if (Extra.DEBUG_ON) {
			return 1;
		}
		
		return dictionary;
	}
}
