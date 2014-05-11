package mariculture.api.fishery;

@Deprecated
//Being replaced with the 'Rod Quality class instead'
public enum EnumRodQuality {
	OLD(RodQuality.OLD, 63, 15, 1),
	GOOD(RodQuality.GOOD, 191, 45, 3),
	SUPER(RodQuality.SUPER, 575, 100, 9),
	FLUX(RodQuality.FLUX, 0, 100, 0);

	private final RodQuality quality;
	private final int maxUses;
	private final int rank;
	private final int enchantability;

	/**
	 * These are the different rod qualities, the rank determines how good a rod
	 * is compared to others, higher = better
	 **/
	private EnumRodQuality(RodQuality quality, int uses, int rank, int enchantability) {
		this.quality = quality;
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

	public RodQuality getQuality() {
		return quality;
	}
}
