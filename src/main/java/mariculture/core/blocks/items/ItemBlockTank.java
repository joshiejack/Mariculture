package mariculture.core.blocks.items;

import java.util.List;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.TankMeta;
import mariculture.core.tile.TileTankBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ItemBlockTank extends ItemBlockMariculture {
    public ItemBlockTank(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case TankMeta.FISH:
                return "fish";
            case TankMeta.TANK:
                return "normal";
            case TankMeta.BOTTLE:
                return "bottle";
            case TankMeta.DIC:
                return "fluidtionary";
            case TankMeta.HATCHERY:
                return "hatchery";
            case TankMeta.TANK_ALUMINUM:
                return "aluminum";
            case TankMeta.TANK_TITANIUM:
                return "titanium";
            case TankMeta.TANK_GAS:
                return "gas";
            default:
                return "tank";
        }
    }

    public FluidStack getFluid(ItemStack stack) {
        if (!stack.hasTagCompound()) return null;

        return FluidStack.loadFluidStackFromNBT(stack.stackTagCompound);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        int dmg = stack.getItemDamage();
        int total = dmg == TankMeta.TANK ? TileTankBlock.COPPER_CAPACITY : dmg == TankMeta.TANK_ALUMINUM ? TileTankBlock.ALUMINUM_CAPACITY : dmg == TankMeta.TANK_TITANIUM ? TileTankBlock.TITANIUM_CAPACITY : dmg == TankMeta.TANK_GAS ? TileTankBlock.GAS_CAPACITY : -1;
        if (total >= 0) {
            FluidStack fluid = getFluid(stack);
            int amount = fluid == null ? 0 : fluid.amount;
            list.add(FluidHelper.getFluidName(fluid));
            list.add("" + amount + "/" + total + "mB");
        }
    }
}