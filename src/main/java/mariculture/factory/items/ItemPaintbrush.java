package mariculture.factory.items;

import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.items.ItemDamageable;
import mariculture.core.lib.AirMeta;
import mariculture.core.util.Rand;
import mariculture.factory.Factory;
import mariculture.factory.blocks.TileCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPaintbrush extends ItemDamageable {
	public ItemPaintbrush(int i, int dmg) {
		super(i, dmg);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		String name = StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name");
		
		if(stack.hasTagCompound()) {
			if(Item.itemsList[stack.stackTagCompound.getInteger("BlockID")] != null) {
				ItemStack block = new ItemStack(stack.stackTagCompound.getInteger("BlockID"), 1, stack.stackTagCompound.getInteger("BlockMeta"));
				name = name + " - " + BlockHelper.getName(block);
				return name;
			}
		}
		
		
		return name;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.isSneaking()) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			} else if(world.isRemote) {
				stack.stackTagCompound.setBoolean("Refresh", !stack.stackTagCompound.getBoolean("Refresh"));
			}

			stack.stackTagCompound.setInteger("BlockID", Core.air.blockID);
			stack.stackTagCompound.setInteger("BlockMeta", AirMeta.FAKE_AIR);
			stack.stackTagCompound.setInteger("BlockSide", 0);
		}

        return stack;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		} else if(world.isRemote) {
			stack.stackTagCompound.setBoolean("Refresh", !stack.stackTagCompound.getBoolean("Refresh"));
		}

		if(player.isSneaking() && world.getBlockId(x, y, z) != Factory.customGate.blockID) {
			if(world.getBlockTileEntity(x, y, z) instanceof TileCustom) {
				TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
				stack.stackTagCompound.setInteger("BlockID", tile.theBlockIDs(side));
				stack.stackTagCompound.setInteger("BlockMeta", tile.theBlockMetas(side));
				stack.stackTagCompound.setInteger("BlockSide", tile.theBlockSides(side));
			} else {
				int id = world.getBlockId(x, y, z);
				int meta = world.getBlockMetadata(x, y, z);
				stack.stackTagCompound.setInteger("BlockID", id);
				stack.stackTagCompound.setInteger("BlockMeta", meta);
				stack.stackTagCompound.setInteger("BlockSide", side);
			}
		} else {
			if (world.getBlockTileEntity(x, y, z) instanceof TileCustom) {
				int id = stack.stackTagCompound.getInteger("BlockID");
				int meta = stack.stackTagCompound.getInteger("BlockMeta");
				int texSide = stack.stackTagCompound.getInteger("BlockSide");
				TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
				if(tile.setSide(side, id, meta, texSide)) {
					if(stack.attemptDamageItem(1, Rand.rand))
						stack.stackSize--;
				}
			}
		}

		return true;
	}
}
