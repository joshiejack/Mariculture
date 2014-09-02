package joshie.mariculture.factory.items;

import joshie.lib.helpers.ItemHelper;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.items.ItemDamageable;
import joshie.mariculture.core.lib.CraftingMeta;
import joshie.mariculture.core.lib.PlansMeta;
import joshie.mariculture.factory.Factory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChalk extends ItemDamageable {
    public ItemChalk(int dmg) {
        super(dmg);
        setCreativeTab(MaricultureTab.tabFactory);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int slot = PlayerHelper.hasItem(player, new ItemStack(Core.crafting, 1, CraftingMeta.BLANK_PLAN), false);
        if (slot != -1) {
            Block block = world.getBlock(x, y, z);
            if (block != null) {
                int plan = PlansMeta.getPlanType(block, world, x, y, z);
                if (plan != -1) {
                    ItemHelper.spawnItem(world, x, y + 1, z, PlansMeta.setType(new ItemStack(Factory.plans), plan));
                    if (!player.capabilities.isCreativeMode) {
                        player.inventory.decrStackSize(slot, 1);
                    }

                    world.spawnParticle("largeexplode", x, y + 1, z, 0.0D, 0.0D, 0.0D);
                    if (stack.attemptDamageItem(1, world.rand) || stack.getItemDamage() >= stack.getMaxDamage()) {
                        player.setCurrentItemOrArmor(0, null);
                    }
                }
            }
        }

        return true;
    }
}
