package joshie.mariculture.diving;

import joshie.mariculture.core.items.ItemMCBaseArmor;
import joshie.mariculture.diving.render.ModelLifejacket;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorSnorkel extends ItemMCBaseArmor {
    public ItemArmorSnorkel(ArmorMaterial material, int j, int k) {
        super(material, j, k);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == Diving.lifejacket) return "mariculture:" + "textures/armor/lifejacket.png";
        else return "mariculture:" + "textures/armor/snorkel.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
        return stack.getItem() == Diving.lifejacket ? new ModelLifejacket() : null;
    }
}