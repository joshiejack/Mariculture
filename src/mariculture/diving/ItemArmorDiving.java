package mariculture.diving;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorDiving extends ItemArmor implements IItemRegistry, IDisablesHardcoreDiving {
	public ItemArmorDiving(int i, EnumArmorMaterial material, int j, int k) {
		super(i, material, j, k);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		if (stack.itemID == Diving.divingPants.itemID) {
			return "mariculture:" + "textures/armor/diving" + "_2.png";
		}

		return "mariculture:" + "textures/armor/diving" + "_1.png";
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)));
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		if(stack1.itemID == Diving.divingHelmet.itemID && OreDicHelper.convert(stack2).equals("ingotCopper"))
			return true;
		if(stack1.itemID == Diving.divingBoots.itemID && OreDicHelper.convert(stack2).equals("ingotIron"))
			return true;
		if((stack1.itemID == Diving.divingTop.itemID || stack1.itemID == Diving.divingPants.itemID) && stack2.itemID == Item.leather.itemID)
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
								if (world.getBlockId(x, y, z) == Block.stone.blockID
										|| world.getBlockId(x, y, z) == Block.dirt.blockID
										|| world.getBlockId(x, y, z) == Block.grass.blockID 
										|| world.getBlockId(x, y, z) == Block.sandStone.blockID
										|| world.getBlockId(x, y, z) == Block.gravel.blockID
										|| world.getBlockMaterial(x, y, z) == Material.water ||
										   world.getBlockId(x, y, z) == Block.sand.blockID) {
									world.setBlockToAir(x, y, z);
								}
							} else {
								if (world.getBlockId(x, y, z) == Core.oreBlocks.blockID
										&& world.getBlockMetadata(x, y, z) == OresMeta.LIMESTONE) {
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
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, j)), new ItemStack(this.itemID, 1, j));
		}
	}
}