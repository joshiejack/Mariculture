package joshie.mariculture.modules.diving.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import joshie.mariculture.api.diving.CapabilityWaterBreathing.WaterbreathingWrapper;
import joshie.mariculture.core.util.ItemArmorMC;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import static joshie.mariculture.modules.diving.Diving.ARMOR_SNORKEL;
import static net.minecraft.inventory.EntityEquipmentSlot.HEAD;

public class ItemSnorkel extends ItemArmorMC<ItemSnorkel> {
    public ItemSnorkel() {
        super(ARMOR_SNORKEL, 0, HEAD);
    }

    @Override //Overwrite this to return an empty map since this armour provides no protection, so we don't want the tooltip showing up
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        return HashMultimap.create();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new WaterbreathingWrapper(stack);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if (player.isInsideOfMaterial(Material.WATER)) {
            if (player.worldObj.getTotalWorldTime() % 2 == 0) {
                player.setAir(player.getAir() + 1);
            }
        }
    }
}
