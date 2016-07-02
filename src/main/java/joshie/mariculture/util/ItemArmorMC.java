package joshie.mariculture.util;

import joshie.mariculture.api.diving.IDisableHardcoreDiving;
import joshie.mariculture.helpers.StringHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.lib.MaricultureInfo.MODID;
import static joshie.mariculture.util.MCTab.getTab;

public class ItemArmorMC<A extends ItemArmorMC> extends ItemArmor implements IDisableHardcoreDiving, MCItem<A> {
    @SideOnly(Side.CLIENT)
    private ModelBiped model;
    private String texture;

    public ItemArmorMC(ArmorMaterial material, int index, EntityEquipmentSlot slot) {
        super(material, index, slot);
        setCreativeTab(getTab("exploration"));
    }

    @Override
    public A register(String name) {
        MCItem.super.register(name);
        texture = MODID + ":textures/armor/" + name + ".png";
        return (A) this;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return StringHelper.localize(MODID + "." + getUnlocalizedName());
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot slot, ModelBiped default_) {
        return model;
    }

    @SideOnly(Side.CLIENT)
    public A setArmorModel(ModelBiped model) {
        this.model = model;
        return (A) this;
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return 0;
    }
}
