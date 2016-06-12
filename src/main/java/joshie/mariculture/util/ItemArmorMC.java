package joshie.mariculture.util;

import joshie.mariculture.api.diving.IDisableHardcoreDiving;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
    public A setUnlocalizedName(String name) {
        texture = MODID + ":textures/armor/" + name + ".png";
        super.setUnlocalizedName(name);
        return (A) this;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return MCItem.super.getItemStackDisplayName(stack);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    public ItemStack getStack() {
        return new ItemStack(this);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean isSelected) {
        if (slot == armorType.getIndex()) {
            onArmorUpdate(world, player, stack);
        }
    }

    public void onArmorUpdate(World world, Entity player, ItemStack stack) {}

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
