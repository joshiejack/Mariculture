package maritech.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IIncubator;
import mariculture.core.Core;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.util.IHasNotification;
import mariculture.fishery.items.ItemEgg;
import maritech.extensions.config.ExtensionMachines.ExtendedSettings;
import maritech.tile.base.TileMultiMachinePowered;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileIncubator extends TileMultiMachinePowered implements IHasNotification, IIncubator {
    private int cooldown = 0;
    private double mutation = 1.1D;

    public TileIncubator() {
        needsInit = true;
        max = MachineSpeeds.getIncubatorSpeed();
        inventory = new ItemStack[22];
        output = new int[] { 13, 14, 15, 16, 17, 18, 19, 20, 21 };
    }

    //Sets the mutation modifier for this incubator
    public void setMutationModifier(double d) {
        TileIncubator tile = (TileIncubator) getMaster();
        if (tile != null) {
            tile.cooldown = 25;
            tile.mutation = d;
        }
    }

    @Override
    public int getRFCapacity() {
        return 50000;
    }

    public int[] in = new int[] { 4, 5, 6, 7, 8, 9, 10, 11, 12 };

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot > 3 && slot < 13 && (Fishing.fishHelper.isEgg(stack) || stack.getItem() == Items.egg || stack.getItem() == Item.getItemFromBlock(Blocks.dragon_egg));
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot > 12;
    }

    @Override
    public boolean canWork() {
        return hasPower() && hasEgg() && rsAllowsWork() && outputHasRoom();
    }

    private boolean outputHasRoom() {
        if (setting.canEject(EjectSetting.ITEM)) return true;
        for (Integer i : output) {
            if (inventory[i] == null) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public void updateUpgrades() {
        super.updateUpgrades();
        if (MaricultureHandlers.upgrades.hasUpgrade("incubator", this)) {
            setMutationModifier(10000D);
        } else if (MaricultureHandlers.upgrades.hasUpgrade("ethereal", this)) {
            setMutationModifier(2D);
        }
        
        speed *= 10;
    }

    @Override
    public void updateMasterMachine() {
        if (!worldObj.isRemote) {
            if (cooldown > 0) {
                cooldown--;
            } else {
                mutation = 1.0D;
            }

            if (canWork) {
                energyStorage.extractEnergy(getPowerPerTick(), false);
                processed += speed;

                if (onTick(70)) {
                    processed -= heat;
                }

                if (processed >= max) {
                    processed = 0;
                    process();
                }
            } else {
                processed = 0;
            }

            if (processed <= 0) {
                processed = 0;
            }
        }
    }

    @Override
    public void process() {
        if (canWork()) {
            int loop = MaricultureHandlers.upgrades.hasUpgrade("incubator", this) ? 1024 : (heat * 4) + 3;
            for (int o = 0; o < loop; o++) {
                hatchEgg();
            }
        }

        updateCanWork();
    }

    private boolean hasEgg() {
        for (Integer i : in)
            if (inventory[i] != null) return true;

        return false;
    }

    private boolean hasPower() {
        return energyStorage.extractEnergy(getPowerPerTick() * 2, true) >= getPowerPerTick() * 2;
    }

    @Override
    public void updatePowerPerTick() {
        if (rf <= 300000) {
            double modifier = 1D - (rf / 300000D) * 0.75D;
            usage = 4 + (int) (modifier * (36 + ((speed - 1) * 40) + (heat * 80)));
        } else usage = 1;
    }

    public boolean hatchEgg() {
        Integer[] inArray = new Integer[in.length];
        int i = 0;
        for (int value : in) {
            inArray[i++] = Integer.valueOf(value);
        }

        List<Integer> list = new ArrayList<Integer>(Arrays.asList(inArray));
        Collections.shuffle(list);
        for (Integer j : list)
            if (inventory[j] != null) if (openEgg(j)) return true;

        return false;
    }

    private boolean openEgg(int slot) {
        if (inventory[slot] == null) return false;
        Random rand = new Random();
        if (inventory[slot].getItem() instanceof ItemEgg) {
            inventory[slot] = Fishing.fishHelper.attemptToHatchEgg(inventory[slot], rand, mutation, this);
        } else if (inventory[slot].getItem() == Items.egg) {
            if (worldObj.rand.nextInt(8) == 0) {
                helper.insertStack(new ItemStack(Items.spawn_egg, 1, 93), output);
            }

            decrStackSize(slot, 1);
            return true;
        } else if (inventory[slot].getItem() == Item.getItemFromBlock(Blocks.dragon_egg)) {
            int chance = MaricultureHandlers.upgrades.hasUpgrade("ethereal", this) ? ExtendedSettings.DRAGON_EGG_ETHEREAL : ExtendedSettings.DRAGON_EGG_BASE;
            if (worldObj.rand.nextInt(chance) == 0) {
                helper.insertStack(new ItemStack(Core.crafting, 1, CraftingMeta.DRAGON_EGG), output);
            }

            if (worldObj.rand.nextInt(10) == 0) {
                decrStackSize(slot, 1);
                return true;
            }
        }

        return false;
    }

    @Override
    public int getBirthChanceBoost() {
        return 9 + (MaricultureHandlers.upgrades.getData("purity", this) * 8);
    }

    @Override
    public void eject(ItemStack fish) {
        helper.insertStack(fish, output);
    }

    @Override
    public boolean isNotificationVisible(NotificationType type) {
        switch (type) {
            case NO_EGG:
                return !hasEgg();
            case NO_RF:
                return !hasPower();
            default:
                return false;
        }
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    @Override
    public void onBlockPlaced() {
        onBlockPlaced(xCoord, yCoord, zCoord);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public void onBlockPlaced(int x, int y, int z) {
        if (isBase(x, y, z) && isTop(x, y + 1, z) && isTop(x, y + 2, z) && !isTop(x, y + 3, z)) {
            MultiPart mstr = new MultiPart(x, y, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y + 1, z, ForgeDirection.DOWN));
            parts.add(setAsSlave(mstr, x, y + 2, z));
            setAsMaster(mstr, parts);
        }

        if (isBase(x, y - 1, z) && isTop(x, y, z) && isTop(x, y + 1, z) && !isTop(x, y + 2, z)) {
            MultiPart mstr = new MultiPart(x, y - 1, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.DOWN));
            parts.add(setAsSlave(mstr, x, y + 1, z));
            setAsMaster(mstr, parts);
        }

        if (isBase(x, y - 2, z) && isTop(x, y - 1, z) && isTop(x, y, z) && !isTop(x, y + 1, z)) {
            MultiPart mstr = new MultiPart(x, y - 2, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y - 1, z, ForgeDirection.DOWN));
            parts.add(setAsSlave(mstr, x, y, z));
            setAsMaster(mstr, parts);
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public boolean isBase(int x, int y, int z) {
        return worldObj.getBlock(x, y, z) == getBlockType() && worldObj.getBlockMetadata(x, y, z) == MachineMultiMeta.INCUBATOR_BASE;
    }

    public boolean isTop(int x, int y, int z) {
        return worldObj.getBlock(x, y, z) == getBlockType() && worldObj.getBlockMetadata(x, y, z) == MachineMultiMeta.INCUBATOR_TOP;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        mutation = nbt.getDouble("MutationModifier");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setDouble("MutationModifier", mutation);
    }
}
