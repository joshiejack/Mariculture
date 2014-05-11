package mariculture.core.lib;

import net.minecraft.util.DamageSource;

public class MaricultureDamage extends DamageSource {
	public static final DamageSource piranha = (new MaricultureDamage("piranha"));
	public static final DamageSource oneRing = (new MaricultureDamage("oneRing")).setDamageBypassesArmor();
	public static final DamageSource scald = (new MaricultureDamage("scald"));
	public static final DamageSource turbine = (new MaricultureDamage("turbine")).setDamageBypassesArmor();
	public static final DamageSource siamese = new MaricultureDamage("siamese");

	private MaricultureDamage(String name) {
		super(name);
	}
}
