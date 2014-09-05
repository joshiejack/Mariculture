package joshie.mariculture.fishery.items;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.core.helpers.OreDicHelper;
import joshie.mariculture.core.lib.Modules;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRod extends ItemFishingRod {
    private ItemStack repair;
    private int enchant;

    public ItemRod() {
        this(127, 1);
        setCreativeTab(MaricultureTab.tabFishery);
    }

    public ItemRod(int max, int enchant) {
        this.enchant = enchant;
        setMaxStackSize(1);
        setCreativeTab(MaricultureTab.tabFishery);
        if (max > 0) {
            setMaxDamage(max);
        }
    }

    @Override
    public Item setCreativeTab(CreativeTabs tab) {
        if (tab == null) {
            MaricultureTab.tabCore = new MaricultureTab("mariculture.core");
            MaricultureTab.tabFactory = new MaricultureTab("mariculture.machines");
            MaricultureTab.tabFishery = new MaricultureTab("mariculture.fishing");
            MaricultureTab.tabWorld = new MaricultureTab("mariculture.world");
            MaricultureTab.tabMagic = new MaricultureTab("mariculture.magic");
            tab = MaricultureTab.tabFishery;
        }

        super.setCreativeTab(tab);
        return this;
    }

    public void setRepairMaterial(ItemStack stack) {
        this.repair = stack;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() instanceof IEnergyContainerItem) return false;
        ItemStack toRepairWith = repair != null ? repair : new ItemStack(Items.stick);
        return OreDicHelper.convert(stack2).equals(OreDicHelper.convert(toRepairWith));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!Modules.isActive(Modules.fishery)) return super.onItemRightClick(stack, world, player);
        return Fishing.fishing.handleRightClick(stack, world, player);
    }

    @Override
    public int getItemEnchantability() {
        return enchant;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String theName, name = getUnlocalizedName().substring(5);
        String[] aName = name.split("\\.");
        if (aName.length == 2) {
            theName = aName[0] + aName[1].substring(0, 1).toUpperCase() + aName[1].substring(1);
        } else {
            theName = name;
        }
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + theName);
    }
}
