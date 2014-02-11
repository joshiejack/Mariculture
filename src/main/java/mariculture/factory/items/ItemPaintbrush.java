package mariculture.factory.items;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.items.ItemDamageable;
import mariculture.core.lib.AirMeta;
import mariculture.factory.Factory;
import mariculture.factory.blocks.TileCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPaintbrush extends ItemDamageable {
	public ItemPaintbrush(int dmg) {
		super(dmg);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String name = StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name");
		
		if(stack.hasTagCompound()) {
			if(Items.itemsList[stack.stackTagCompound.getInteger("BlockID")] != null) {
				ItemStack block = new ItemStack(stack.stackTagCompound.getInteger("BlockID"), 1, stack.stackTagCompound.getInteger("BlockMeta"));
				name = name + " - " + BlockHelper.getName(block);
				return name;
			}
		}
		
		
		return name;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		boolean custom = false;
		if (stack.hasTagCompound()) {
			int id = stack.stackTagCompound.getInteger("BlockID");
			int meta = stack.stackTagCompound.getInteger("BlockMeta");
			int sideTexture = stack.stackTagCompound.getInteger("BlockSide");

			if (id > 0) {
				if (world.getTileEntity(x, y, z) != null) {
					if (world.getTileEntity(x, y, z) instanceof TileCustom) {
						int blockID = world.getBlockId(x, y, z);
						if (!player.isSneaking() && blockID != Factory.customGate.blockID || (player.isSneaking() && blockID == Factory.customGate.blockID)) {
							TileCustom tile = (TileCustom) world.getTileEntity(x, y, z);
							if(tile.setSide(side, id, meta, sideTexture)) {
								stack.attemptDamageItem(1, new Random());
							}

							if (world.isRemote) {
								Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(x, y, z);
							}
						}

						custom = true;

						if (blockID == Factory.customGate.blockID) {
							custom = false;
						}
					}
				}
			}
		}

		if (player.isSneaking()) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}

			if (world.getBlockId(x, y, z) != Factory.customGate.blockID) {
				int newID;
				int newMeta;
				int prevID = stack.stackTagCompound.getInteger("BlockID");
				int prevMeta = stack.stackTagCompound.getInteger("BlockMeta");
				int prevSide = stack.stackTagCompound.getInteger("BlockSide");
				if (custom) {
					stack.stackTagCompound.setInteger("BlockID", Core.airBlocks.blockID);
					stack.stackTagCompound.setInteger("BlockMeta", AirMeta.FAKE_AIR);
					stack.stackTagCompound.setInteger("BlockSide", 0);
					newID = Core.airBlocks.blockID;
					newMeta = AirMeta.FAKE_AIR;
				} else {
					newID = world.getBlockId(x, y, z);
					newMeta = world.getBlockMetadata(x, y, z);
					stack.stackTagCompound.setInteger("BlockID", newID);
					stack.stackTagCompound.setInteger("BlockMeta", newMeta);
					stack.stackTagCompound.setInteger("BlockSide", side);
				}
			
				if(newID != prevID || newMeta != prevMeta) {
					stack.attemptDamageItem(1, new Random());
				}
			}
		}

		if (world.isRemote && player.isSneaking()) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}

			if (stack.stackTagCompound.getBoolean("update") == false) {
				stack.stackTagCompound.setBoolean("update", true);
			} else {
				stack.stackTagCompound.setBoolean("update", false);
			}
		}

		return true;
	}
}
