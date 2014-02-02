package mariculture.core.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.guide.GuiGuide;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import mariculture.plugins.compatibility.CompatBooks;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGuide extends ItemMariculture {
	public Icon pageIcon;
	
	public ItemGuide(int id) {
		super(id);
	}

	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case GuideMeta.FISHING:
			return "fishing";
		case GuideMeta.BREEDING:
			return "breeding";
		case GuideMeta.DIVING:
			return "diving";
		case GuideMeta.ENCHANTS:
			return "enchants";
		case GuideMeta.MACHINES:
			return "machines";
		case GuideMeta.PROCESSING:
			return "processing";
		case GuideMeta.CUSTOM:
			return "custom";
		default:
			return "guide";
		}
	}

	public static Object getGui(ItemStack stack) {
		if(stack == null)
			return null;
		switch (stack.getItemDamage()) {
		case GuideMeta.FISHING:
			return new GuiGuide(0x008C8C, "fishing");
		case GuideMeta.BREEDING:
			return new GuiGuide(0xFF8000, "breeding");
		case GuideMeta.PROCESSING:
			return new GuiGuide(0x1C1B1B, "processing");
		case GuideMeta.MACHINES:
			return new GuiGuide(0x333333, "machines");
		case GuideMeta.ENCHANTS:
			return new GuiGuide(0xA64DFF, "enchants");
		case GuideMeta.DIVING:
			return new GuiGuide(0x75BAFF, "diving");
		case GuideMeta.CUSTOM:
			return CompatBooks.getGUI(stack);
		default:
			return null;
		}
	}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {
		if(stack.getItemDamage() < GuideMeta.CUSTOM || pass > 0)
			return 16777215;
		return CompatBooks.getColor(stack);
    }
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if(stack.getItemDamage() < GuideMeta.CUSTOM || pass == 0) {
			return getIconFromDamage(stack.getItemDamage());
		} else {
			return pageIcon;
		}
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		if(stack.getItemDamage() < GuideMeta.CUSTOM)
			return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		return CompatBooks.getName(stack);
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(stack.getItemDamage() < GuideMeta.CUSTOM) {
			list.add(StatCollector.translateToLocal("mariculture.string.by") + " " + StatCollector.translateToLocal("item.guide." + getName(stack) + ".author"));
		} else {
			CompatBooks.addAuthor(stack, list);
		}
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public boolean getShareTag() {
		return true;
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote) {
			player.openGui(Mariculture.instance, GuiIds.GUIDE, world, stack.getItemDamage(), 0, 0);
		}

		return stack;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case GuideMeta.PROCESSING:
			return true;
		case GuideMeta.FISHING:
			return Modules.fishery.isActive();
		case GuideMeta.MACHINES:
			return Modules.factory.isActive();
		case GuideMeta.BREEDING:
			return false;
		case GuideMeta.ENCHANTS:
			return Modules.magic.isActive();
		case GuideMeta.DIVING:
			return Modules.diving.isActive();
		default:
			return false;
		}
	}

	@Override
	public int getMetaCount() {
		return GuideMeta.COUNT;
	}

	@Override
	public void register() {
		for (int j = 0; j < getMetaCount() + 1; j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, j)) + "Guide", new ItemStack(this.itemID, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + getName(itemstack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creative, List list) {
		super.getSubItems(id, creative, list);
		CompatBooks.addBooks(id, creative, list);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			if(i != GuideMeta.BREEDING) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this.itemID, 1, i)) + "Guide");
			}
		}
		
		pageIcon = iconRegister.registerIcon(Mariculture.modid + ":guidePages");
	}
}
