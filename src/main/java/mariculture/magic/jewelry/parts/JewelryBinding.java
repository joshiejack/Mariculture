package mariculture.magic.jewelry.parts;

import java.util.HashMap;

import mariculture.Mariculture;
import mariculture.core.util.Text;
import mariculture.magic.JewelryHandler;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class JewelryBinding {
	public static final String nbt = "binding";
	public static final HashMap<String, JewelryBinding> list = new HashMap();
	public final HashMap<JewelryType, IIcon> icons = new HashMap();
	public boolean ignore = false;
	
	public JewelryBinding() {
		list.put(getIdentifier(), this);
	}
	
	public String getIdentifier() {
		String name = this.getClass().getSimpleName().substring(7);
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
	
	/** The icon for this material **/
	public IIcon getIcon(JewelryType type) {
		return icons.get(type);
	}
	
	/** Called by each individual jewelry registration **/
	public void registerIcons(IIconRegister iconRegistry, JewelryType type) {
		icons.put(type, iconRegistry.registerIcon(Mariculture.modid + ":jewelry/" + type.name().toLowerCase() + "/binding/" + getIdentifier()));
	}

	/** The durability base of this material **/
	public abstract int getDurabilityBase(JewelryType type);

	/** The stack used to craft this binding **/
	public abstract ItemStack getCraftingItem(JewelryType type);

	/** Color of this binding **/
	public abstract String getColor();
	
	/** Number of hits this binding requires **/
	public abstract int getHitsBase(JewelryType type);

	/** How much of a chance this binding has for each enchantment to be kept **/
	public abstract int getKeepEnchantmentChance(JewelryType type);
	
	/** The ABSOLUTE maximum enchantment level this binding can have **/
	public abstract int getMaxEnchantmentLevel(JewelryType type);
}
