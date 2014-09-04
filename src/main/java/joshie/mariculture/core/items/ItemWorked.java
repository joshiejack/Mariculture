package joshie.mariculture.core.items;

import java.util.List;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.helpers.NBTHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWorked extends ItemMCDamageable {
    public IIcon crack;

    public ItemWorked() {
        super(100);
        setNoRepair();
        setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.stackTagCompound == null) return StatCollector.translateToLocal("itemGroup.jewelryTab");

        ItemStack worked = NBTHelper.getItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
        return StatCollector.translateToLocal("mariculture.string.unworked") + " " + worked.getItem().getItemStackDisplayName(worked);
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        if (stack.stackTagCompound == null) return 0;

        return 1 + stack.stackTagCompound.getInteger("Required") - stack.stackTagCompound.getInteger("Worked");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return stack.hasTagCompound() && stack.stackTagCompound.hasKey("EnchantmentList");
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (stack.stackTagCompound == null) return 0;

        return 1 + stack.stackTagCompound.getInteger("Required");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (stack.stackTagCompound == null) return;

        int max = stack.stackTagCompound.getInteger("Required");
        int cur = stack.stackTagCompound.getInteger("Worked");
        int percent = (int) ((double) cur / (double) max * 100);

        list.add(percent + "% Worked");
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int meta) {
        return 5;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (pass < 4) if (stack.hasTagCompound()) {
            ItemStack worked = NBTHelper.getItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
            if (worked == null || worked.getItem() == null) return crack;
            if (worked.getItem() != this) return worked.getItem().getIcon(worked, pass);
        }

        return crack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":unworked");
        crack = iconRegister.registerIcon(Mariculture.modid + ":crack");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        return;
    }
}
