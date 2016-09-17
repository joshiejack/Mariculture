package joshie.mariculture.modules.fishery.item;

import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import joshie.mariculture.modules.fishery.utils.FishingRodHelper;
import joshie.mariculture.core.util.item.MCItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/** Mariculture custom fishing rod **/
public class ItemFishingRodMC extends ItemFishingRod implements MCItem<ItemFishingRodMC> {
    public ItemFishingRodMC() {
        setCreativeTab(MCTab.getFishery());
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return FishingRodHelper.getDurability(stack);
    }

    @Override
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, @Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        if (playerIn.fishEntity != null) {
            int i = playerIn.fishEntity.handleHookRetraction();
            if (FishingRodHelper.isDamageable(stack)) {
                stack.damageItem(i, playerIn);
            }

            playerIn.swingArm(hand);
        } else {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote) {
                worldIn.spawnEntityInWorld(new EntityFishHookMC(worldIn, playerIn, null));
            }

            playerIn.swingArm(hand);
            playerIn.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        list.add(FishingRodHelper.build(1000, false));
    }
}
