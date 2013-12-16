package mariculture.fishery.blocks;

import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.core.lib.MachineSpeeds;
import mariculture.fishery.TankHelper;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileNet extends TileEntity {
	private int tick = 0;
	private final int lifeLeft = 0;
	private final int lifeLength = 1000;
	private final Random rand = new Random();

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		tick = tagCompound.getInteger("Tick");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("Tick", this.tick);
	}

	private void doWork() {

		int nets = 0;

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (this.worldObj.getBlockTileEntity(this.xCoord + i, this.yCoord, this.zCoord + j) instanceof TileNet) {
					nets++;
				}
			}
		}

		int chance = 100 - (nets * 10);
		if (rand.nextInt(chance) == 1) {
			ItemStack loot = Fishing.loot.getLoot(rand, EnumRodQuality.OLD, this.worldObj, this.xCoord, this.yCoord,
					this.zCoord);
			if (loot != null && loot.getItem() instanceof ItemFishy) {
				EntityItem dropped = new EntityItem(this.worldObj, this.xCoord + 0.5D, this.yCoord + 1, this.zCoord + 0.5D,
						loot);
				this.worldObj.spawnEntityInWorld(dropped);
			}
		}
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			tick++;

			if (tick == MachineSpeeds.getNetSpeed()) {
				tick = 0;
				doWork();
			}
		}
	}
}
