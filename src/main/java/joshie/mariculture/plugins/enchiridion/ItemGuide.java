package joshie.mariculture.plugins.enchiridion;

import java.util.List;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.items.ItemMariculture;
import joshie.mariculture.core.lib.GuideMeta;
import joshie.mariculture.core.lib.Modules;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enchiridion.CreativeTab;
import enchiridion.Enchiridion;

public class ItemGuide extends ItemMariculture {
    public ItemGuide() {
        setCreativeTab(CreativeTab.books);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case GuideMeta.FISHING:
                return "fishing";
            case GuideMeta.DIVING:
                return "diving";
            case GuideMeta.ENCHANTS:
                return "enchants";
            case GuideMeta.MACHINES:
                return "machines";
            case GuideMeta.PROCESSING:
                return "processing";
            default:
                return "guide";
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
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + getName(itemstack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[getMetaCount()];

        for (int i = 0; i < icons.length; i++)
            if (i != GuideMeta.RECIPES) {
                icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)) + "Guide");
            }
    }
}
