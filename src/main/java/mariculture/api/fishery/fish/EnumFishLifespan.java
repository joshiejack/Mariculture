package mariculture.api.fishery.fish;

//These values are the true values: (species.getLifeSpan() * 20) * 60;
@Deprecated
public enum EnumFishLifespan {
	MINISCULE(0, 8750, true), 
	TINY(8751, 16252, false), 
	SHORT(16253, 26255, true), 
	MEDIUM(26256, 33757, true), 
	LONG(33758, 43760, false), 
	EPIC(43761, 51262, true), 
	MARATHON(51263, 125000, false), 
	INSANE(125001, 500000, false);

	private final int minLife;
	private final int maxLife;
	private final boolean isDominant;

	private EnumFishLifespan(int min, int max, boolean dominant) {
		this.minLife = min;
		this.maxLife = max;
		this.isDominant = dominant;
	}

	public int getMin() {
		return this.minLife;
	}

	public int getMax() {
		return this.maxLife;
	}

	public boolean isDominant() {
		return this.isDominant;
	}
}
