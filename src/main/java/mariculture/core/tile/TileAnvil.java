package mariculture.core.tile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.core.IAnvilHandler;
import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.NBTHelper;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileStorage;
import mariculture.core.util.IFaceable;
import mariculture.core.util.Rand;
import mariculture.magic.JewelryHandler;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAnvil extends TileStorage implements ISidedInventory, IAnvilHandler, IFaceable {
	private static final HashMap<String, RecipeAnvil> recipes = new HashMap();
	public ForgeDirection orientation = ForgeDirection.EAST;
	public TileAnvil() {
		inventory = new ItemStack[1];
	}
	
	@Override
	public void addRecipe(RecipeAnvil recipe) {
		recipes.put(OreDicHelper.convert(recipe.input), recipe);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		
		if(!worldObj.isRemote) {
			PacketHandler.syncInventory(this, inventory);
		}
	}
	
	@Override
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
	public boolean canBeWorked(ItemStack stack) {
		if(stack.getItem() instanceof ItemWorked)
			return true;
		RecipeAnvil result = (RecipeAnvil) recipes.get(OreDicHelper.convert(stack));
		return result != null;
	}
	
	public boolean canBeRepaired(ItemStack stack) {
		if (stack.isItemStackDamageable() && stack.isItemDamaged() && stack.getItem().isRepairable()) {
			if (stack.getItemDamage() > 0) {
				return true;
			}
		} else if (stack.getItem() instanceof ItemJewelry && stack.getItemDamage() > 0) {
			return true;
		}
		
		return false;
	}
	
	public boolean workItem(EntityPlayer player, ItemStack hammer) {
		if (hammer == null)
			return false;
		ItemStack stack = getStackInSlot(0);
		if(stack == null)
			return false;
		if(!canBeWorked(stack)) {
			int modifier = 1;
			if(stack.isItemEnchanted()) {
				LinkedHashMap<Integer, Integer> maps = (LinkedHashMap<Integer, Integer>) EnchantmentHelper.getEnchantments(stack);
				for(Entry<Integer, Integer> i: maps.entrySet()) {
					int total = maps.entrySet().iterator().next().getValue();
					Enchantment enchant = Enchantment.enchantmentsList[maps.keySet().iterator().next().intValue()];
					int bonus = (enchant.getMaxEnchantability(1) - enchant.getMinEnchantability(1));
					modifier += (total + bonus);
				}
				
				modifier /= 3;
				modifier = (modifier >= 1)? modifier: 1;
				
				if(stack.getItem() instanceof  ItemJewelry) {
                    modifier *= JewelryHandler.getMaterial(stack).getRepairModifier(JewelryHandler.getType(stack));
                }
			}
			
			float effiency = 1.0F - EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, hammer) /25;
			float drop = (((1.0F / (player.xpBarCap() * 1)) /4) * modifier) * effiency;
			if((player.experience >= drop || player.experienceLevel > 0) && canBeRepaired(stack)) {
				for(int i = 0; i < EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, hammer); i++) {
					if(Rand.nextInt(3)) {
						stack.setItemDamage(stack.getItemDamage() - 1);
					}
				}
				
				stack.setItemDamage(stack.getItemDamage() - 1);
				if(stack.getItemDamage() < 0) stack.setItemDamage(0);
				float experience = player.experience - drop;
				if(experience <= 0.0F) {
					player.experience = 1.0F;
					player.experienceLevel -= 1;
				} else {
					player.experience = experience;
				}
		        
				if(stack.getItemDamage() == 0)
					worldObj.spawnParticle("hugeexplosion", xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0, 0, 0);
				else
					worldObj.spawnParticle("explode", xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0, 0, 0);
				if(worldObj.isRemote)
					worldObj.playSoundAtEntity(player, Mariculture.modid + ":hammer", 1.0F, 1.0F);
				
				return true;
			}
			
			return false;
		}
		
		if(!(stack.getItem() instanceof ItemWorked)) {
			RecipeAnvil recipe = ((RecipeAnvil) recipes.get(OreDicHelper.convert(stack)));
			setInventorySlotContents(0, createWorkedItem(recipe.output.copy(), recipe.hits));
			worldObj.playSoundAtEntity(player, Mariculture.modid + ":hammer", 1.0F, 1.0F);
			return true;
		} else {
			int workedVal = stack.stackTagCompound.getInteger("Worked") + 1;
			stack.stackTagCompound.setInteger("Worked", workedVal);
			if(workedVal >= stack.stackTagCompound.getInteger("Required")) {
				ItemStack result = NBTHelper.getItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
				if(Modules.isActive(Modules.magic)) {
					result = JewelryHandler.finishJewelry(stack, result, Rand.rand);
				}
				
				setInventorySlotContents(0, result);
				
				worldObj.spawnParticle("explode", xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0, 0, 0);
				worldObj.playSoundAtEntity(player, Mariculture.modid + ":bang", 1.0F, 1.0F);
				return true;
			}
			
			worldObj.spawnParticle("explode", xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0, 0, 0);
			worldObj.playSoundAtEntity(player, Mariculture.modid + ":hammer", 1.0F, 1.0F);
			
			return true;
		}
	}
	
	@Override
	public ItemStack createWorkedItem(ItemStack output, int hits) {
		ItemStack worked = new ItemStack(Core.worked);
		worked.setTagCompound(new NBTTagCompound());
		worked.stackTagCompound.setInteger("Worked", 0);
		worked.stackTagCompound.setInteger("Required", hits);
		worked.stackTagCompound.setTag("WorkedItem", NBTHelper.writeItemStackToNBT(new NBTTagCompound(), output));
		return worked;
	}
	
	@Override
	public boolean rotate() {
		setFacing(BlockHelper.rotate(orientation, 4));
		return true;
	}
	
	@Override
	public ForgeDirection getFacing() {
		return this.orientation;
	}
	
	@Override
	public void setFacing(ForgeDirection dir) {
		this.orientation = dir;
		if(!worldObj.isRemote) {
			PacketHandler.updateOrientation(this);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return side == ForgeDirection.UP.ordinal() && inventory[0] == null;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		if(stack.getItem() instanceof ItemWorked)
			return side == ForgeDirection.UP.ordinal();
		return true;
	}

	public ItemStack[] getInventory() {
		return inventory;
	}

	@Override
	public HashMap<String, RecipeAnvil> getRecipes() {
		return recipes;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Orientation", orientation.ordinal());
	}
}
