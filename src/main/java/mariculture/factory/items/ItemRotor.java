package mariculture.factory.items;

import mariculture.api.core.MaricultureTab;
import mariculture.core.items.ItemDamageable;

public class ItemRotor extends ItemDamageable {
	private int tier;
	
	public ItemRotor(int dmg, int tier) {
		super(dmg);
		this.tier = tier;
		setCreativeTab(MaricultureTab.tabFactory);
	}
	
	public boolean isTier(int which) {
		return (tier == which || tier == (which - 1));
	}
}
