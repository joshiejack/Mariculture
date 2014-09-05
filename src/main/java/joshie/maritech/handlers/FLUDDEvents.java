package joshie.maritech.handlers;

import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.helpers.KeyHelper;
import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.lib.ArmorSlot;
import joshie.mariculture.core.network.PacketHandler;
import joshie.maritech.extensions.config.ExtensionGeneralStuff;
import joshie.maritech.extensions.modules.ExtensionFactory;
import joshie.maritech.items.ItemFLUDD;
import joshie.maritech.items.ItemFLUDD.Mode;
import joshie.maritech.network.PacketFLUDD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FLUDDEvents {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyPress(KeyInputEvent event) {
        EntityPlayer player = ClientHelper.getPlayer();
        if (KeyHelper.isActivateKeyPressed()) {
            if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, ExtensionFactory.fludd)) {
                if (player.isSneaking()) {
                    ItemStack stack = PlayerHelper.getArmorStack(player, ArmorSlot.TOP);
                    if (stack.hasTagCompound()) {
                        int mode = stack.stackTagCompound.getInteger("mode");
                        mode++;
                        if (mode > 3) {
                            mode = 0;
                        }

                        ClientHelper.addToChat(StatCollector.translateToLocal("mariculture.string.fludd.mode." + mode));
                        stack.stackTagCompound.setInteger("mode", mode);
                    } else {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                } else if (getArmorMode(player) == Mode.SQUIRT) {
                    PacketHandler.sendToServer(new PacketFLUDD(PacketFLUDD.SQUIRT, ItemFLUDD.SQUIRT));
                }
            }
        }
    }

    private int hoverTick = 0;
    private int jumpTick = 0;

    public static Mode getArmorMode(EntityPlayer player) {
        if (!PlayerHelper.hasArmor(player, ArmorSlot.TOP, ExtensionFactory.fludd)) return Mode.NONE;
        return ItemFLUDD.getMode(player.inventory.armorInventory[ArmorSlot.TOP]);
    }

    @SubscribeEvent
    public void LivingUpdateEvent(LivingUpdateEvent event) {
        World world = event.entity.worldObj;
        if (world.isRemote) {
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                if (KeyHelper.isActivateKeyPressed() && !player.isSneaking()) {
                    Mode mode = getArmorMode(player);
                    if (mode == Mode.TURBO) {
                        if (player.isInWater()) {
                            player.moveFlying(0.0F, 1.0F, 0.05F);
                        } else if (player.onGround) {
                            player.moveFlying(0.0F, 1.0F, 0.5F);
                        } else {
                            player.moveFlying(0.0F, 1.0F, 0.1F);
                        }

                        PacketHandler.sendToServer(new PacketFLUDD(PacketFLUDD.DAMAGE, ItemFLUDD.TURBO));
                        playSmoke(ItemFLUDD.TURBO, player, true);
                    }

                    //Rocket Mode
                    if (mode == Mode.ROCKET) {
                        if (jumpTick < 10) {
                            player.addVelocity(0, 0.35, 0);
                            player.fallDistance = 0F;
                            PacketHandler.sendToServer(new PacketFLUDD(PacketFLUDD.DAMAGE, ItemFLUDD.ROCKET));
                            playSmoke(ItemFLUDD.ROCKET, player, true);
                        }
                    }

                    if (player.onGround) {
                        jumpTick = 0;
                    }

                    hoverTick++;
                    jumpTick++;

                    //Hover Mode
                    if (mode == Mode.HOVER) {
                        if (hoverTick >= 64) {
                            disableHover(player);
                        } else {
                            if (player.onGround) {
                                player.motionY = 10;
                            }
                            if (jumpTick < 10) {
                                player.addVelocity(0, 0.01, 0);
                                PacketHandler.sendToServer(new PacketFLUDD(PacketFLUDD.DAMAGE, ItemFLUDD.HOVER));
                            }

                            player.fallDistance = 0F;
                            player.getEntityData().setBoolean("UsingFLUDDHover", true);
                            player.capabilities.isFlying = true;
                            player.capabilities.setFlySpeed(0.005F);
                            player.motionY = ClientHelper.isForwardPressed() ? 0F : ClientHelper.isJumpPressed() ? 0.08F : 0.05F;
                            if (!player.onGround && ClientHelper.isForwardPressed()) {
                                player.moveFlying(0.0F, 1.0F, 0.03F);
                            }

                            playSmoke(ItemFLUDD.HOVER, player, true);
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
            case ItemFLUDD.HOVER:
                return playHover(player, isSender);
            case ItemFLUDD.ROCKET:
                return playRocket(player, isSender);
            case ItemFLUDD.TURBO:
                return playTurbo(player, isSender);
            default:
                return true;
        }
    }

    private static boolean playHover(EntityPlayer player, boolean isSender) {
        if (ExtensionGeneralStuff.FLUDD_WATER_ON) {
            if (isSender) {
                PacketHandler.sendToServer(new PacketFLUDD(PacketFLUDD.ANIMATE, ItemFLUDD.HOVER));
            }
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
        if (ExtensionGeneralStuff.FLUDD_WATER_ON) {
            boolean send = false;
            if (isSender) {
                PacketHandler.sendToServer(new PacketFLUDD(PacketFLUDD.ANIMATE, ItemFLUDD.ROCKET));
            }
            for (float k = -1F; k < 1.05F; k = k + 0.15F) {
                for (float j = -1F; j < 1.05F; j = j + 0.15F) {
                    int i = 0;
                    if (send) {
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
        if (ExtensionGeneralStuff.FLUDD_WATER_ON) {
            if (isSender) {
                PacketHandler.sendToServer(new PacketFLUDD(PacketFLUDD.ANIMATE, ItemFLUDD.TURBO));
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
            if (armor.getItem() instanceof ItemFLUDD) {
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
