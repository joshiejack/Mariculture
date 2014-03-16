package mariculture.factory.items;

import mariculture.core.items.ItemDamageable;

public class ItemRotor extends ItemDamageable {
	private int tier;
	
	public ItemRotor(int i, int dmg, int tier) {
		super(i, dmg);
		this.tier = tier;
	}
	
	public boolean isTier(int which) {
		return (tier == which || tier == (which - 1));
	}
}
