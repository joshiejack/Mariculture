package mariculture.diving;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.Extra;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorDiving extends ItemArmor implements IItemRegistry, IDisablesHardcoreDiving {
	public ItemArmorDiving(ArmorMaterial material, int j, int k) {
		super(material, j, k);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (stack.getItem() == Diving.divingPants) {
			return "mariculture:" + "textures/armor/diving" + "_2.png";
		}

		return "mariculture:" + "textures/armor/diving" + "_1.png";
	}

	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		if(stack1.getItem() == Diving.divingHelmet && OreDicHelper.convert(stack2).equals("ingotCopper"))
			return true;
		if(stack1.getItem() == Diving.divingBoots && OreDicHelper.convert(stack2).equals("ingotIron"))
			return true;
		if((stack1.getItem() == Diving.divingTop || stack1.getItem() == Diving.divingPants) && stack2.getItem() == Items.leather)
			return true;
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (Extra.DEBUG_ON) {
			int size = 32;
			if (!world.isRemote) {
				for (int x = (int) (player.posX - size); x < player.posX + size; x++) {
					for (int z = (int) (player.posZ - size); z < player.posZ + size; z++) {
						for (int y = 0; y < 155; y++) {
							if (player.isSneaking()) {
								if (world.getBlock(x, y, z) == Blocks.stone
										|| world.getBlock(x, y, z) == Blocks.dirt
										|| world.getBlock(x, y, z) == Blocks.grass 
										|| world.getBlock(x, y, z) == Blocks.sandstone
										|| world.getBlock(x, y, z) == Blocks.gravel
										|| world.getBlock(x, y, z).getMaterial() == Material.water ||
										   world.getBlock(x, y, z) == Blocks.sand) {
									world.setBlockToAir(x, y, z);
								}
							} else {
								if (world.getBlock(x, y, z) == Core.limestone) {
									world.setBlockToAir(x, y, z);
								}
							}
						}
					}
				}
			}
		}

		return stack;
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
	public void register(Item item) {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(item, 1, j)), new ItemStack(item, 1, j));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String theName, name = getName(new ItemStack(this));
		String[] aName = name.split("\\.");
		if(aName.length == 2) theName = aName[0] + aName[1].substring(0, 1).toUpperCase() + aName[1].substring(1);
		else theName = name;
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + theName);
	}
}