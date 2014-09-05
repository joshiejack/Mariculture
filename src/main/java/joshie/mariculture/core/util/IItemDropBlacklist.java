package joshie.mariculture.core.util;

public interface IItemDropBlacklist {
    //Return false if this slot doesn't drop it's items
    public boolean doesDrop(int slot);
}
