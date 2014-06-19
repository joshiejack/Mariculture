package mariculture.fishery.tile;

import java.util.ArrayList;
import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.network.PacketCrack;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileMultiStorage;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileSifter extends TileMultiStorage implements ISidedInventory {
    public ItemStack texture = new ItemStack(Blocks.planks);
    public boolean hasInventory;
    private int[] slots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    public TileSifter() {
        inventory = new ItemStack[10];
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return slots;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        TileSifter master = getMaster();
        if (master != null) return master.hasInventory && Fishing.sifter.getResult(stack) != null;
        else return false;
    }

    @Override
    public int getInventoryStackLimit() {
        TileSifter master = getMaster();
        if (master != null) return master.hasInventory ? super.getInventoryStackLimit() : 0;
        else return 0;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length + 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        TileSifter master = getMaster();
        if (master != null) return slot < 10 ? master.inventory[slot] : null;
        else return null;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return hasInventory;
    }

    public boolean process(ItemStack fullStack, Random rand) {
        ArrayList<RecipeSifter> result = Fishing.sifter.getResult(fullStack);
        if (result != null) {
            ItemStack display = fullStack.getItem() instanceof ItemBlock ? fullStack : null;
            if (!worldObj.isRemote) {
                if (display == null) display = new ItemStack(Blocks.dirt);
                PacketHandler.sendAround(new PacketCrack(Block.getIdFromBlock(Block.getBlockFromItem(display.getItem())), display.getItemDamage(), xCoord, yCoord, zCoord), this);
                PacketHandler.sendAround(new PacketCrack(Block.getIdFromBlock(Block.getBlockFromItem(display.getItem())), display.getItemDamage(), slaves.get(0).xCoord, slaves.get(0).yCoord, slaves.get(0).zCoord), this);
            }

            worldObj.playSoundEffect(xCoord, yCoord, zCoord, "minecraft" + ":step.grass", 1.0F, 1.0F);

            int loop = fullStack.stackSize;
            for (int i = 0; i < loop; i++) {
                for (RecipeSifter bait : result) {
                    int chance = rand.nextInt(100);
                    if (chance < bait.chance) {
                        ItemStack ret = bait.bait.copy();
                        ret.stackSize = bait.minCount + rand.nextInt(bait.maxCount + 1 - bait.minCount);
                        if (hasInventory) {
                            InventoryHelper.addItemStackToInventory(inventory, ret, slots);
                        } else if (!worldObj.isRemote) {
                            if (rand.nextInt(2) == 0 && slaves.size() > 0) {
                                SpawnItemHelper.spawnItem(worldObj, slaves.get(0).xCoord, slaves.get(0).yCoord + 1, slaves.get(0).zCoord, ret.copy(), true, 0, 10, 0.25F);
                                SpawnItemHelper.spawnXP(worldObj, slaves.get(0).xCoord, slaves.get(0).yCoord + 1, slaves.get(0).zCoord, Math.max(1, bait.chance/20));
                            } else {
                                SpawnItemHelper.spawnItem(worldObj, xCoord, yCoord + 1, zCoord, ret.copy(), true, 0, 10, 0.25F);
                                SpawnItemHelper.spawnXP(worldObj, xCoord, yCoord + 1, zCoord, Math.max(1, bait.chance/20));
                            }
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }

    public int getSuitableSlot(ItemStack item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) return i;

            if (inventory[i].getItemDamage() == item.getItemDamage() && inventory[i].getItem() == item.getItem() && inventory[i].stackSize + item.stackSize <= inventory[i].getMaxStackSize()) return i;
        }

        return 10;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        hasInventory = nbt.getBoolean("HasInventory");
        if (nbt.hasKey("Texture")) {
            texture = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("Texture"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("HasInventory", hasInventory);
        nbt.setTag("Texture", texture.writeToNBT(new NBTTagCompound()));
    }

    public boolean isSifter(int x, int y, int z) {
        return worldObj.getTileEntity(x, y, z) instanceof TileSifter && !isPartnered(x, y, z);
    }

    @Override
    public TileSifter getMaster() {
        if (master == null) return null;
        TileEntity tile = worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
        return tile != null && tile instanceof TileSifter ? (TileSifter) tile : null;
    }

    @Override
    public void onBlockPlaced() {
        if (onBlockPlaced(xCoord, yCoord, zCoord)) {
            TileSifter master = getMaster();
            if (master != null && !master.isInit()) {
                master.init();
            }
        }
    }

    public boolean onBlockPlaced(int x, int y, int z) {
        if (isSifter(x, y, z) && isSifter(x + 1, y, z)) {
            MultiPart mstr = new MultiPart(x, y, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.WEST));
            setAsMaster(mstr, parts, ForgeDirection.EAST);
            return true;
        }

        if (isSifter(x - 1, y, z) && isSifter(x, y, z)) {
            MultiPart mstr = new MultiPart(x - 1, y, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
            setAsMaster(mstr, parts, ForgeDirection.EAST);
            return true;
        }

        if (isSifter(x, y, z) && isSifter(x, y, z + 1)) {
            MultiPart mstr = new MultiPart(x, y, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.SOUTH));
            setAsMaster(mstr, parts, ForgeDirection.NORTH);
            return true;
        }

        if (isSifter(x, y, z - 1) && isSifter(x, y, z)) {
            MultiPart mstr = new MultiPart(x, y, z - 1);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.SOUTH));
            setAsMaster(mstr, parts, ForgeDirection.NORTH);
            return true;
        }

        return false;
    }
}
