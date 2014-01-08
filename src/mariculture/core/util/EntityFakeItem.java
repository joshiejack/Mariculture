package mariculture.core.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class EntityFakeItem extends Entity {	
    public EntityFakeItem(World world) {
        super(world);
        this.yOffset = 0.0F;
        this.setSize(0.35F, 0.35F);
    }

    public EntityFakeItem(World world, double x, double y, double z, ItemStack stack) {
		this(world);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.setEntityItemStack(stack);
	}

	protected void entityInit() {
    	getDataWatcher().addObjectByDataType(10, 5);
    }

    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public void onUpdate() {
    	return;
    }
    
    public ItemStack getEntityItem() {
		ItemStack itemstack = getDataWatcher().getWatchableObjectItemStack(10);
		if (itemstack == null) {
			if (worldObj != null) {
				//setDead();
				worldObj.getWorldLogAgent().logSevere("Item entity " + entityId + " has no item?!");
			}

			return new ItemStack(Block.stone);
		} else {
			return itemstack;
		}
	}

	public void setEntityItemStack(ItemStack stack) {
        getDataWatcher().updateObject(10, stack);
        getDataWatcher().setObjectWatched(10);
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
    	if (this.getEntityItem() != null) {
			nbt.setCompoundTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        NBTTagCompound tag = nbt.getCompoundTag("Item");
		setEntityItemStack(ItemStack.loadItemStackFromNBT(tag));
		ItemStack item = getDataWatcher().getWatchableObjectItemStack(10);
		if (item == null || item.stackSize <= 0) {
			setDead();
		}
    }
}
