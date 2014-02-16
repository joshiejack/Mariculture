package mariculture.core.blocks;

import java.util.List;

import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.Text;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

public class BlockTankItem extends ItemBlockMariculture {
	public BlockTankItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case TankMeta.FISH:
			return "fish";
		case TankMeta.TANK:
			return "normal";
		case TankMeta.BOTTLE:
			return "bottle";
		default:
			return "tank";
		}
	}
	
	public FluidStack getFluid(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			return null;
		}
		
		return FluidStack.loadFluidStackFromNBT(stack.stackTagCompound);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.getItemDamage() == TankMeta.TANK)
			return Text.ORANGE + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		return super.getItemStackDisplayName(stack);
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(stack.getItemDamage() == TankMeta.TANK) {
			FluidStack fluid = getFluid(stack);
			int amount = fluid == null? 0: fluid.amount;
			list.add(StringHelper.getFluidName(fluid));
			list.add(""+ amount + "/16000mB");
		}
	}
}