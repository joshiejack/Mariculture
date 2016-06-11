package joshie.mariculture.modules.diving.item;

import joshie.mariculture.helpers.EntityHelper;
import joshie.mariculture.modules.diving.render.ModelBuoyancyAid;
import joshie.mariculture.util.ItemArmorMC;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.modules.diving.Diving.ARMOR_SNORKEL;
import static joshie.mariculture.modules.diving.Diving.BUOYANCY_AID_ASCEND_SPEED;
import static net.minecraft.inventory.EntityEquipmentSlot.CHEST;

public class ItemBuoyancyAid extends ItemArmorMC<ItemBuoyancyAid> {
    public ItemBuoyancyAid() {
        super(ARMOR_SNORKEL, 0, CHEST);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot slot, ModelBiped default_) {
        return new ModelBuoyancyAid().setBiped(default_);
    }

    @Override
    public void onArmorUpdate(World world, Entity entity, ItemStack stack) {
        if (!entity.isSneaking()) {
            if (entity.motionY > -0.15D) {
                if (entity.isInsideOfMaterial(Material.WATER)) {
                    entity.motionY = BUOYANCY_AID_ASCEND_SPEED;
                } else if (EntityHelper.isInWater(entity)) {
                    entity.motionY = 0.0D;
                }
            }
        }
    }
}
