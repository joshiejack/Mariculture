package mariculture.api.util;

public class CachedCoords {
    public int x;
    public int y;
    public int z;

    public CachedCoords(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CachedCoords) {
            CachedCoords cache = (CachedCoords) o;
            return cache.x == x && cache.y == y && cache.z == z;
        } else return false;
    }
}
