package mariculture.core.handlers;

import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.magic.Magic;
import mariculture.magic.ResurrectionTracker;
import mariculture.plugins.compatibility.CompatBooks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTrackerHandler implements IPlayerTracker {
	@Override
	public void onPlayerLogin(EntityPlayer player) {	
		NBTTagCompound nbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		//Spawn Processing Book on Player Join
		if(Extra.SPAWN_BOOKS) {
			if(!nbt.hasKey("ProcessingBook")) {
				nbt.setBoolean("ProcessingBook", true);
				ItemStack book = new ItemStack(Core.guides, 1, GuideMeta.PROCESSING);
				if (!player.inventory.addItemStackToInventory(book)) {
					World world = player.worldObj;
					if(!world.isRemote) {
						SpawnItemHelper.spawnItem(world, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
					}
				}
			}
		}
		
		for(String str: CompatBooks.onWorldStart) {
			if(!nbt.hasKey(str + "Book")) {
				nbt.setBoolean(str + "Book", true);
				ItemStack book = CompatBooks.generateBook(str);
				if (!player.inventory.addItemStackToInventory(book)) {
					World world = player.worldObj;
					if(!world.isRemote) {
						SpawnItemHelper.spawnItem(world, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
					}
				}
			}
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		if(Modules.magic.isActive() && EnchantHelper.exists(Magic.resurrection)) {
			ResurrectionTracker.onPlayerRespawn(player);
		}
	}
}
