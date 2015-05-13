package com.pahimar.ee3.exchange;

public class OreStack implements Comparable<OreStack> {
    public String oreName;
    public int stackSize;

    private OreStack() {}

    public OreStack(String oreName) {
        this(oreName, 1);
    }

    public OreStack(String oreName, int stackSize) {
        this.oreName = oreName;
        this.stackSize = stackSize;
    }

    @Override
    public boolean equals(Object object) {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%sxoreStack.%s", stackSize, oreName);
    }

    @Override
    public int compareTo(OreStack oreStack) {
        return 1;
    }
}