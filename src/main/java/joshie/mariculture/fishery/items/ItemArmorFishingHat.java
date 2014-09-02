package joshie.mariculture.fishery.items;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.api.fishery.ICaughtAliveModifier;
import joshie.mariculture.fishery.render.ModelFishingHat;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorFishingHat extends ItemArmor implements ICaughtAliveModifier {
    public ItemArmorFishingHat(ArmorMaterial material, int j, int k) {
        super(material, j, k);
        setCreativeTab(MaricultureTab.tabFishery);
    }

    @Override
    public double getModifier() {
        return 5D;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return "mariculture:" + "textures/armor/fishinghat.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
        return new ModelFishingHat();
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + this.getUnlocalizedName().substring(5));
    }
}