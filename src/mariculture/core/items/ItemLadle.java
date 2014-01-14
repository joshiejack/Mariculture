package mariculture.core.items;

import java.util.List;

import mariculture.core.blocks.TileAnvil;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.helpers.cofh.StringHelper;
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
	public ItemLadle(int i) {
		super(i, MAX_DAMAGE);
	}

	public static final int MAX_DAMAGE = 100;
	protected int capacity = MetalRates.INGOT;

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(item.getItemDamage() < item.getMaxDamage()) {
			if (world.getBlockTileEntity(x, y, z) instanceof IFluidHandler && FluidHelper.isIContainer(item)) {
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				IFluidHandler tank = (IFluidHandler) world.getBlockTileEntity(x, y, z);
				IFluidContainerItem container = (IFluidContainerItem) item.getItem();
				FluidStack fluid = container.getFluid(item);
				if(fluid != null && fluid.amount > 0) {
					FluidStack stack = container.drain(item, 1000000, false);
					if(stack != null) {
						stack.amount = tank.fill(dir, stack, false);					
						if(stack.amount > 0) {
							container.drain(item, stack.amount, true);
							tank.fill(dir, stack, true);
							item.attemptDamageItem(1, Rand.rand);
							if(item.getItemDamage() == item.getMaxDamage())
								player.renderBrokenItemStack(item);
							return true;
						}
					}
				} else {
					FluidStack stack = tank.drain(dir, MetalRates.INGOT, false);
					if(stack != null && stack.amount > 0) {
						tank.drain(dir, stack.amount, true);
						container.fill(item, stack, true);
						item.attemptDamageItem(1, Rand.rand);
						if(item.getItemDamage() == item.getMaxDamage())
							player.renderBrokenItemStack(item);
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
        return Text.ORANGE + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		FluidStack fluid = getFluid(stack);
		int amount = fluid == null? 0: fluid.amount;
		list.add(StringHelper.getFluidName(fluid));
		list.add("" + amount + "/144mB");
	}

	@Override
	public int getCapacity(ItemStack stack) {
		return capacity;
	}

	@Override
	public FluidStack getFluid(ItemStack container) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid")) {
			return null;
		}

		return FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		if (resource == null) {
			return 0;
		}

		if (!doFill) {
			if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid")) {
				return Math.min(capacity, resource.amount);
			}

			FluidStack stack = FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));

			if (stack == null) {
				return Math.min(capacity, resource.amount);
			}

			if (!stack.isFluidEqual(resource)) {
				return 0;
			}

			return Math.min(capacity - stack.amount, resource.amount);
		}

		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}

		if (!container.stackTagCompound.hasKey("Fluid")) {
			NBTTagCompound fluidTag = resource.writeToNBT(new NBTTagCompound());

			if (capacity < resource.amount) {
				fluidTag.setInteger("Amount", capacity);
				container.stackTagCompound.setTag("Fluid", fluidTag);
				return capacity;
			}

			container.stackTagCompound.setTag("Fluid", fluidTag);
			return resource.amount;
		}

		NBTTagCompound fluidTag = container.stackTagCompound.getCompoundTag("Fluid");
		FluidStack stack = FluidStack.loadFluidStackFromNBT(fluidTag);

		if (!stack.isFluidEqual(resource)) {
			return 0;
		}

		int filled = capacity - stack.amount;
		if (resource.amount < filled) {
			stack.amount += resource.amount;
			filled = resource.amount;
		} else {
			stack.amount = capacity;
		}

		container.stackTagCompound.setTag("Fluid", stack.writeToNBT(fluidTag));
		return filled;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid")) {
			return null;
		}
		
		FluidStack stack = FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));
		if (stack == null) {
			return null;
		}

		stack.amount = Math.min(stack.amount, maxDrain);
		if (doDrain) {
			if (maxDrain >= capacity) {
				container.stackTagCompound.removeTag("Fluid");

				if (container.stackTagCompound.hasNoTags()) {
					container.stackTagCompound = null;
				}
				
				System.out.println("i should have drained");
				
				return stack;
			}

			NBTTagCompound fluidTag = container.stackTagCompound.getCompoundTag("Fluid");
			fluidTag.setInteger("Amount", fluidTag.getInteger("Amount") - maxDrain);
			container.stackTagCompound.setTag("Fluid", fluidTag);
		}
		
		return stack;
	}
}
