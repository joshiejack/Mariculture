package joshie.mariculture.magic.jewelry.parts;

import joshie.lib.helpers.ItemHelper;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.PearlColor;
import joshie.mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class MaterialPearlGold extends JewelryMaterial {
    @Override
    public String getColor() {
        return joshie.lib.util.Text.YELLOW;
    }

    @Override
    public int onKill(LivingDeathEvent event, EntityPlayer player) {
        if (player.worldObj.rand.nextInt(50) == 0) {
            ItemHelper.spawnItem(event.entity.worldObj, (int) event.entity.posX, (int) event.entity.posY, (int) event.entity.posZ, new ItemStack(Items.gold_nugget));
            return 2;
        } else return 0;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return type == JewelryType.BRACELET ? 2 : type == JewelryType.RING ? 0 : 1;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return type == JewelryType.BRACELET ? 5 : 4;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 2.0F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 1.0F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 0.8F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Core.pearls, 1, PearlColor.GOLD);
    }
}
