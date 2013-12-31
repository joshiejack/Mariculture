package mariculture.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import mariculture.core.Mariculture;
import mariculture.core.handlers.KeyBindingHandler;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import mariculture.core.network.Packet107FLUDD;
import mariculture.core.network.Packet107FLUDD.PacketType;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.network.PacketDispatcher;

public class FactoryEvents {
	private int hoverTick = 0;
	private int jumpTick = 0;

	@ForgeSubscribe
	public void HarvestSpeed(BreakSpeed event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (event.block.blockID == Factory.customBlock.blockID || event.block.blockID == Factory.customFlooring.blockID
					|| event.block.blockID == Factory.customSlabs.blockID
					|| event.block.blockID == Factory.customSlabsDouble.blockID
					|| event.block.blockID == Factory.customStairs.blockID) {
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

	@ForgeSubscribe
	public void LivingUpdateEvent(LivingUpdateEvent event) {
		if (event.entity.worldObj.isRemote) {
			if (event.entity instanceof EntityPlayer) {

				EntityPlayer player = (EntityPlayer) event.entity;
				World world = event.entity.worldObj;

				if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, Factory.fludd)
						&& ItemArmorFLUDD.getMode(player.inventory.armorInventory[ArmorSlot.TOP]) == ItemArmorFLUDD.TURBO) {
					if (GameSettings.isKeyDown(KeyBindingHandler.fludd) && !player.isSneaking()) {
						if (player.isInWater()) {
							player.moveFlying(0.0F, 1.0F, 0.05F);
						} else if (player.onGround) {
							player.moveFlying(0.0F, 1.0F, 0.5F);
						} else {
							player.moveFlying(0.0F, 1.0F, 0.1F);
						}
						if (world.isRemote) {
							sendDamagePacket(player, ItemArmorFLUDD.TURBO);
						}

						playSmoke(ItemArmorFLUDD.TURBO, player, true);
					}
				}

				if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, Factory.fludd)
						&& ItemArmorFLUDD.getMode(player.inventory.armorInventory[ArmorSlot.TOP]) == ItemArmorFLUDD.ROCKET) {
					if (GameSettings.isKeyDown(KeyBindingHandler.fludd) && !player.isSneaking()) {
						if (jumpTick < 10) {
							player.addVelocity(0, 0.35, 0);

							if (world.isRemote) {
								sendDamagePacket(player, ItemArmorFLUDD.ROCKET);
							}

							playSmoke(ItemArmorFLUDD.ROCKET, player, true);
						}
					}
				}

				if (player.onGround) {
					jumpTick = 0;
				}

				hoverTick++;
				jumpTick++;

				if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, Factory.fludd)
						&& ItemArmorFLUDD.getMode(player.inventory.armorInventory[ArmorSlot.TOP]) == ItemArmorFLUDD.HOVER) {
					if (GameSettings.isKeyDown(KeyBindingHandler.fludd) && !player.isSneaking()) {
						if (hoverTick >= 128) {
							disableHover(player);
						} else {

							if (jumpTick < 10) {
								player.addVelocity(0, 0.01, 0);
								if (world.isRemote) {
									sendDamagePacket(player, ItemArmorFLUDD.HOVER);
								}
							}

							player.getEntityData().setBoolean("UsingFLUDDHover", true);
							player.capabilities.isFlying = true;
							player.capabilities.setFlySpeed(0.005F);
							player.motionY = -0.03F;
							if (!player.onGround && GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward)) {
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
					disableHover(player);
				}
			}
		}
	}

	public static boolean playSmoke(int mode, EntityPlayer player, boolean original) {
		switch (mode) {
		case ItemArmorFLUDD.HOVER:
			return playHover(player, original);
		case ItemArmorFLUDD.ROCKET:
			return playRocket(player, original);
		case ItemArmorFLUDD.TURBO:
			return playTurbo(player, original);
		default:
			return true;
		}
	}

	private static boolean playHover(final EntityPlayer player, final boolean original) {
		if (Extra.FLUDD_WATER_ON) {
			if (original) {
				sendWaterAnimateServer(ItemArmorFLUDD.HOVER, player);
			}
			for (float j = -0.1F; j < 0.15F; j = j + 0.05F) {
				int i = 0;
				while (player.worldObj.isAirBlock((int) player.posX, (int) player.posY - i, (int) player.posZ)) {
					player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + j, 0, -1D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j + 0.5F, player.posY - 0.23F - i, player.posZ + j, 0,
							-5D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j - 0.5F, player.posY - 0.23F - i, player.posZ + j, 0,
							-5D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + j + 0.5F, 0,
							-5D, 0);
					player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + j - 0.5F, 0,
							-5D, 0);
					i++;
				}
			}
		}

		return true;
	}

	private static boolean playRocket(final EntityPlayer player, final boolean original) {
		if (Extra.FLUDD_WATER_ON) {
			if (original) {
				sendWaterAnimateServer(ItemArmorFLUDD.ROCKET, player);
			}
			for (float k = -1F; k < 1.05F; k = k + 0.15F) {
				for (float j = -1F; j < 1.05F; j = j + 0.15F) {
					int i = 0;
					while (player.worldObj.isAirBlock((int) player.posX, (int) player.posY - i, (int) player.posZ)) {
						player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + k, 0, -1D,
								0);
						player.worldObj.spawnParticle("cloud", player.posX + j + 0.5F, player.posY - 0.23F - i, player.posZ + k,
								0, -5D, 0);
						player.worldObj.spawnParticle("cloud", player.posX + j - 0.5F, player.posY - 0.23F - i, player.posZ + k,
								0, -5D, 0);
						player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + k + 0.5F,
								0, -5D, 0);
						player.worldObj.spawnParticle("cloud", player.posX + j, player.posY - 0.23F - i, player.posZ + k - 0.5F,
								0, -5D, 0);
						i++;
					}
				}
			}
		}

		return true;
	}

	private static boolean playTurbo(EntityPlayer player, boolean original) {
		if (Extra.FLUDD_WATER_ON) {
			if (original) {
				sendWaterAnimateServer(ItemArmorFLUDD.TURBO, player);
			}
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

	private static void sendWaterAnimateServer(int mode, EntityPlayer player) {
		if (player instanceof EntityClientPlayerMP) {
			((EntityClientPlayerMP) player).sendQueue.addToSendQueue(new Packet107FLUDD(false, mode, player.entityId, PacketType.ANIMATE).build());
		}
	}

	private void disableHover(EntityPlayer player) {
		if (player.getEntityData().hasKey("UsingFLUDDHover")) {
			player.getEntityData().removeTag("UsingFLUDDHover");
			player.capabilities.isFlying = false;
			player.capabilities.setFlySpeed(0.02F);
		}
	}

	public static void activateSquirt(EntityPlayer player) {
		if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, Factory.fludd)
				&& ItemArmorFLUDD.getMode(player.inventory.armorInventory[ArmorSlot.TOP]) == ItemArmorFLUDD.SQUIRT) {
			((EntityClientPlayerMP) player).sendQueue.addToSendQueue(new Packet107FLUDD(false, 0, player.entityId, PacketType.SQUIRT).build());
		}
	}
	
	private static void sendDamagePacket(EntityPlayer player, int mode) {
		if (player instanceof EntityClientPlayerMP) {
			((EntityClientPlayerMP) player).sendQueue.addToSendQueue(new Packet107FLUDD(false, mode, player.entityId, PacketType.DAMAGE).build());
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

	public static ItemStack handleFill(ItemStack item, boolean doFill, int amount) {
		if (item.getItem() instanceof ItemArmorFLUDD) {
			ItemStack fludd = item.copy();
			int water = 0;
			if (fludd.hasTagCompound()) {
				water = fludd.stackTagCompound.getInteger("water");
			}

			if (water + amount < ItemArmorFLUDD.STORAGE) {
				if (doFill) {
					fludd.stackTagCompound.setInteger("water", water + amount);
				}

				return fludd;
			}
		}

		return null;
	}
}