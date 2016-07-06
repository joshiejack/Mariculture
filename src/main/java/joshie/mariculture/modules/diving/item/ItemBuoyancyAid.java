package joshie.mariculture.modules.diving.item;

import joshie.mariculture.helpers.EntityHelper;
import joshie.mariculture.util.ItemArmorMC;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import static joshie.mariculture.modules.diving.Diving.ARMOR_SNORKEL;
import static joshie.mariculture.modules.diving.Diving.BUOYANCY_AID_ASCEND_SPEED;
import static net.minecraft.inventory.EntityEquipmentSlot.CHEST;
import static net.minecraft.util.DamageSource.drown;

public class ItemBuoyancyAid extends ItemArmorMC<ItemBuoyancyAid> implements ISpecialArmor {
    public ItemBuoyancyAid() {
        super(ARMOR_SNORKEL, 0, CHEST);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if (player.motionY > -0.15D) {
            if (player.isInsideOfMaterial(Material.WATER)) {
                player.motionY = BUOYANCY_AID_ASCEND_SPEED;
            } else if (EntityHelper.isInWater(player)) {
                player.motionY = 0.0D;
            }
        }
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source == drown) return new ArmorProperties(0, 0.01D, Integer.MAX_VALUE);
        else if(source.isUnblockable()) return new ArmorProperties(0, 0, 0);
        else if (source.getEntity() instanceof EntityGuardian) {
            return new ArmorProperties(0, 0.04D, Integer.MAX_VALUE);
        } else return new ArmorProperties(0, 0.4D, Integer.MAX_VALUE);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 1;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        if (source.getEntity() instanceof EntityGuardian) {
            if (entity.worldObj.rand.nextInt(64) == 0) stack.damageItem(damage, entity);
        } else stack.damageItem(damage, entity);
    }
}
