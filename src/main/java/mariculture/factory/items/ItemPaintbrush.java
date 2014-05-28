package mariculture.factory.items;

import mariculture.core.Core;
import mariculture.core.items.ItemDamageable;
import mariculture.core.lib.AirMeta;
import mariculture.core.util.Rand;
import mariculture.factory.Factory;
import mariculture.factory.tile.TileCustom;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemPaintbrush extends ItemDamageable {
	public ItemPaintbrush(int dmg) {
		super(dmg);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound()) {
			Item item = Item.getItemFromBlock(((Block)Block.blockRegistry.getObject(stack.stackTagCompound.getString("Block"))));
			if(item != null) {
				return super.getItemStackDisplayName(stack) + " - " + item.getItemStackDisplayName(new ItemStack(item, 1, stack.stackTagCompound.getInteger("Meta")));
			}
		}
		
		return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.isSneaking()) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			} else if(world.isRemote) {
				stack.stackTagCompound.setBoolean("Refresh", !stack.stackTagCompound.getBoolean("Refresh"));
			}
			
			stack.stackTagCompound.setString("Block", Block.blockRegistry.getNameForObject(Core.air));
			stack.stackTagCompound.setInteger("Meta", AirMeta.FAKE_AIR);
			stack.stackTagCompound.setInteger("Side", 0);
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
		
		if(player.isSneaking() && world.getBlock(x, y, z) != Factory.customGate) {
			if(world.getTileEntity(x, y, z) instanceof TileCustom) {
				TileCustom tile = (TileCustom) world.getTileEntity(x, y, z);
				stack.stackTagCompound.setString("Block", Block.blockRegistry.getNameForObject(tile.theBlocks(side)));
				stack.stackTagCompound.setInteger("Meta", tile.theBlockMetas(side));
				stack.stackTagCompound.setInteger("Side", tile.theBlockSides(side));
			} else {
				String block = Block.blockRegistry.getNameForObject(world.getBlock(x, y, z));
				int meta = world.getBlockMetadata(x, y, z);
				stack.stackTagCompound.setString("Block", block);
				stack.stackTagCompound.setInteger("Meta", meta);
				stack.stackTagCompound.setInteger("Side", side);
			}
		} else {
			if (world.getTileEntity(x, y, z) instanceof TileCustom) {
				Block block = Block.getBlockFromName(stack.stackTagCompound.getString("Block"));
				int meta = stack.stackTagCompound.getInteger("Meta");
				int texSide = stack.stackTagCompound.getInteger("Side");
				TileCustom tile = (TileCustom) world.getTileEntity(x, y, z);
				if(tile.setSide(side, block, meta, texSide)) {
					if(stack.attemptDamageItem(1, Rand.rand))
						stack.stackSize--;
				}
			}
		}
		
		return true;
	}
}
