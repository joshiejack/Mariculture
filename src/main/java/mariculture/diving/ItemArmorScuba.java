package mariculture.diving;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.util.IItemRegistry;
import mariculture.core.util.Text;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorScuba extends ItemArmor implements IItemRegistry, IDisablesHardcoreDiving {

    public ItemArmorScuba(ArmorMaterial material, int j, int k) {
        super(material, j, k);
        setCreativeTab(MaricultureTab.tabWorld);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == Diving.scubaSuit) return "mariculture:" + "textures/armor/scuba" + "_2.png";

        return "mariculture:" + "textures/armor/scuba" + "_1.png";
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (stack.getItem() == Diving.scubaMask) if (stack.hasTagCompound()) {
            final boolean landLightOn = stack.getTagCompound().getBoolean("ScubaMaskOnOutOfWater");

            if (landLightOn) {
                list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + Text.DARK_GREEN + StatCollector.translateToLocal("mariculture.string.on"));
            } else {
                list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + Text.RED + StatCollector.translateToLocal("mariculture.string.off"));
            }
        } else {
            list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + Text.RED + StatCollector.translateToLocal("mariculture.string.off"));
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() != Diving.scubaTank) return stack2.getItem() == Core.crafting && stack2.getItemDamage() == CraftingMeta.NEOPRENE;
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) if (stack.getItem() == Diving.scubaMask) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }

            if (player.isSneaking()) {
                stack.stackTagCompound.setBoolean("ScubaMaskOnOutOfWater", true);
            } else {
                stack.stackTagCompound.setBoolean("ScubaMaskOnOutOfWater", false);
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
        for (int j = 0; j < getMetaCount(); j++) {
            MaricultureRegistry.register(getName(new ItemStack(item, 1, j)), new ItemStack(item, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String theName, name = getName(new ItemStack(this));
        String[] aName = name.split("\\.");
        if (aName.length == 2) {
            theName = aName[0] + aName[1].substring(0, 1).toUpperCase() + aName[1].substring(1);
        } else {
            theName = name;
        }
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + theName);
    }
}