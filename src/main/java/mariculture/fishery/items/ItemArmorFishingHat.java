package mariculture.fishery.items;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.ICaughtAliveModifier;
import mariculture.core.items.ItemMCBaseArmor;
import mariculture.fishery.render.ModelFishingHat;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorFishingHat extends ItemMCBaseArmor implements ICaughtAliveModifier {
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
}