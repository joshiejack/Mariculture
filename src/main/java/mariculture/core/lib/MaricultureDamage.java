package mariculture.core.lib;

import net.minecraft.util.DamageSource;

public class MaricultureDamage extends DamageSource {
	public static DamageSource piranha = (new MaricultureDamage("piranha"));
	public static DamageSource oneRing = (new MaricultureDamage("oneRing")).setDamageBypassesArmor();
	public static DamageSource scald = (new MaricultureDamage("scald"));
    public static DamageSource turbine = (new MaricultureDamage("turbine")).setDamageBypassesArmor();

	private MaricultureDamage(String name) {
		super(name);
	}
}
