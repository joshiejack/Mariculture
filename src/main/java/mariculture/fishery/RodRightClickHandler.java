package mariculture.fishery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IFishingRod;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.api.fishery.RodQuality;
import mariculture.core.lib.Text;
import mariculture.fishery.RodQualityHandler.FishingRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class RodRightClickHandler implements IFishingRod {	
	@Override
	public ItemStack handleRightClick(ItemStack stack, World world, EntityPlayer player, RodQuality quality, Random rand) {
		ItemBaseRod rod = (ItemBaseRod) stack.getItem();
		if(!rod.canFish(world, (int)player.posX, (int)player.posY, (int)player.posZ, player, stack)) return stack;
		
		int baitQuality = getBait(player, quality)[0];
		int baitSlot = getBait(player, quality)[1];
		
		if (player.fishEntity != null) {
            ((ItemBaseRod)stack.getItem()).damage(world, player, stack, player.fishEntity.catchFish());
            player.swingItem();
        } else if(baitSlot != -1) {
        	world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
        	        	
        	EntityFishing bobber = new EntityFishing(world, player, baitQuality + 1);
        	if(!stack.hasTagCompound()) {
        		stack.setTagCompound(new NBTTagCompound());
        	}
        	
        	boolean notFound = true;
        	int id = stack.stackTagCompound.getInteger("EntityID");
        	if(id != 0) {
        		List list = world.getEntitiesWithinAABB(EntityFishing.class, player.boundingBox.expand(64D, 64D, 64D));
    			for (Object i : list) {
    				if(((EntityFishing)i).entityId == id)
    					notFound = false;
    			}
        	}
	
        	if(id == 0 || world.getEntityByID(id) == null || notFound) {        		
        		stack.stackTagCompound.setInteger("EntityID", bobber.entityId);
        		if (!world.isRemote) {
        			if (!player.capabilities.isCreativeMode) {
    					player.inventory.decrStackSize(baitSlot, 1);
    				}
        			
                	world.spawnEntityInWorld(new EntityFishing(world, player, baitQuality + 1));
                	PacketDispatcher.sendPacketToPlayer(new Packet103SetSlot(0, baitSlot + 36, player.inventory.getStackInSlot(baitSlot)), (Player) player);
                }
        	}

            player.swingItem();
        }

        return stack;
	}

	public static int[] getBait(EntityPlayer player, RodQuality quality) {
		int baitQuality = 0;
		int currentSlot = player.inventory.currentItem;
		int foundSlot = -1;

		if (currentSlot > 0) {
			int leftSlot = currentSlot - 1;

			if (player.inventory.getStackInSlot(leftSlot) != null) {
				if (Fishing.quality.canUseBait(player.inventory.getStackInSlot(leftSlot), quality)) {
					baitQuality = Fishing.bait.getBaitQuality(player.inventory.getStackInSlot(leftSlot));
					foundSlot = leftSlot;
				}
			}
		}

		if (foundSlot == -1 && currentSlot < 8) {
			int rightSlot = currentSlot + 1;

			if (player.inventory.getStackInSlot(rightSlot) != null) {
				if (Fishing.quality.canUseBait(player.inventory.getStackInSlot(rightSlot), quality)) {
					baitQuality = Fishing.bait.getBaitQuality(player.inventory.getStackInSlot(rightSlot));
					foundSlot = rightSlot;
				}
			}
		}
		
		return new int[] { baitQuality, foundSlot };
	}

	@Override
	public void addBaitList(List list, RodQuality quality) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			list.add(Text.INDIGO + StatCollector.translateToLocal("mariculture.string.bait"));
			
			ArrayList<FishingRod> lootTmp = (ArrayList<FishingRod>) RodQualityHandler.getCanUseList().clone();
			for (FishingRod loot : lootTmp) {
				if (loot.enumQuality== quality) {
					list.add(loot.itemStack.getItem().getItemDisplayName(loot.itemStack));
				}
			}
		} else {
			list.add(Text.INDIGO + StatCollector.translateToLocal("mariculture.string.shift.hold") + " " +
					Text.WHITE + StatCollector.translateToLocal("mariculture.string.shift.shift") + " " + 
					Text.INDIGO + StatCollector.translateToLocal("mariculture.string.shift.rod"));
		}
	}
}
