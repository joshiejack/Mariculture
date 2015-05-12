package mariculture.plugins.enchiridion;

import java.util.List;

import mariculture.Mariculture;
import mariculture.core.items.ItemMCMeta;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.MCTranslate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enchiridion.CreativeTab;
import enchiridion.Enchiridion;

public class ItemGuide extends ItemMCMeta {
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
            case GuideMeta.FISH_DATA:
                return "fish";
            case GuideMeta.BLOOD_MAGIC:
                return "blood";
            case GuideMeta.BOTANIA:
                return "botania";
            default:
                return "guide";
        }
    }

    public boolean isEnchiridion2Book(int meta) {
        return meta >= GuideMeta.FISH_DATA || meta == GuideMeta.PROCESSING;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add(MCTranslate.translate("by") + " " + MCTranslate.translate("guide." + getName(stack) + ".author"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (isEnchiridion2Book(stack.getItemDamage())) return stack;
        else {
            player.openGui(Enchiridion.instance, 0, player.worldObj, 0, 0, 0);
            return stack;
        }
    }
    
    public static boolean BLOOD = false;
    public static boolean BOTANIA = false;

    @Override
    public boolean isActive(int meta) {
        switch (meta) {
            case GuideMeta.PROCESSING:
                return true;
            case GuideMeta.FISHING:
                return Modules.isActive(Modules.fishery);
            case GuideMeta.MACHINES:
                return Modules.isActive(Modules.factory);
            case GuideMeta.FISH_DATA:
                return Modules.isActive(Modules.fishery);
            case GuideMeta.ENCHANTS:
                return Modules.isActive(Modules.magic);
            case GuideMeta.DIVING:
                return Modules.isActive(Modules.diving);
            case GuideMeta.BLOOD_MAGIC:
                return BLOOD;
            case GuideMeta.BOTANIA:
                return BOTANIA;
            default:
                return false;
        }
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] { CreativeTab.books };
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

        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)) + "Guide");
        }
    }
}
