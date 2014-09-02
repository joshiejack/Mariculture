package joshie.mariculture.core.items;

import java.util.List;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDamageable extends Item {
    public ItemDamageable(int dmg) {
        setCreativeTab(MaricultureTab.tabCore);
        setMaxStackSize(1);
        setMaxDamage(dmg);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String theName, name = this.getUnlocalizedName().substring(5);
        String[] aName = name.split("\\.");
        if (aName.length == 2) {
            theName = aName[0] + aName[1].substring(0, 1).toUpperCase() + aName[1].substring(1);
        } else {
            theName = name;
        }
        
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + theName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        list.add(new ItemStack(item, 1, 0));
    }
}
