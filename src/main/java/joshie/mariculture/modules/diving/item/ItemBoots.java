package joshie.mariculture.modules.diving.item;

import joshie.mariculture.core.helpers.ClientHelper;
import joshie.mariculture.core.helpers.EntityHelper;
import joshie.mariculture.core.util.item.ItemArmorMC;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static joshie.mariculture.modules.diving.Diving.ARMOR_DIVING;
import static net.minecraft.inventory.EntityEquipmentSlot.FEET;

public class ItemBoots extends ItemArmorMC<ItemBoots> {
    public ItemBoots() {
        super(ARMOR_DIVING, 0, FEET);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if (!world.isRemote) return;
        if (player.motionY > -10F) {
            if (player.isInsideOfMaterial(Material.WATER) || EntityHelper.isInWater(player)) {
                if (player.motionY < -0F && player.motionY != -0.02 && player.motionY != -0.0040000004768371705) player.motionY -= 0.05F;
                if ((player.motionX != 0 || player.motionZ != 0) && ClientHelper.isForwardPressed()) {
                    player.moveRelative(0F, 1F, 0.02F);
                }
            }
        }
    }
}
