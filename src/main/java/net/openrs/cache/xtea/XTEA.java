package net.openrs.cache.xtea;

public class XTEA {

    private final int region;

    private final int[] keys;

    public XTEA(int region, int[] keys) {
        this.region = region;
        this.keys = keys;
    }

    public int getRegion() {
        return region;
    }

    public int[] getKeys() {
        return keys;
    }
}
