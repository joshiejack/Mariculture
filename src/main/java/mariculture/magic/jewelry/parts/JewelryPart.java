package mariculture.magic.jewelry.parts;

import java.util.ArrayList;

import mariculture.core.lib.Jewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class JewelryPart {
	public static final ArrayList<JewelryPart> materialList = new ArrayList();
	public final int id;
	
	public JewelryPart(int id) {
		this.id = id;
		materialList.add(this);
	}
	
	//Whether or not this part is valid for 0 - Ring, 1 - Bracelet, 2 - Necklace
	public boolean isValid(int type) {
		return true;
	}
	
	//Whether this is 'visible' as in whether to show/register a image
	public boolean isVisible(int type) {
		return true;
	}

	//The name of this part
	public String getPartName() {
		return "blank";
	}
	
	//The language Entry to use
	public String getPartLang() {
		return "";
	}

	//The type of Jewelry Part this is
	public String getPartType(int type) {
		return "blank";
	}

	//The 'Enchantability' that this part provides
	public int getEnchantability() {
		return 1;
	}

	//return the color code you want this material to make the jewelry pieces' name
	public String getColor() {
		return "\u00a7f";
	}
	
	//return the ItemStack for this item for crafting
	public ItemStack getItemStack() {
		return new ItemStack(Items.gold_ingot);
	}
	
	//Add Any Enchantments by default
	public ItemStack addEnchantments(ItemStack stack) {
		return stack;
	}

	//Return true if you should only add once instance of this item
	public boolean addOnce() {
		return false;
	}

	//Return true if this a 'special' part like for example the one ring
	public boolean isSingle() {
		return false;
	}

	//Number of hits this part requires in an anvil
	public int getHits(int type) {
		if(type == Jewelry.RING)
			return 30;
		if(type == Jewelry.BRACELET)
			return 60;
		return 50;
	}

	//Number of Base durability this part provides
	public int getDurabilityBase(int type) {
		return 100;
	}
	
	//How much this part modifies the durability by
	public double getDurabilityModifier(int type) {
		return 1.0D;
	}

	//Do nothing
	public int cancelDamage(EntityPlayer player, DamageSource source) {
		return 0;
	}
}
