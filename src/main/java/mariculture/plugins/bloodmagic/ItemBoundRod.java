package mariculture.plugins.bloodmagic;

import java.util.List;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodQuality;
import mariculture.core.util.Rand;
import mariculture.fishery.items.ItemRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;

public class ItemBoundRod extends ItemRod {
	public ItemBoundRod(int i, RodQuality quality) {
		super(i, quality);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.isSneaking()) {
			EnergyItems.checkAndSetItemOwner(stack, player);
			return stack;
		} else {
			return Fishing.rodHandler.handleRightClick(stack, world, player, quality, Rand.rand);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Let the rivers flow red with blood");

		if (!(stack.stackTagCompound == null)) {
			if (stack.stackTagCompound.getBoolean("isActive")) {
				list.add("Activated");
			} else {
				list.add("Deactivated");
			}

			if (!stack.stackTagCompound.getString("ownerName").equals("")) {
				list.add("Current owner: " + stack.stackTagCompound.getString("ownerName"));
			}
		}
		
		super.addInformation(stack, player, list, bool);
	}

	@Override
	public boolean canFish(World world, int x, int y, int z, EntityPlayer player, ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack damage(EntityPlayer player, ItemStack stack, int fish) {
		if (player != null)
			EnergyItems.syphonBatteries(stack, player, 250);
		else {
			EnergyItems.syphonWhileInContainer(stack, 250);
		}

		return stack;
	}
}
