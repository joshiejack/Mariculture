package mariculture.core.handlers;

import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.ServerHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.magic.Magic;
import mariculture.magic.ResurrectionTracker;
import mariculture.magic.enchantments.EnchantmentResurrection;
import mariculture.plugins.compatibility.CompatBooks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTrackerHandler implements IPlayerTracker {
	@Override
	public void onPlayerLogin(EntityPlayer player) {
		//Spawn Processing Book on Player Join
		if(Extra.SPAWN_BOOKS) {
			if(!player.getEntityData().hasKey("ProcessingBook")) {
				player.getEntityData().setBoolean("ProcessingBook", true);
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
			if(!player.getEntityData().hasKey(str + "Book")) {
				player.getEntityData().setBoolean(str + "Book", true);
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
