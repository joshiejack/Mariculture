package mariculture.plugins.bloodmagic;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodQuality;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.Extra;
import mariculture.core.util.Rand;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemRod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.ModItems;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBoundRod extends ItemRod {
	private Icon passiveIcon;

	public ItemBoundRod(int i, RodQuality quality) {
		super(i, quality);
	}

	public int[] getShard(EntityPlayer player) {
		int currentSlot = player.inventory.currentItem;
		int leftSlot = currentSlot - 1;
		if (leftSlot == -1)
			leftSlot = 8;
		int rightSlot = currentSlot + 1;
		if (rightSlot == 8)
			rightSlot = 0;
		
		int left = hasDemonShard(player, leftSlot);
		if(left > 0) {
			return new int[] { leftSlot, left };
		} else {
			int right = hasDemonShard(player, rightSlot);
			if(right > 0) {
				return new int[] { rightSlot, right };
			}
		}
		
		return null;
	}

	public int hasDemonShard(EntityPlayer player, int slot) {
		ItemStack stack = player.inventory.getStackInSlot(slot);
		if(stack != null) {
			return stack.itemID == ModItems.weakBloodShard.itemID ? Extra.WEAK_FISH_LIMIT: stack.itemID == ModItems.demonBloodShard.itemID? Extra.DEMON_FISH_LIMIT: -1;
		} else return -1;
	}

	// Catches the fish and returns how many were caught
	private int catchFish(World world, EntityPlayer player, int maximum) {
		boolean lightning = false;
		int catches = 0;
		for (int x = -3; x <= 3; x++) {
			for (int y = -3; y <= 3; y++) {
				for (int z = -3; z <= 3; z++) {
					int x2 = (int) (x + player.posX);
					int y2 = (int) (y + player.posY);
					int z2 = (int) (z + player.posZ);

					if (!lightning && Rand.nextInt(2048)) {
						lightning = true;
						world.addWeatherEffect(new EntityLightningBolt(world, x2, y2, z2));
					}

					if (BlockHelper.isWater(world, x2, y2, z2) && Rand.nextInt(5)) {
						ItemStack loot = Fishing.loot.getLoot(Rand.rand, quality, world, x2, y2, z2);
						if (!world.isRemote) {
							float f = 0.7F;
							double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
							double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
							double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
							EntityItem entityitem = new EntityItem(world, (double) x2 + d0, (double) y2 + d1, (double) z2 + d2, loot);
							entityitem.motionX = world.rand.nextFloat() * f * 0D;
							entityitem.motionY = world.rand.nextFloat() * f * 1.5F;
							entityitem.motionZ = world.rand.nextFloat() * f * 0D;
							if (loot.getItem() instanceof ItemFishy)
								entityitem.lifespan = 60;
							entityitem.delayBeforeCanPickup = 0;
							world.spawnEntityInWorld(entityitem);
							
							player.addStat(StatList.fishCaughtStat, 1);
							player.worldObj.spawnEntityInWorld(new EntityXPOrb(world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, world.rand.nextInt(6) + 1));
						}

						catches++;

						if (catches > maximum) {
							return catches;
						}
					}
				}
			}
		}

		return catches;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		EnergyItems.checkAndSetItemOwner(stack, player);

		if (player.isPotionActive(AlchemicalWizardry.customPotionInhibit)) {
			return stack;
		}

		if (player.isSneaking()) {
			this.setActivated(stack, !getActivated(stack));
			stack.stackTagCompound.setInteger("worldTimeDelay", (int) (world.getWorldTime() - 1) % 200);
		} else {
			if (!getActivated(stack))
				return Fishing.rodHandler.handleRightClick(stack, world, player, quality, Rand.rand);
			else {
				int[] nums = getShard(player);
				if (nums != null) {
					int slot = nums[0];
					int count = nums[1];
					int catches = catchFish(world, player, count);
					if (!world.isRemote) {
						if (!player.capabilities.isCreativeMode) {
							player.inventory.decrStackSize(slot, 1);
						}

						PacketDispatcher.sendPacketToPlayer(new Packet103SetSlot(0, slot + 36, player.inventory.getStackInSlot(slot)), (Player) player);
					}

					EnergyItems.syphonBatteries(stack, player, 500 * catches);
				}
			}
		}

		return stack;
	}

	public void setActivated(ItemStack stack, boolean newActivated) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt == null) {
			stack.setTagCompound(new NBTTagCompound());
		}

		nbt.setBoolean("isActive", newActivated);
	}

	public boolean getActivated(ItemStack stack) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if (nbt == null) {
			stack.setTagCompound(new NBTTagCompound());
		}

		return nbt.getBoolean("isActive");
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Let the rivers flow red with blood");

		if (!(stack.stackTagCompound == null)) {
			if (stack.stackTagCompound.getBoolean("isActive")) {
				list.add("Activated");
			} else {
				list.add("Deactivated");
			}

			if (!stack.stackTagCompound.getString("ownerName").equals("")) {
				list.add("Current owner: " + stack.stackTagCompound.getString("ownerName"));
			}
		}

		super.addInformation(stack, player, list, bool);
	}

	@Override
	public boolean canFish(World world, int x, int y, int z, EntityPlayer player, ItemStack stack) {
		if(stack.hasTagCompound()) {
			EntityPlayer entityOwner = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(stack.stackTagCompound.getString("ownerName"));
			return entityOwner != null;
		} else return false;
	}

	@Override
	public ItemStack damage(World world, EntityPlayer player, ItemStack stack, int fish) {
		if (player != null)
			EnergyItems.syphonBatteries(stack, player, fish * 250);
		else {
			if (!EnergyItems.syphonWhileInContainer(stack, fish * 250)) {
				if (!world.isRemote) {
					EntityPlayer entityOwner = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(stack.stackTagCompound.getString("ownerName"));
					if(entityOwner != null) {
						entityOwner.addPotionEffect(new PotionEffect(Potion.confusion.id, 500));
					}
				}
			}
		}

		return stack;
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) 7.0F, 0));
		return multimap;
	}
	
	@Override
	public float getDamage() {
		return 4.0F;
	}

	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound tag = stack.stackTagCompound;
		if (tag.getBoolean("isActive")) {
			return this.itemIcon;
		} else {
			return this.passiveIcon;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		super.registerIcons(iconRegister);
		passiveIcon = iconRegister.registerIcon(Mariculture.modid + ":rodBlood_deactivated");
	}
}
