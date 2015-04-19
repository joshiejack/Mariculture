package mariculture.plugins.botania;

import mariculture.fishery.items.ItemRod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.ModItems;

public class ItemLivingRod extends ItemRod implements IManaUsingItem {
    public ItemLivingRod() {
        super();
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if (!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, 60, true)) stack.setItemDamage(stack.getItemDamage() - 1);
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == ModItems.manaResource && stack2.getItemDamage() == 0 ? true : super.getIsRepairable(stack1, stack2);
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }
}
