package mariculture.fishery.gui;

import scala.util.Random;
import mariculture.api.fishery.fish.FishDNABase;
import mariculture.fishery.Fishery;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FishTankData {
    private static Random rand = new Random();
    private NBTTagCompound data;
    private int count;
    protected int x;
    protected int y;

    public FishTankData() {}

    public FishTankData(NBTTagCompound data, int count) {
        this.data = data;
        this.count = count;
    }
    
    public void set(int count) {
        this.count = count;
    }
    
    public int getCount() {
        return count;
    }

    public void add(int amount) {
        this.count += amount;
    }

    public void remove(int amount) {
        this.count -= amount;
    }

    public ItemStack make() {
        return make(count);
    }

    public ItemStack make(int stackSize) {
        ItemStack fishy = new ItemStack(Fishery.fishy);
        fishy.stackSize = stackSize;
        fishy.stackTagCompound = data;
        return fishy;
    }

    public boolean matches(ItemStack stack) {
        if (!stack.hasTagCompound()) return false;
        for (FishDNABase dna : FishDNABase.DNAParts) {
            if (!dna.getDNA(stack).equals(dna.getDNA(make()))) {
                return false;
            }

            if (!(dna.getLowerDNA(stack).equals(dna.getLowerDNA(make())))) {
                return false;
            }
        }

        if (stack.stackTagCompound.getInteger("CurrentLife") != make().stackTagCompound.getInteger("CurrentLife")) {
            return false;
        }

        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        data = nbt.getCompoundTag("Data");
        count = nbt.getInteger("Count");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("Data", data);
        nbt.setInteger("Count", count);
    }
    
    private int xPlus;
    private int yPlus;

    public void move() {
        if(x == 145 || x == 1 || y == 91 || y == 1 || rand.nextInt(60) == 0) {
            xPlus = rand.nextInt(3) - 1;
            yPlus = rand.nextInt(3) - 1;
        }
        
        x = Math.min(145, Math.max(1, x + xPlus));
        y = Math.min(91, Math.max(1, y + yPlus));
    }
}
