package mariculture.magic.jewelry.parts;

import java.util.ArrayList;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.Core;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class MaterialPearlBrown extends JewelryMaterial {
    @Override
    public String getColor() {
        return mariculture.lib.util.Text.ORANGE;
    }

    @Override
    public int onBlockBreak(HarvestDropsEvent event, EntityPlayer player) {
        int dmg = 0;
        if (Modules.isActive(Modules.fishery)) if (event.block == Blocks.dirt || event.block == Blocks.grass) {
            dmg = 64;
            ArrayList<RecipeSifter> recipe = Fishing.sifter.getResult(new ItemStack(event.block));
            for (RecipeSifter sifter : recipe)
                if (player.worldObj.rand.nextInt(100) < sifter.chance) {
                    ItemStack cloned = sifter.bait.copy();
                    cloned.stackSize = sifter.minCount + player.worldObj.rand.nextInt(sifter.maxCount + 1 - sifter.minCount);
                    event.drops.add(sifter.bait);
                    dmg--;
                }
        }

        return dmg > 0 ? dmg : 1;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return 1;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return 5;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 1.0F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 0.75F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 1.25F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Core.pearls, 1, PearlColor.BROWN);
    }
}
