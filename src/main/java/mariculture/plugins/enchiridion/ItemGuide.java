package mariculture.plugins.enchiridion;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enchiridion.api.GuideHandler;

public class ItemGuide extends ItemMariculture {
    public ItemGuide(int i) {
        super(i);
    }

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
	public boolean getShareTag() {
		return true;
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		GuideHandler.openBook(player, Mariculture.modid + ":" + getName(stack));
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
			MaricultureRegistry.register(getName(new ItemStack(this, 1, j)) + "Guide", new ItemStack(this, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + getName(itemstack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			if(i != GuideMeta.BREEDING) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)) + "Guide");
			}
		}
	}
}
