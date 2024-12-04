package net.openrs.cache.tools.area_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.npcs.NpcTypeList;
import net.openrs.cache.type.spotanims.SpotAnimType;
import net.openrs.cache.type.spotanims.SpotAnimTypeList;

import java.io.IOException;

public class Gfxlist {
    private static final SpotAnimTypeList area = new SpotAnimTypeList();

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open("C:\\Users\\Sgsrocks\\Downloads\\2019-07-04-rev180\\cache"))) {
            area.initialize(cache);
            area.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
