package net.openrs.cache.tools.area_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.objects.ObjectTypeList;
import net.openrs.cache.type.sequences.SequenceTypeList;

import java.io.IOException;

public class objectlist {
    private static final ObjectTypeList area = new ObjectTypeList();

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH_four))) {
            area.initialize(cache);
            area.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
