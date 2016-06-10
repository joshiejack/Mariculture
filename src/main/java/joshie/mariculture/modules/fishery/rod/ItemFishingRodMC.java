package joshie.mariculture.modules.fishery.rod;

import joshie.mariculture.helpers.RegistryHelper;
import joshie.mariculture.modules.fishery.entity.EntityFishHookMC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

/** Mariculture custom fishing rod **/
public class ItemFishingRodMC extends ItemFishingRod {
    private String unlocalizedName;

    @Override
    public ItemFishingRodMC setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        unlocalizedName = MODID + "." + name;
        RegistryHelper.register(this, name);
        return this;
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (playerIn.fishEntity != null) {
            int i = playerIn.fishEntity.handleHookRetraction();
            stack.damageItem(i, playerIn);
            playerIn.swingArm(hand);
        } else {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!worldIn.isRemote) {
                worldIn.spawnEntityInWorld(new EntityFishHookMC(worldIn, playerIn));
            }

            playerIn.swingArm(hand);
            playerIn.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }
}
