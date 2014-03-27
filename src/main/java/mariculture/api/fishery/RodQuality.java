package mariculture.api.fishery;

public class RodQuality {
	public static final RodQuality OLD = new RodQuality(63, 15, 1);
	public static final RodQuality GOOD = new RodQuality(191, 45, 3);
	public static final RodQuality SUPER = new RodQuality(575, 100, 9);
	public static final RodQuality FLUX = new RodQuality(0, 100, 0);

	private final int maxUses;
	private final int rank;
	private final int enchantability;

	/**
	 * These are the different rod qualities, the rank determines how good a rod
	 * is compared to others, higher = better
	 **/
	public RodQuality(int uses, int rank, int enchantability) {
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
