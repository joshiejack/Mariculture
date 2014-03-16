package mariculture.magic.jewelry;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.items.ItemDamageable;
import mariculture.core.util.Text;
import mariculture.magic.JewelryHandler;
import mariculture.magic.JewelryHandler.SettingType;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.parts.JewelryBinding;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemJewelry extends ItemDamageable {	
	public static final HashMap<String, IIcon> specials = new HashMap();
	
	public static enum JewelryType {
		RING(1), BRACELET(3), NECKLACE(7), NONE(0);

		public final int max;
		private JewelryType(int max) {
			this.max = max;
		}
		
		public int getMaximumEnchantments() {
			return this.max;
		}
	}
	
	public ItemJewelry() {
		super(100);
		setNoRepair();
		setCreativeTab(MaricultureTab.tabJewelry);
	}

	//The 'code' 0, 1, 2 for the item types
	public abstract JewelryType getType();
	//This is the utter maximum durability upgrades this piece can have
	public abstract int getMaxDurability();
	//This is the maximum levels this piece can gain
	public abstract int getMaxLevel();
	//Whether to render the binding
	public abstract boolean renderBinding();
	
	@Override
	public int getItemEnchantability() {
        return 2;
    }
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int meta) {
		return 3;
	}
		
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			String level =  " (" + JewelryHandler.getSetting(stack, SettingType.LEVEL) + "/" + getMaxLevel() + ")";
			JewelryMaterial material = JewelryHandler.getMaterial(stack);
			if(material.ignore) return stack.stackTagCompound.getString("Specials") + level;
			return material.getColor() + material.getCraftingItem(getType()).getDisplayName() + " " + Text.localize(getUnlocalizedName(stack) + ".name") + level;
		}

		return Text.localize(getUnlocalizedName(stack));
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.hasTagCompound()) {
			JewelryBinding binding = JewelryHandler.getBinding(stack);
			list.add(binding.getColor() + StatCollector.translateToLocal("mariculture.string.with") + " " + binding.getCraftingItem(getType()).getDisplayName());
			
			list.add(" ");
			list.add(Text.BRIGHT_GREEN + "XP: (" + JewelryHandler.getSetting(stack, SettingType.XP) + " / 100)");
		}

		//Add the one ring lore
		if (EnchantHelper.getLevel(Magic.oneRing, stack) > 0) {
			list.add(" ");
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line1"));
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line2"));
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line3"));
			list.add(StatCollector.translateToLocal("enchantment.oneRing.line4"));
			list.add(" ");
		}
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		if(stack.hasTagCompound()) {
			int base = (int) (JewelryHandler.getBinding(stack).getDurabilityBase(getType()) * JewelryHandler.getMaterial(stack).getDurabilityModifier(getType()));
			int boost = JewelryHandler.getSetting(stack, SettingType.DURABILITY) * 50;
			return base + boost;
		} else return this.getMaxDamage();
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if(stack.hasTagCompound()) {
			if(pass == 0) {
				if(renderBinding()) {
					JewelryBinding binding = JewelryHandler.getBinding(stack);
					if(binding != null && !binding.ignore) return binding.getIcon(getType());
				}
			} else if (pass == 1) {
				JewelryMaterial material = JewelryHandler.getMaterial(stack);
				if(material != null && !material.ignore) return material.getIcon(getType());
			} else if(stack.stackTagCompound.hasKey("Specials")) {
				return specials.get(stack.stackTagCompound.getString("Specials"));
			}
		} 
		
		return itemIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		//Register the binding icons for this type
		if(renderBinding()) {
			for (Entry<String, JewelryBinding> binding : JewelryBinding.list.entrySet()) {
				if(binding.getValue().ignore) continue;
				binding.getValue().registerIcons(iconRegister, getType());
			}
		}
		
		//Register the material icons for this type
		for (Entry<String, JewelryMaterial> material : JewelryMaterial.list.entrySet()) {
			if(material.getValue().ignore) continue;
			material.getValue().registerIcons(iconRegister, getType());
		}
		
		//Register the invisible icon
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":jewelry/blank");
		
		//Register the onering
		specials.put(Text.YELLOW + "One Ring", iconRegister.registerIcon(Mariculture.modid + ":jewelry/ring/oneRing"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		//Add all item variants to the list
		for (Entry<String, JewelryBinding> binding : JewelryBinding.list.entrySet()) {
			if(binding.getValue().ignore) continue;
			for (Entry<String, JewelryMaterial> material : JewelryMaterial.list.entrySet()) {
				if(material.getValue().ignore) continue;
				list.add(JewelryHandler.createBestJewelry((ItemJewelry)item, binding.getValue(), material.getValue()));
			}
		}
		
		//Add the one ring to the list
		ItemStack stack = JewelryHandler.createSpecial((ItemJewelry) item, JewelryType.RING, Text.YELLOW + "One Ring");
		if(stack != null) {
			if(EnchantHelper.exists(Magic.oneRing)) {
				stack.addEnchantment(Magic.oneRing, 1);
				list.add(stack);
			}
		}
	}
}
