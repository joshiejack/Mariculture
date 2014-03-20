package mariculture.factory;

import mariculture.Mariculture;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import mariculture.core.network.PacketFLUDD;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.items.ItemArmorFLUDD.Mode;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FactoryEvents {
	private int hoverTick = 0;
	private int jumpTick = 0;

	@SubscribeEvent
	public void HarvestSpeed(BreakSpeed event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if(Item.getItemFromBlock(event.block) == Item.getItemFromBlock(Factory.customBlock)) {
				if (player.getCurrentEquippedItem() != null) {
					if (player.getCurrentEquippedItem().getItem() instanceof ItemPickaxe) {
						event.newSpeed = event.originalSpeed * 3F;
					}

					if (player.getCurrentEquippedItem().getItem() instanceof ItemAxe) {
						event.newSpeed = event.originalSpeed * 2F;
					}
					
					if(player.getCurrentEquippedItem().getItem() instanceof ItemFishy) {
						event.newSpeed = event.originalSpeed * 5F;
					}
				}
			}
		}
	}
	
	public static Mode getArmorMode(EntityPlayer player) {
		if(!PlayerHelper.hasArmor(player, ArmorSlot.TOP, Factory.fludd)) return Mode.NONE;
		return ItemArmorFLUDD.getMode(player.inventory.armorInventory[ArmorSlot.TOP]);
	}

	@SubscribeEvent
	public void LivingUpdateEvent(LivingUpdateEvent event) {
		World world = event.entity.worldObj;
		if (world.isRemote) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				if(ClientHelper.isActivateKeyPressed() && !player.isSneaking()) {
					//Turbo Mode
					if (getArmorMode(player) == Mode.TURBO) {
						if (player.isInWater()) {
							player.moveFlying(0.0F, 1.0F, 0.05F);
						} else if (player.onGround) {
							player.moveFlying(0.0F, 1.0F, 0.5F);
						} else {
							player.moveFlying(0.0F, 1.0F, 0.1F);
						}
							
						Mariculture.packets.sendToServer(new PacketFLUDD(PacketFLUDD.DAMAGE, ItemArmorFLUDD.TURBO));
						playSmoke(ItemArmorFLUDD.TURBO, player, true);
					}
	
					//Rocket Mode
					if (getArmorMode(player) == Mode.ROCKET) {
						if (jumpTick < 10) {
							player.addVelocity(0, 0.35, 0);
							player.fallDistance = 0F;
							Mariculture.packets.sendToServer(new PacketFLUDD(PacketFLUDD.DAMAGE, ItemArmorFLUDD.ROCKET));
							playSmoke(ItemArmorFLUDD.ROCKET, player, true);
						}
					}
	
					if (player.onGround) {
						jumpTick = 0;
					}
	
					hoverTick++;
					jumpTick++;
	
					//Hover Mode
					if (getArmorMode(player) == Mode.HOVER) {
						if (hoverTick >= 64) {
							disableHover(player);
						} else {
							if(player.onGround) player.motionY = 10;
							if (jumpTick < 10) {
								player.addVelocity(0, 0.01, 0);
								Mariculture.packets.sendToServer(new PacketFLUDD(PacketFLUDD.DAMAGE, ItemArmorFLUDD.HOVER));
							}
	
							player.fallDistance = 0F;
							player.getEntityData().setBoolean("UsingFLUDDHover", true);
							player.capabilities.isFlying = true;
							player.capabilities.setFlySpeed(0.005F);
							player.motionY = ClientHelper.isForwardPressed()? 0F: ClientHelper.isJumpPressed()? 0.08F: 0.05F;
							if (!player.onGround && ClientHelper.isForwardPressed()) {
								player.moveFlying(0.0F, 1.0F, 0.03F);
							}
	
							playSmoke(ItemArmorFLUDD.HOVER, player, true);
						}
					} else {
						if (player.onGround) {
							hoverTick = 0;
						}
	
						disableHover(player);
					}
				} else {
					if (player.onGround) {
						hoverTick = 0;
					}

					disableHover(player);
				}
			}
		}
	}

	public static boolean playSmoke(int mode, EntityPlayer player, boolean isSender) {
		switch (mode) {
		case ItemArmorFLUDD.HOVER:
			return playHover(player, isSender);
		case ItemArmorFLUDD.ROCKET:
			return playRocket(player, isSender);
		case ItemArmorFLUDD.TURBO:
			return playTurbo(player, isSender);
		default:
			return true;
		}
	}

	private static boolean playHover(EntityPlayer player, boolean isSender) {
		if (Extra.FLUDD_WATER_ON) {
			if(isSender) Mariculture.packets.sendToServer(new PacketFLUDD(PacketFLUDD.ANIMATE, ItemArmorFLUDD.HOVER));
			for (float j = -0.1F; j < 0.15F; j = j + 0.05F) {
				double i = 0.2D;
				while (player.worldObj.isAirBlock((int) player.posX, (int) (player.posY - i), (int) player.posZ)) {
					player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + j, 0, -1D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j + 0.5F, player.posY - 0.23F - i, player.posZ + j, 0, -5D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j - 0.5F, player.posY - 0.23F - i, player.posZ + j, 0, -5D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + j + 0.5F, 0, -5D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + j - 0.5F, 0, -5D, 0);
					i++;
				}
			}
		}

		return true;
	}

	private static boolean playRocket(EntityPlayer player, boolean isSender) {
		if (Extra.FLUDD_WATER_ON) {
			boolean send = false;
			if(isSender) Mariculture.packets.sendToServer(new PacketFLUDD(PacketFLUDD.ANIMATE, ItemArmorFLUDD.ROCKET));
			for (float k = -1F; k < 1.05F; k = k + 0.15F) {
				for (float j = -1F; j < 1.05F; j = j + 0.15F) {
					int i = 0;
					if(send) {
						while (player.worldObj.isAirBlock((int) player.posX, (int) player.posY - i, (int) player.posZ)) {
							player.worldObj.spawnParticle("explode", player.posX + j, player.posY - 0.23F - i, player.posZ + k, 0, -1D, 0);
							player.worldObj.spawnParticle("explode", player.posX + j + 0.5F, player.posY - 0.23F - i, player.posZ + k, 0, -5D, 0);
							player.worldObj.spawnParticle("explode", player.posX + j - 0.5F, player.posY - 0.23F - i, player.posZ + k, 0, -5D, 0);
							player.worldObj.spawnParticle("explode", player.posX + j, player.posY - 0.23F - i, player.posZ + k + 0.5F, 0, -5D, 0);
							player.worldObj.spawnParticle("explode", player.posX + j, player.posY - 0.23F - i, player.posZ + k - 0.5F, 0, -5D, 0);
							i++;
						}
					}
					
					send = !send;
				}
			}
		}

		return true;
	}

	private static boolean playTurbo(EntityPlayer player, boolean isSender) {
		if (Extra.FLUDD_WATER_ON) {
			if(isSender) Mariculture.packets.sendToServer(new PacketFLUDD(PacketFLUDD.ANIMATE, ItemArmorFLUDD.TURBO));
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
			player.worldObj.spawnParticle("cloud", player.posX, player.posY + 0.8F, player.posZ, 0, -1D, 0);
		}
		return true;
	}

	private void disableHover(EntityPlayer player) {
		if (player.getEntityData().hasKey("UsingFLUDDHover")) {
			player.getEntityData().removeTag("UsingFLUDDHover");
			player.capabilities.isFlying = false;
			player.capabilities.setFlySpeed(0.02F);
		}
	}
	
	public static void damageFLUDD(EntityPlayer player, int mode) {
		if (player.inventory.armorInventory[ArmorSlot.TOP] != null) {
			ItemStack armor = player.inventory.armorInventory[ArmorSlot.TOP];
			if (armor.getItem() instanceof ItemArmorFLUDD) {
				int water = 0;
				if (armor.hasTagCompound()) {
					water = armor.stackTagCompound.getInteger("water");
				}

				if (water > 0) {
					player.inventory.armorInventory[ArmorSlot.TOP].stackTagCompound.setInteger("water", water - 1);
					player.inventory.armorInventory[ArmorSlot.TOP].stackTagCompound.setInteger("mode", mode);
				}
			}
		}
	}
}