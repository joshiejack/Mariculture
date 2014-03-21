package mariculture.magic.jewelry.parts;

import java.util.HashMap;

import mariculture.Mariculture;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public abstract class JewelryMaterial {
	public static final String nbt = "material";
	public static final HashMap<String, JewelryMaterial> list = new HashMap();
	public final HashMap<JewelryType, IIcon> icons = new HashMap();
	public boolean ignore;
	public int id;
	
	public JewelryMaterial() {
		this.id = list.size();
		list.put(getIdentifier(), this);
	}
	
	public String getIdentifier() {
		String name = this.getClass().getSimpleName().substring(8);
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	/** Called by the jewelry handler when a player has the elemental affinity enchantment on a living hurt event 
	 * @param event - The event, can be cancelled from here
	 * @param player - Quicker access to the player **/
	public int onHurt(LivingHurtEvent event, EntityPlayer player) {
		return 0;
	}
	
	/** Called by the jewelry handler when a player has the elemental affinity enchantment on a living attack event and they're being attacked
	 * @param event - The event, can be cancelled from here
	 * @param player - Quicker access to the player **/
	public int onAttacked(LivingAttackEvent event, EntityPlayer player) {
		return 0;
	}
	
	/** Called by the jewelry handler when a player has the elemental affinity enchantment on a living attack event and they're being attacking
	 * @param event - The event, can be cancelled from here
	 * @param player - Quicker access to the player **/
	public int onAttack(LivingAttackEvent event, EntityPlayer player) {
		return 0;
	}
	
	/** Called by the jewelry handler when a player has the elemental affinity enchantment on a living death event 
	 * @param event - The event, can be cancelled from here
	 * @param player - Quicker access to the player **/
	public int onKill(LivingDeathEvent event, EntityPlayer player) {
		return 0;
	}
	
	/** Called by the jewelry handler when a player has the elemental affinity enchantment on a block break event 
	 * @param event - The event, can be cancelled from here
	 * @param player - Quicker access to the player **/
	public int onBlockBreak(HarvestDropsEvent event, EntityPlayer player) {
		return 0;
	}

	/** The icon for this material **/
	public IIcon getIcon(JewelryType type) {
		return icons.get(type);
	}
	
	/** Called by each individual jewelry registration **/
	public void registerIcons(IIconRegister iconRegistry, JewelryType type) {
		icons.put(type, iconRegistry.registerIcon(Mariculture.modid + ":jewelry/" + type.name().toLowerCase() + "/material/" + getIdentifier()));
	}
	
	/** A boost/reduction to maximum number of enchantments based on this color **/
	public abstract int getExtraEnchantments(JewelryType type);
	
	/** A limiting factor for the enchantment level on this piece of jewelry **/
	public abstract int getMaximumEnchantmentLevel(JewelryType type);
	
	/** Repair cost modifier, in anvil **/
	public abstract float getRepairModifier(JewelryType type);

	/** The modifier to durability **/
	public abstract float getDurabilityModifier(JewelryType type);

	/** Item used to craft this material **/
	public abstract ItemStack getCraftingItem(JewelryType type);

	/** The color this material displays on the title **/
	public abstract String getColor();
	
	/** Modifier to the hits, more or less **/
	public abstract float getHitsModifier(JewelryType type);
}
