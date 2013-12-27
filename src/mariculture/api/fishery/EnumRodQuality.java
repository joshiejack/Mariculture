package mariculture.api.fishery;

public enum EnumRodQuality {
	OLD(63, 15, 1),
	GOOD(191, 45, 3),
	SUPER(575, 100, 9);

	private final int maxUses;
	private final int rank;
	private final int enchantability;

	/**
	 * These are the different rod qualities, the rank determines how good a rod
	 * is compared to others, higher = better
	 **/
	private EnumRodQuality(int uses, int rank, int enchantability) {
		this.maxUses = uses;
		this.rank = rank;
		this.enchantability = enchantability;
	}

	public int getRank() {
		return this.rank;
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public int getEnchantability() {
		return this.enchantability;
	}
}
