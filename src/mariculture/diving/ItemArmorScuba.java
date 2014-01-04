package mariculture.diving;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Mariculture;
import mariculture.core.lib.Text;
import mariculture.core.util.IItemRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemArmorScuba extends ItemArmor implements IItemRegistry, IDisablesHardcoreDiving {

	public ItemArmorScuba(int i, EnumArmorMaterial material, int j, int k) {
		super(i, material, j, k);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		if (stack.itemID == Diving.scubaSuit.itemID) {
			return "mariculture:" + "textures/armor/scuba" + "_2.png";
		}

		return "mariculture:" + "textures/armor/scuba" + "_1.png";
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.itemID == Diving.scubaMask.itemID) {
			if (stack.hasTagCompound()) {
				final boolean landLightOn = stack.getTagCompound().getBoolean("ScubaMaskOnOutOfWater");

				if (landLightOn) {
					list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + Text.DARK_GREEN
							+ StatCollector.translateToLocal("mariculture.string.on"));
				} else {
					list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + Text.RED
							+ StatCollector.translateToLocal("mariculture.string.off"));
				}
			} else {
				list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + Text.RED
						+ StatCollector.translateToLocal("mariculture.string.off"));
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if (stack.itemID == Diving.scubaMask.itemID) {
				if (!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
				}

				if (player.isSneaking()) {
					stack.stackTagCompound.setBoolean("ScubaMaskOnOutOfWater", true);
				} else {
					stack.stackTagCompound.setBoolean("ScubaMaskOnOutOfWater", false);
				}
			}
		}

		return stack;
	}

	@Override
	public void registerIcons(final IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)));
	}
	
	@Override
	public int getMetaCount() {
		return 1;
	}
	
	@Override
	public String getName(ItemStack stack) {
		return stack.getUnlocalizedName().substring(5);
	}
	
	@Override
	public void register() {
		for(int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, j)), new ItemStack(this.itemID, 1, j));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creative, List list) {
		ItemStack stack = new ItemStack(this);
		if(stack.itemID == Diving.scubaTank.itemID) {
			stack.setItemDamage(1);
		}
		
		list.add(stack);
	}
}