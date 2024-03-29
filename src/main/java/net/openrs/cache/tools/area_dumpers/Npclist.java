package net.openrs.cache.tools.area_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.areas.AreaTypeList;
import net.openrs.cache.type.npcs.NpcTypeList;

import java.io.IOException;

public class Npclist {
    private static final NpcTypeList area = new NpcTypeList();

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            area.initialize(cache);
            area.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
