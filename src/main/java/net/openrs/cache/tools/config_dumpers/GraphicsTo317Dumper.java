package net.openrs.cache.tools.config_dumpers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.spotanims.SpotAnimType;
import net.openrs.cache.type.spotanims.SpotAnimTypeList;

public class GraphicsTo317Dumper {

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            File dir = new File("E:/dump");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            SpotAnimTypeList list = new SpotAnimTypeList();
            list.initialize(cache);

            try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(dir, "spotanim.dat")))) {
                dos.writeShort(list.size());

                for (int i = 0; i < list.size(); i++) {
                    SpotAnimType spotAnim = list.list(i);

                    if (spotAnim == null) {
                        spotAnim = new SpotAnimType(1);
                    }

                    spotAnim.encode(dos);

                    double progress = ((double) (i + 1) / list.size()) * 100;

                    System.out.println(String.format("%.2f%s", progress, "%"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
