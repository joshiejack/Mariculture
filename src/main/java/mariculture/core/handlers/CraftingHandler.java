package mariculture.core.handlers;

import mariculture.core.Core;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.diving.ItemArmorDiving;
import mariculture.diving.ItemArmorSnorkel;
import mariculture.fishery.Fishery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CraftingHandler implements ICraftingHandler {
    @Override
    public void onCrafting (EntityPlayer player, ItemStack stack, IInventory craftMatrix) {
    	if(Extra.SPAWN_BOOKS) {
    		//Spawn Diving Book on crafting of snorkel or diving pieces
    		if(Modules.diving.isActive()) {
    			if(stack.getItem() instanceof ItemArmorSnorkel || stack.getItem() instanceof ItemArmorDiving) {
	    			if(!player.getEntityData().hasKey("DivingBook")) {
	    				player.getEntityData().setBoolean("DivingBook", true);
	    				ItemStack book = new ItemStack(Core.guides, 1, GuideMeta.DIVING);
	    				if (!player.inventory.addItemStackToInventory(book)) {
	    					World world = player.worldObj;
	    					if(!world.isRemote) {
	    						SpawnItemHelper.spawnItem(world, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
	    					}
	    				}
	    			}
    			}
    		}
    		
    		//Spawn Fishing book on crafing of a reed rod
    		if(Modules.fishery.isActive()) {
    			if(stack.itemID == Fishery.rodReed.itemID) {
	    			if(!player.getEntityData().hasKey("FishingBook")) {
	    				player.getEntityData().setBoolean("FishingBook", true);
	    				ItemStack book = new ItemStack(Core.guides, 1, GuideMeta.FISHING);
	    				if (!player.inventory.addItemStackToInventory(book)) {
	    					World world = player.worldObj;
	    					if(!world.isRemote) {
	    						SpawnItemHelper.spawnItem(world, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
	    					}
	    				}
	    			}
    			}
    		}
    		
    		//Spawn Factory book on crafting of an iron wheel
    		if(Modules.factory.isActive()) {
    			if(stack.itemID == Core.craftingItems.itemID && stack.getItemDamage() == CraftingMeta.WHEEL) {
	    			if(!player.getEntityData().hasKey("FactoryBook")) {
	    				player.getEntityData().setBoolean("FactoryBook", true);
	    				ItemStack book = new ItemStack(Core.guides, 1, GuideMeta.MACHINES);
	    				if (!player.inventory.addItemStackToInventory(book)) {
	    					World world = player.worldObj;
	    					if(!world.isRemote) {
	    						SpawnItemHelper.spawnItem(world, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
	    					}
	    				}
	    			}
    			}
    		}
    	}
    }

    @Override
    public void onSmelting (EntityPlayer player, ItemStack item)
    {
    }
}
