package mariculture.api.fishery.fish;

public enum EnumFishWorkEthic {
	LAZY(0, false), 
	NORMAL(1, true), 
	HARDWORKER(2, false),
	OVERCLOCKER(3, false);

	private final int multiplier;
	private final boolean isDominant;

	private EnumFishWorkEthic(int multiplier, boolean dominant) {
		this.multiplier = multiplier;
		this.isDominant = dominant;
	}

	public int getMultiplier() {
		return this.multiplier;
	}

	public boolean isDominant() {
		return this.isDominant;
	}
}
