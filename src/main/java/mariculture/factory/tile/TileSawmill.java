package mariculture.factory.tile;

import java.util.ArrayList;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.PlansMeta;
import mariculture.core.tile.base.TileMachine;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.IProgressable;
import mariculture.factory.blocks.BlockItemCustom;
import mariculture.factory.items.ItemPlan;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileSawmill extends TileMachine implements IHasNotification, IProgressable {
    public static final int TOP = 6;
    public static final int NORTH = 7;
    public static final int SOUTH = 8;
    public static final int WEST = 9;
    public static final int EAST = 10;
    public static final int BOTTOM = 11;
    public static final int OUT = 12;

    public int selected = 3;

    public TileSawmill() {
        max = MachineSpeeds.getSawmillSpeed();
        inventory = new ItemStack[13];
        setting = EjectSetting.ITEM;
        output = new int[] { OUT };
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        switch (side) {
            case 0:
                return new int[] { BOTTOM, OUT };
            case 1:
                return new int[] { TOP, OUT };
            case 2:
                return new int[] { NORTH, OUT };
            case 3:
                return new int[] { SOUTH, OUT };
            case 4:
                return new int[] { WEST, OUT };
            case 5:
                return new int[] { EAST, OUT };
        }

        return new int[] {};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (slot == OUT) return false;
        if (slot < TOP) return stack.getItem() instanceof ItemPlan;
        if (stack.getItem() instanceof BlockItemCustom) return false;

        return stack.getItem() instanceof ItemBlock || stack.getItem() == Items.feather;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == OUT;
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    @Override
    public boolean canWork() {
        return RedstoneMode.canWork(this, mode) && hasPlanSelected() && allSidesFilled() && hasRoom(getResult());
    }

    //Whether a plan is selected or not
    private boolean hasPlanSelected() {
        return inventory[selected] != null && inventory[selected].getItem() instanceof ItemPlan;
    }

    //Whether all sides of the sawmill are filled or not
    private boolean allSidesFilled() {
        return inventory[TOP] != null && inventory[BOTTOM] != null && inventory[NORTH] != null && inventory[SOUTH] != null && inventory[EAST] != null && inventory[WEST] != null;
    }

    //Whether the plan type and data matches or not
    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        if (!stack1.isItemEqual(stack2)) return false;
        if (stack1.hasTagCompound() && stack2.hasTagCompound()) return PlansMeta.matches(stack1.stackTagCompound, stack2.stackTagCompound);
        return stack1.stackTagCompound == null && stack2.stackTagCompound == null;
    }

    @Override
    public boolean hasRoom(ItemStack stack) {
        if (setting.canEject(EjectSetting.ITEM)) return true;
        else if (inventory[OUT] == null) return true;
        else return areStacksEqual(inventory[OUT], stack) && inventory[OUT].stackSize + stack.stackSize < inventory[OUT].getMaxStackSize();
    }

    //Whether the item is a feather
    private boolean isFeather(int slot) {
        return inventory[slot].getItem() == Items.feather;
    }

    //Get the block data for the block
    private String getBlock(int slot) {
        if (isFeather(slot)) return Block.blockRegistry.getNameForObject(Core.air);
        else return Block.blockRegistry.getNameForObject(Block.getBlockFromItem(inventory[slot].getItem()));
    }

    //Get the meta data
    private int getMeta(int slot) {
        if (isFeather(slot)) return AirMeta.FAKE_AIR;
        else return inventory[slot].getItemDamage();
    }

    public ItemStack getResult() {
        String[] blocks = new String[] { getBlock(BOTTOM), getBlock(TOP), getBlock(NORTH), getBlock(SOUTH), getBlock(WEST), getBlock(EAST) };
        int[] metas = new int[] { getMeta(BOTTOM), getMeta(TOP), getMeta(NORTH), getMeta(SOUTH), getMeta(WEST), getMeta(EAST) };

        ItemStack stack = PlansMeta.getBlockStack(PlansMeta.getType(inventory[selected]));
        stack.setTagCompound(new NBTTagCompound());
        for (int i = 0; i < 6; i++) {
            stack.stackTagCompound.setString("BlockIdentifier" + i, blocks[i]);
        }

        stack.stackTagCompound.setFloat("BlockResistance", 0F);
        stack.stackTagCompound.setFloat("BlockHardness", 0F);
        stack.stackTagCompound.setIntArray("BlockMetas", metas);
        stack.stackTagCompound.setIntArray("BlockSides", new int[] { 0, 0, 0, 0, 0, 0 });
        stack.stackTagCompound.setString("Name", stack.getDisplayName());
        stack.stackSize = ((ItemPlan) inventory[selected].getItem()).getStackSize(inventory[selected]);
        if (MaricultureHandlers.upgrades.hasUpgrade("ethereal", this)) {
            stack.stackSize *= 2;
        }
        return stack;
    }

    @Override
    public void process() {
        ItemStack result = getResult();
        helper.insertStack(result, output);

        for (int i = TOP; i < TOP + 6; i++) {
            --inventory[i].stackSize;

            if (inventory[i].stackSize == 0) {
                Item var2 = inventory[i].getItem().getContainerItem();
                inventory[i] = var2 == null ? null : new ItemStack(var2);
            }
        }

        inventory[selected].attemptDamageItem(1, worldObj.rand);
        if (inventory[selected].getItemDamage() > inventory[selected].getMaxDamage()) {
            inventory[selected] = null;
        }
    }

    @Override
    public void setGUIData(int id, int value) {
        super.setGUIData(id, value);
        if (id - offset == 0) {
            selected = value;
        }
    }
    
    @Override
    public ArrayList<Integer> getGUIData() {
        ArrayList<Integer> list = super.getGUIData();
        list.add(selected);
        return list;
    }

    @Override
    public boolean isNotificationVisible(NotificationType type) {
        switch (type) {
            case NO_PLAN:
                return !hasPlanSelected();
            case MISSING_SIDE:
                return !allSidesFilled();
            default:
                return false;
        }
    }

    //Read/Write
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        selected = nbt.getInteger("PlanSelected");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("PlanSelected", selected);
    }
}
