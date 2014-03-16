package mariculture.magic.jewelry.parts;

import java.util.HashMap;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.core.util.Text;
import mariculture.magic.JewelryHandler;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public abstract class JewelryMaterial {
	public static final String nbt = "material";
	public static final HashMap<String, JewelryMaterial> list = new HashMap();
	public final HashMap<JewelryType, IIcon> icons = new HashMap();
	public boolean ignore;
	
	public JewelryMaterial() {
		list.put(getIdentifier(), this);
	}
	
	public String getIdentifier() {
		String name = this.getClass().getSimpleName().substring(8);
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	/** Called by the jewelry handler when a player has the elemental affinity enchantment on a living hurt event 
	 * @param event - The event, can be cancelled from here
	 * @param player - Quicker access to the player **/
	public void onHurt(LivingHurtEvent event, EntityPlayer player) {
		return;
	}

	/** The icon for this material **/
	public IIcon getIcon(JewelryType type) {
		return icons.get(type);
	}
	
	/** Called by each individual jewelry registration **/
	public void registerIcons(IIconRegister iconRegistry, JewelryType type) {
		icons.put(type, iconRegistry.registerIcon(Mariculture.modid + ":jewelry/" + type.name().toLowerCase() + "/material/" + getIdentifier()));
	}

	/** The modifier to durability **/
	public abstract float getDurabilityModifier(JewelryType type);

	/** Item used to craft this material **/
	public abstract ItemStack getCraftingItem(JewelryType type);

	/** The color this material displays on the title **/
	public abstract String getColor();
	
	/** Modifier to the hits, more or less **/
	public abstract float getHitsModifier(JewelryType type);
}
