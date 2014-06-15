package mariculture.fishery.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.config.Machines.MachineSettings;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.tile.base.TileMultiMachinePowered;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import mariculture.fishery.Fish;
import mariculture.fishery.FishyHelper;
import mariculture.fishery.items.ItemEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileIncubator extends TileMultiMachinePowered implements IHasNotification {
    private int cooldown = 0;
    private double mutation = 1.0D;

    public TileIncubator() {
        needsInit = true;
        max = MachineSpeeds.getIncubatorSpeed();
        inventory = new ItemStack[22];
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
    public int[] out = new int[] { 13, 14, 15, 16, 17, 18, 19, 20, 21 };

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
        for (Integer i : out) {
            if (inventory[i] == null) {
                ;
            }
            return true;
        }

        return false;
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
                energyStorage.extractEnergy(getRFUsage(), false);
                processed += speed;

                if (onTick(70)) {
                    processed -= heat;
                }
                
                if (processed >= max) {
                    processed = 0;
                    if (canWork()) {
                        int loop = MaricultureHandlers.upgrades.hasUpgrade("incubator", this) ? 1024 : (heat * 4) + 1;
                        for (int o = 0; o < loop; o++) {
                            hatchEgg();
                        }
                    }

                    canWork = canWork();
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
    public void updateSlaveMachine() {
        return;
    }

    private boolean hasEgg() {
        for (Integer i : in)
            if (inventory[i] != null) return true;

        return false;
    }

    private boolean hasPower() {
        return energyStorage.extractEnergy(getRFUsage() * 2, true) >= getRFUsage() * 2;
    }

    @Override
    public int getRFUsage() {
        return 35 + ((speed - 1) * 40) + (heat * 80);
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
        if (MaricultureHandlers.upgrades.hasUpgrade("incubator", this)) {
            setMutationModifier(10000D);
        } else if (MaricultureHandlers.upgrades.hasUpgrade("ethereal", this)) {
            setMutationModifier(1.25D);
        }

        if (inventory[slot] == null) return false;
        Random rand = new Random();
        if (inventory[slot].getItem() instanceof ItemEgg) {
            int[] fertility = inventory[slot].stackTagCompound.getIntArray(Fish.fertility.getEggString());
            int[] lifes = inventory[slot].stackTagCompound.getIntArray(Fish.lifespan.getEggString());

            if (inventory[slot].getTagCompound().hasKey("SpeciesList")) {
                int birthChance = 1 + MaricultureHandlers.upgrades.getData("purity", this);
                inventory[slot].getTagCompound().setInteger("currentFertility", inventory[slot].getTagCompound().getInteger("currentFertility") - 1);
                if (rand.nextInt(1000) < birthChance) {
                    ItemStack fish = Fishing.fishHelper.makeBredFish(inventory[slot], rand, mutation);
                    if (fish != null) {
                        int dna = Fish.gender.getDNA(fish);
                        helper.insertStack(fish, out);

                        if (dna == FishyHelper.MALE) {
                            inventory[slot].getTagCompound().setInteger("malesGenerated", inventory[slot].getTagCompound().getInteger("malesGenerated") + 1);
                        } else if (dna == FishyHelper.FEMALE) {
                            inventory[slot].getTagCompound().setInteger("femalesGenerated", inventory[slot].getTagCompound().getInteger("femalesGenerated") + 1);
                        }
                    } else {
                        helper.insertStack(new ItemStack(Items.fish, 2, 0), out);
                    }
                }

                if (inventory[slot].getTagCompound().getInteger("currentFertility") == 0) {
                    ItemStack fish = Fishing.fishHelper.makeBredFish(inventory[slot], rand, mutation);
                    if (fish != null) {
                        // If no males were generated create one
                        if (inventory[slot].getTagCompound().getInteger("malesGenerated") <= 0) {
                            helper.insertStack(Fish.gender.addDNA(fish.copy(), FishyHelper.MALE), out);
                        }

                        fish = Fishing.fishHelper.makeBredFish(inventory[slot], rand, mutation);
                        if (fish != null) // If no females were generated create one
                        if (inventory[slot].getTagCompound().getInteger("femalesGenerated") <= 0) {
                            helper.insertStack(Fish.gender.addDNA(fish.copy(), FishyHelper.FEMALE), out);
                        }
                    } else {
                        helper.insertStack(new ItemStack(Items.fish), out);
                    }

                    decrStackSize(slot, 1);
                    return true;
                }
            }
        } else if (inventory[slot].getItem() == Items.egg) {
            if (Rand.nextInt(8)) {
                helper.insertStack(new ItemStack(Items.spawn_egg, 1, 93), out);
            }

            decrStackSize(slot, 1);
            return true;
        } else if (inventory[slot].getItem() == Item.getItemFromBlock(Blocks.dragon_egg)) {
            int chance = MaricultureHandlers.upgrades.hasUpgrade("ethereal", this) ? MachineSettings.DRAGON_EGG_ETHEREAL : MachineSettings.DRAGON_EGG_BASE;
            if (Rand.nextInt(chance)) {
                helper.insertStack(new ItemStack(Core.crafting, 1, CraftingMeta.DRAGON_EGG), out);
            }

            if (Rand.nextInt(10)) {
                decrStackSize(slot, 1);
                return true;
            }
        }

        return false;
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
