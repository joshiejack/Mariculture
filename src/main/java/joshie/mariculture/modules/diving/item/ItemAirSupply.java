package joshie.mariculture.modules.diving.item;

import joshie.mariculture.api.diving.CapabilityWaterBreathing.WaterbreathingWrapper;
import joshie.mariculture.core.helpers.EntityHelper;
import joshie.mariculture.core.util.item.ItemArmorMC;
import joshie.mariculture.modules.diving.Diving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import static joshie.mariculture.modules.diving.Diving.ARMOR_DIVING;
import static net.minecraft.inventory.EntityEquipmentSlot.CHEST;

public class ItemAirSupply extends ItemArmorMC<ItemAirSupply> {
    public ItemAirSupply() {
        super(ARMOR_DIVING, 0, CHEST);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new WaterbreathingWrapper(stack) {
            @Override
            public boolean canBreatheUnderwater(EntityPlayer player, ItemStack stack) {
                return EntityHelper.hasArmor(player, EntityEquipmentSlot.HEAD, Diving.HELMET);
            }
        };
    }
}
