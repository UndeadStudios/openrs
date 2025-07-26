package net.openrs.cache.tools.area_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.sequences.SequenceTypeList;
import net.openrs.cache.type.spotanims.SpotAnimTypeList;

import java.io.IOException;

public class animlist {
    private static final SequenceTypeList area = new SequenceTypeList();

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH_four))) {
            area.initialize(cache);
            area.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
