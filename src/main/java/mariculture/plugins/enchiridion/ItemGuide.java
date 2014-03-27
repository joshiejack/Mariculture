package mariculture.plugins.enchiridion;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.Core;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.IHasGUI;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enchiridion.Enchiridion;

public class ItemGuide extends ItemMariculture {	
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
			case GuideMeta.FISHING: 	return "fishing";
			case GuideMeta.DIVING: 		return "diving";
			case GuideMeta.ENCHANTS: 	return "enchants";
			case GuideMeta.MACHINES: 	return "machines";
			case GuideMeta.PROCESSING: 	return "processing";
			default: 					return "guide";
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(StatCollector.translateToLocal("mariculture.string.by") + " " + StatCollector.translateToLocal("item.guide." + getName(stack) + ".author"));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.openGui(Enchiridion.instance, 0, player.worldObj, 0, 0, 0);
		return stack;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case GuideMeta.PROCESSING:
			return true;
		case GuideMeta.FISHING:
			return Modules.isActive(Modules.fishery);
		case GuideMeta.MACHINES:
			return Modules.isActive(Modules.factory);
		case GuideMeta.RECIPES:
			return false;
		case GuideMeta.ENCHANTS:
			return Modules.isActive(Modules.magic);
		case GuideMeta.DIVING:
			return Modules.isActive(Modules.diving);
		default:
			return false;
		}
	}

	@Override
	public int getMetaCount() {
		return GuideMeta.COUNT;
	}

	@Override
	public void register(Item item) {
		for (int j = 0; j < getMetaCount() + 1; j++) {
			MaricultureRegistry.register(getName(new ItemStack(item, 1, j)) + "Guide", new ItemStack(item, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + getName(itemstack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			if(i != GuideMeta.RECIPES) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)) + "Guide");
			}
		}
	}
}
