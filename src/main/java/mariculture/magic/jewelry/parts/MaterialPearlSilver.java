package mariculture.magic.jewelry.parts;

import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.lib.helpers.ItemHelper;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class MaterialPearlSilver extends JewelryMaterial {
    @Override
    public String getColor() {
        return mariculture.lib.util.Text.WHITE;
    }

    @Override
    public int onKill(LivingDeathEvent event, EntityPlayer player) {
        if (event.entity instanceof EntitySilverfish && player.worldObj.rand.nextInt(8) == 0) {
            ItemStack stack = null;
            if (Modules.isActive(Modules.fishery)) {
                stack = Fishing.fishing.getCatch(event.entity.worldObj, (int) event.entity.posX, (int) event.entity.posY, (int) event.entity.posZ, null, null);
            } else {
                stack = new ItemStack(Items.cooked_fished);
            }

            ItemHelper.spawnItem(event.entity.worldObj, (int) event.entity.posX, (int) event.entity.posY, (int) event.entity.posZ, stack);
            return 3;
        } else return 0;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return type == JewelryType.NECKLACE ? 1 : 0;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return type == JewelryType.NECKLACE ? 5 : 4;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 1.75F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 2.0F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 0.9F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Core.pearls, 1, PearlColor.SILVER);
    }
}
