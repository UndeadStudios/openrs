package net.openrs.cache.tools.config_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.items.ItemType;
import net.openrs.cache.type.items.ItemTypeList;

import java.io.*;

public class ItemDefinitionTo317Dumper {

    public static void main(String[] args) throws IOException {
        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            ItemTypeList list = new ItemTypeList();
            list.initialize(cache);

            DataOutputStream dat = new DataOutputStream(new FileOutputStream(new File(dir, "obj.dat")));
            DataOutputStream idx = new DataOutputStream(new FileOutputStream(new File(dir, "obj.idx")));

            idx.writeShort(list.size());
            dat.writeShort(list.size());

            //PrintWriter writer = new PrintWriter(new File("./info.txt"));

            for (int i = 0; i < list.size(); i++) {
                ItemType def = list.list(i);

                int start = dat.size();

                if (def != null) {
                    def.encode(dat);
                }

                dat.writeByte(0);

                int end = dat.size();

                //System.out.println(String.format("%d %d", i, end));

                idx.writeShort(end - start);

                double progress = ((double) (i + 1) / list.size()) * 100;
                System.out.println(String.format("%.2f%s", progress, "%"));

            }

            //writer.close();

            dat.close();
            idx.close();

        }
    }

}
