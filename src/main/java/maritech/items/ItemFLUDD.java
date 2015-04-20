package maritech.items;

import java.util.List;

import mariculture.api.core.IPVChargeable;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.items.ItemMCBaseArmor;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.util.Fluids;
import maritech.lib.MTModInfo;
import maritech.model.ModelFLUDD;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFLUDD extends ItemMCBaseArmor implements IPVChargeable {
    public static final int HOVER = 0;
    public static final int ROCKET = 1;
    public static final int TURBO = 2;
    public static final int SQUIRT = 3;
    public static final int STORAGE = 20000;

    public static enum Mode {
        HOVER, ROCKET, TURBO, SQUIRT, NONE
    }

    public ItemFLUDD(ArmorMaterial material, int j, int k) {
        super(MTModInfo.MODPATH, MaricultureTab.tabFactory, material, j, k);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
            side = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
            if (side == 0) {
                --y;
            }
            if (side == 1) {
                ++y;
            }
            if (side == 2) {
                --z;
            }
            if (side == 3) {
                ++z;
            }
            if (side == 4) {
                --x;
            }
            if (side == 5) {
                ++x;
            }
        }

        if (!player.canPlayerEdit(x, y, z, side, stack)) return false;
        else if (stack.stackSize == 0) return false;
        else {
            if (stack.hasTagCompound() && !world.isRemote) {
                Block fludd = Core.renderedMachines;
                if (world.canPlaceEntityOnSide(fludd, x, y, z, false, side, (Entity) null, stack)) {
                    int j1 = fludd.onBlockPlaced(world, x, y, z, side, par8, par9, par10, MachineRenderedMeta.FLUDD_STAND);

                    world.setBlock(x, y, z, fludd, j1, 3);

                    if (world.getBlock(x, y, z) == fludd) {
                        fludd.onBlockPlacedBy(world, x, y, z, player, stack);
                        fludd.onPostBlockPlaced(world, x, y, z, j1);
                    }

                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                }
            }

            return true;
        }
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = Items.carrot.getIconFromDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        list.add(build(STORAGE));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.hasTagCompound()) {
            list.add(stack.stackTagCompound.getInteger("water") + " " + StatCollector.translateToLocal("mariculture.water"));
        }
    }

    public static Mode getMode(ItemStack stack) {
        if (stack.hasTagCompound()) if (stack.stackTagCompound.getInteger("water") > 0) {
            int mode = stack.stackTagCompound.getInteger("mode");
            return Mode.values()[mode < 4 && mode >= 0 ? mode : 0];
        }

        return Mode.NONE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        return new ModelFLUDD();
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
        return "maritech:" + "textures/blocks/fludd_texture.png";
    }

    //Called by the Factory
    public ItemStack build(int amount) {
        ItemStack fludd = new ItemStack(this);
        fludd.setTagCompound(new NBTTagCompound());
        fludd.stackTagCompound.setInteger("mode", 0);
        fludd.stackTagCompound.setInteger("water", amount);
        return fludd;
    }

    @Override
    public void fill(IFluidTank tank, ItemStack[] inventory, int special) {
        if (tank.getFluid() != null && tank.getFluid().getFluid() != null) {
        	if (tank.getFluid().getFluid() == Fluids.getFluid("hp_water") && tank.getFluidAmount() > 0) {
                ItemStack stack = inventory[special].copy();
                int water = 0;
                if (stack.hasTagCompound()) {
                    water = stack.stackTagCompound.getInteger("water");
                } else {
                    stack.setTagCompound(new NBTTagCompound());
                }
                
                int drain = ItemFLUDD.STORAGE - water;
                FluidStack doDrain = tank.drain(drain, false);
                if (doDrain != null) {
                    int theDrain = doDrain.amount;
                    int newWater = Math.min(ItemFLUDD.STORAGE, water + theDrain);
                    tank.drain(theDrain, true);
                    stack.stackTagCompound.setInteger("water", newWater);
                }

                inventory[special] = stack;
            }
        }
    }
}