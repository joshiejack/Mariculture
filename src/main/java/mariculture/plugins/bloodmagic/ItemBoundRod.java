package mariculture.plugins.bloodmagic;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.core.config.FishMechanics;
import mariculture.core.helpers.BlockHelper;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemRod;
import mariculture.plugins.PluginBloodMagic;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBoundRod extends ItemRod {
    private IIcon passiveIcon;

    public ItemBoundRod() {
        super();
    }

    public int[] getShard(EntityPlayer player) {
        int currentSlot = player.inventory.currentItem;
        int leftSlot = currentSlot - 1;
        if (leftSlot == -1) {
            leftSlot = 8;
        }
        int rightSlot = currentSlot + 1;
        if (rightSlot == 8) {
            rightSlot = 0;
        }

        int left = hasDemonShard(player, leftSlot);
        if (left > 0) return new int[] { leftSlot, left };
        else {
            int right = hasDemonShard(player, rightSlot);
            if (right > 0) return new int[] { rightSlot, right };
        }

        return null;
    }

    public int hasDemonShard(EntityPlayer player, int slot) {
        ItemStack stack = player.inventory.getStackInSlot(slot);
        if (stack != null) return stack.getItem() == PluginBloodMagic.weakBloodShard.getItem() ? FishMechanics.WEAK_FISH_LIMIT : stack.getItem() == PluginBloodMagic.demonBloodShard.getItem() ? FishMechanics.DEMON_FISH_LIMIT : -1;
        else return -1;
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

                    if (!lightning && world.rand.nextInt(2048) == 0) {
                        lightning = true;
                        world.addWeatherEffect(new EntityLightningBolt(world, x2, y2, z2));
                    }

                    if (BlockHelper.isWater(world, x2, y2, z2) && world.rand.nextInt(5) == 0) {
                        ItemStack loot = Fishing.fishing.getCatch(world, x2, y2, z2, player, player.getCurrentEquippedItem());
                        if (!world.isRemote && loot != null) {
                            float f = 0.7F;
                            double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                            double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                            double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                            EntityItem entityitem = new EntityItem(world, x2 + d0, y2 + d1, z2 + d2, loot);
                            entityitem.motionX = world.rand.nextFloat() * f * 0D;
                            entityitem.motionY = world.rand.nextFloat() * f * 1.5F;
                            entityitem.motionZ = world.rand.nextFloat() * f * 0D;
                            if (loot.getItem() instanceof ItemFishy) {
                                entityitem.lifespan = 60;
                            }
                            entityitem.delayBeforeCanPickup = 0;
                            world.spawnEntityInWorld(entityitem);

                            player.addStat(StatList.fishCaughtStat, 1);
                            player.worldObj.spawnEntityInWorld(new EntityXPOrb(world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, world.rand.nextInt(6) + 1));
                        }

                        catches++;

                        if (catches > maximum) return catches;
                    }
                }
            }
        }

        return catches;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        SoulNetworkHandler.checkAndSetItemOwner(stack, player);
        if (player.isPotionActive(AlchemicalWizardry.customPotionInhibit)) return stack;

        if (player.isSneaking()) {
            setActivated(stack, !getActivated(stack));
            stack.stackTagCompound.setInteger("worldTimeDelay", (int) (world.getWorldTime() - 1) % 200);
        } else if (!getActivated(stack)) {
            return Fishing.fishing.handleRightClick(stack, world, player);
        } else {
            int[] nums = getShard(player);
            if (nums != null) {
                int slot = nums[0];
                int count = nums[1];
                int catches = catchFish(world, player, count);
                if (!player.capabilities.isCreativeMode) {
                    player.inventory.decrStackSize(slot, 1);
                }

                SoulNetworkHandler.syphonAndDamageFromNetwork(stack, player, 500 * catches);
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
    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 7.0F, 0));
        return multimap;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (stack.stackTagCompound == null) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = stack.stackTagCompound;
        if (tag.getBoolean("isActive")) return itemIcon;
        else return passiveIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        passiveIcon = iconRegister.registerIcon(Mariculture.modid + ":rodBlood_deactivated");
    }
}
