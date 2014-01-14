package mariculture.core.items;

import java.util.List;

import mariculture.core.blocks.TileAnvil;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Text;
import mariculture.core.util.Rand;
import mariculture.factory.blocks.Tank;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemLadle extends ItemDamageable implements IFluidContainerItem {
	public ItemLadle(int i, int dmg) {
		super(i, dmg);
	}

	protected int capacity = MetalRates.INGOT;

	@Override
	public FluidStack getFluid(ItemStack container) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid")) {
			return null;
		}

		return FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(world.getBlockTileEntity(x, y, z) instanceof IFluidHandler && FluidHelper.isFluidOrEmpty(player.getCurrentEquippedItem())) {
			FluidHelper.handleFillOrDrain((IFluidHandler) world.getBlockTileEntity(x, y, z), player, ForgeDirection.getOrientation(side));
		}
		
		return true;
	}
	
	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public int getCapacity(ItemStack stack) {
		return capacity;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean bool) {
		if (getFluid(stack) == null || getFluid(stack).amount == 0) {
			list.add(Text.AQUA + StatCollector.translateToLocal("mariculture.string.empty")+ " 0/144mB");
		} else {
			FluidStack fluid = getFluid(stack);
			list.add(Text.AQUA + fluid.getFluid().getLocalizedName() + " " + fluid.amount + "/144mB");
		}
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		if(container.stackTagCompound == null) {
			container.setTagCompound(new NBTTagCompound());
		}
		
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(container.stackTagCompound);
		if(fluid == null) {
			fluid = resource;
		}
		
		if(!fluid.isFluidEqual(resource)) {
			return 0;
		}
		
		int fill = 144 - (144 - fluid.amount);
		if(doFill) {
			fluid.amount=fill;
			doFill.writeToNBT(container.stackTagCompound);
		}
				
		return fill;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		if(container.stackTagCompound == null) {
			return null;
		}
		
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(container.stackTagCompound);
		if(fluid == null) {
			return null;
		}
		
		FluidStack copied = fluid.copy();
		int drain = Math.min(fluid.amount, maxDrain);
		fluid.amount = drain;
		copied.amount-=drain;
		if(doDrain) {
			copied.writeToNBT(container.stackTagCompound);
		}
		
		return fluid;
	}
}
