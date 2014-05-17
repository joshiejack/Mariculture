package mariculture.fishery.blocks;

import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.items.ItemMariculture;
import mariculture.fishery.Fish;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class BlockItemNet extends ItemMariculture {
	public BlockItemNet() {
		setHasSubtypes(false);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition object = getMovingObjectPositionFromPlayer(world, player, true);

		if (object == null) {
			return stack;
		} else {
			if (object.typeOfHit == MovingObjectType.BLOCK) {
				int x = object.blockX;
				int y = object.blockY;
				int z = object.blockZ;

				if (!world.canMineBlock(player, x, y, z)) {
					return stack;
				}

				if (!player.canPlayerEdit(x, y, z, object.sideHit, stack)) {
					return stack;
				}

				if (world.getBlock(x, y, z).getMaterial() == Material.water  && world.getBlockMetadata(x, y, z) == 0 && world.isAirBlock(x, y + 1, z)) {
					world.setBlock(x, y + 1, z, Core.ticking);

					if (!player.capabilities.isCreativeMode) {
						--stack.stackSize;
					}
				}
			}

			return stack;
		}
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)  {
		if(!entity.worldObj.isRemote) {
			if(entity instanceof EntitySquid) {
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
				SpawnItemHelper.spawnItem(entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ, Fishing.fishHelper.makePureFish(Fish.squid));
				entity.worldObj.spawnParticle("bubble", entity.posX, entity.posY, entity.posZ, 0, 0, 0);
				entity.setDead();
			}
		}
		
        return false;
    }
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName();
	}
	
	@Override 
	public String getName(ItemStack stack) {
		return "net";
	}
}
