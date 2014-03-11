package mariculture.fishery;

import java.util.ArrayList;
import java.util.HashMap;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IFishingRod;
import mariculture.core.lib.Extra;
import mariculture.core.util.Rand;
import mariculture.fishery.items.ItemVanillaRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import cofh.api.energy.IEnergyContainerItem;

public class FishingRodHandler implements IFishingRod {
	public static HashMap<Item, EnumRodQuality> registry = new HashMap();
	public static ArrayList<FishingRod> canUseBait = new ArrayList<FishingRod>();

	public static ArrayList<FishingRod> getCanUseList() {
		return canUseBait;
	}
	
	public static class FishingRod {
		ItemStack itemStack;
		EnumRodQuality enumQuality;

		public FishingRod(ItemStack item, EnumRodQuality quality) {
			this.itemStack = item;
			this.enumQuality = quality;
		}

		public boolean equals(ItemStack item, EnumRodQuality quality) {
			return (quality == enumQuality && item.isItemEqual(this.itemStack));
		}
	}
	
	private int[] getBait(EntityPlayer player, EnumRodQuality quality) {
		int baitQuality = 0;
		int currentSlot = player.inventory.currentItem;
		int foundSlot = -1;

		if (currentSlot > 0) {
			int leftSlot = currentSlot - 1;

			if (player.inventory.getStackInSlot(leftSlot) != null) {
				if (Fishing.rodHandler.canUseBait(player.inventory.getStackInSlot(leftSlot), quality)) {
					baitQuality = Fishing.bait.getBaitQuality(player.inventory.getStackInSlot(leftSlot));
					foundSlot = leftSlot;
				}
			}
		}

		if (foundSlot == -1 && currentSlot < 8) {
			int rightSlot = currentSlot + 1;

			if (player.inventory.getStackInSlot(rightSlot) != null) {
				if (Fishing.rodHandler.canUseBait(player.inventory.getStackInSlot(rightSlot), quality)) {
					baitQuality = Fishing.bait.getBaitQuality(player.inventory.getStackInSlot(rightSlot));
					foundSlot = rightSlot;
				}
			}
		}
		
		return new int[] { baitQuality, foundSlot };
	}
	
	@Override
	public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player) {
		EnumRodQuality quality = Fishing.rodHandler.getRodQuality(stack);
		if(quality != null) {
			if(stack.getItem() instanceof IEnergyContainerItem) {
				if(((IEnergyContainerItem)stack.getItem()).getEnergyStored(stack) < 100)
					return stack;
			}
			
			int baitQuality = getBait(player, quality)[0];
			int baitSlot = getBait(player, quality)[1];
			
			if (player.fishEntity != null) {
	            int i = player.fishEntity.func_146034_e();
	            if(stack.getItem() instanceof IEnergyContainerItem) {
	            	((IEnergyContainerItem)stack.getItem()).extractEnergy(stack, 100, false);
	            } else {
	            	stack.damageItem(i, player);
	            }
	            
	            player.swingItem();
	        } else if(baitSlot != -1 || (!Extra.VANILLA_FORCE && stack.getItem() instanceof ItemVanillaRod)) {
	        	world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (Rand.rand.nextFloat() * 0.4F + 0.8F));
	        	        	
	        	EntityHook hook = new EntityHook(world, player, baitQuality);
	        	if(!world.isRemote)  {
	        		world.spawnEntityInWorld(hook);
	        	}
	        	
	        	if (!player.capabilities.isCreativeMode) {
					if(baitQuality > 0) player.inventory.decrStackSize(baitSlot, 1);
				}
		/*
	        	if(id == 0 || world.getEntityByID(id) == null || notFound) {        		
	        		stack.stackTagCompound.setInteger("EntityID", bobber.getEntityId());
	        		if (!world.isRemote) {
	        			if (!player.capabilities.isCreativeMode) {
	    					player.inventory.decrStackSize(baitSlot, 1);
	    				}
	        			
	                	world.spawnEntityInWorld(new EntityFishing(world, player, baitQuality + 1));
	                	Set SlotPacketDispatcher.sendPacketToPlayer(new Packet103SetSlot(0, baitSlot + 36, player.inventory.getStackInSlot(baitSlot)), (Player) player);
	                }
	        	} */
	
	            player.swingItem();
	        }
		}

        return stack;
	}
	
	@Override
	public boolean canUseBait(ItemStack stack, EnumRodQuality quality) {
		ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) getCanUseList().clone();
		for (FishingRod loot : lootTmp) {
			if (loot.equals(stack, quality)) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public EnumRodQuality addRodQuality(String name, int maxUses) {
		return EnumHelper.addEnum(EnumRodQuality.class, name, maxUses);
	}

	@Override
	public EnumRodQuality getRodQuality(ItemStack stack) {
		return registry.get(stack.getItem());
	}

	@Override
	public void registerRod(Item item, EnumRodQuality quality) {
		registry.put(item, quality);
	}
}
