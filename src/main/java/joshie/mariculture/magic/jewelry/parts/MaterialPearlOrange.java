package joshie.mariculture.magic.jewelry.parts;

import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.PearlColor;
import joshie.mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class MaterialPearlOrange extends JewelryMaterial {
    @Override
    public String getColor() {
        return joshie.lib.util.Text.ORANGE;
    }

    @Override
    public int onKill(LivingDeathEvent event, EntityPlayer player) {
        EntityXPOrb orb = new EntityXPOrb(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, player.worldObj.rand.nextInt(10));
        player.worldObj.spawnEntityInWorld(orb);
        return 10;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return 0;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return 3;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 1.5F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 2.5F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 1.5F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Core.pearls, 1, PearlColor.ORANGE);
    }
}
