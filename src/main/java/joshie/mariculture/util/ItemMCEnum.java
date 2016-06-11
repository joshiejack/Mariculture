package joshie.mariculture.util;

import joshie.mariculture.helpers.RegistryHelper;
import joshie.mariculture.helpers.StringHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static joshie.mariculture.lib.MaricultureInfo.MODID;
import static joshie.mariculture.util.MCTab.getTab;

public class ItemMCEnum<E extends Enum<E>> extends Item implements CreativeSorted {
    protected final E[] values;
    private String unlocalizedName;
    public ItemMCEnum(Class<E> clazz) {
        this(getTab("core"), clazz);
    }

    public ItemMCEnum(CreativeTabs tab, Class<E> clazz) {
        values = clazz.getEnumConstants();
        setHasSubtypes(true);
    }

    public ItemStack getStackFromEnum(E e) {
        return new ItemStack(this, 1, e.ordinal());
    }

    public E getEnumFromStack(ItemStack stack) {
        return values[Math.max(0, Math.min(values.length, stack.getItemDamage()))];
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public ItemMCEnum<E> setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        unlocalizedName = MODID + "." + name;
        RegistryHelper.register(this, name);
        return this;
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = getUnlocalizedName();
        String name = stack.getItem().getUnlocalizedName(stack);
        return StringHelper.localize(unlocalized + "." + name);
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return 500;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (E e: values) {
            list.add(new ItemStack(item, 1, e.ordinal()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModels(Item item, String name) {
        for (int i = 0; i < values.length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(MODID, getUnlocalizedName().replace(MODID + ".", "") + "_" + values[i].name().toLowerCase()), "inventory"));
        }
    }
}