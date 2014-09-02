package joshie.mariculture.diving;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.diving.render.ModelLifejacket;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorSnorkel extends ItemArmor implements IDisablesHardcoreDiving {
    public ItemArmorSnorkel(ArmorMaterial material, int j, int k) {
        super(material, j, k);
        setCreativeTab(MaricultureTab.tabWorld);
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

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + getUnlocalizedName().substring(5));
    }
}