package mariculture.api.fishery;


public class RodQuality {
	public static final RodQuality OLD = new RodQuality(EnumRodQuality.OLD, 63, 16, 1, 10);
	public static final RodQuality GOOD = new RodQuality(EnumRodQuality.GOOD, 191, 32, 3, 25);
	public static final RodQuality SUPER = new RodQuality(EnumRodQuality.SUPER, 575, 64, 9, 50);
	public static final RodQuality FLUX = new RodQuality(EnumRodQuality.FLUX, 0, 96, 0, 75);

	@Deprecated
	private EnumRodQuality quality;
	//This variable determines the ratio of GOOD vs JUNK loot with this rod, 100 = 100% good, minimum is 1, anything higher than 100 = guaranteed to be good loot instead of junk
	private final int alter;
	private final int rank;
	private final int maxUses;
	private final int enchantability;

	/**
	 * These are the different rod qualities, the rank determines how good a rod
	 * is compared to others, higher = better
	 **/
	@Deprecated
	public RodQuality(EnumRodQuality quality, int uses, int rank, int enchantability, int alter) {
		this(uses, rank, enchantability, alter);
		this.quality = quality;
	}
	
	public RodQuality(int uses, int rank, int enchantability, int alter) {
		this.maxUses = uses;
		this.rank = rank;
		this.enchantability = enchantability;
		this.alter = alter;
	}

	public int getRank() {
		return this.rank;
	}
	
	public int getRatio() {
		return this.alter;
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public int getEnchantability() {
		return this.enchantability;
	}
	
	@Deprecated
	public EnumRodQuality getEnum() {
		return quality;
	}

	//Special cases for catching certain fish non raw, based on the rod quality
	public boolean caughtAlive(String species) {
		return false;
	}
}
