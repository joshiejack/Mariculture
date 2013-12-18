package mariculture.api.fishery.fish;

public enum EnumFishFertility {
	EXTREMEHIGH(0, 10, false),
	VERYHIGH(11, 35, true),
	HIGH(36, 60, false),
	NORMAL(61, 130, true),
	LOW(131, 190, false),
	VERYLOW(191, 300, false),
	EXTREMELOW(301, 2000, false),
	PATHETIC(2001, 10000, false);

	private final int minFertility;
	private final int maxFertility;
	private final boolean isDominant;

	private EnumFishFertility(int min, int max, boolean dominant) {
		this.minFertility = min;
		this.maxFertility = max;
		this.isDominant = dominant;
	}

	public int getMin() {
		return this.minFertility;
	}

	public int getMax() {
		return this.maxFertility;
	}

	public boolean isDominant() {
		return this.isDominant;
	}
}
