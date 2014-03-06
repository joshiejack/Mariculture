package mariculture.api.fishery;

import java.util.HashMap;
import java.util.List;

import mariculture.api.core.EnumBiomeType;

public enum EnumRodQuality {
	OLD(15),
	GOOD(45),
	SUPER(100), 
	ELECTRIC(101);
	
	private final int rank;
	/** These are the different rod qualities, the rank determines how good a rod
	 * is compared to others, higher = better
	 **/
	private EnumRodQuality(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return this.rank;
	}
}
