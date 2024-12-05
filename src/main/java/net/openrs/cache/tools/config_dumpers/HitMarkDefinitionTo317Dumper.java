package net.openrs.cache.tools.config_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.enums.EnumType;
import net.openrs.cache.type.enums.EnumTypeList;
import net.openrs.cache.type.hitmarks.HitMarkType;
import net.openrs.cache.type.hitmarks.HitMarkTypeList;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HitMarkDefinitionTo317Dumper {

    public static void main(String[] args) throws IOException {
        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            HitMarkTypeList list = new HitMarkTypeList();
            list.initialize(cache);

            DataOutputStream dat = new DataOutputStream(new FileOutputStream(new File(dir, "hitmark.dat")));
            DataOutputStream idx = new DataOutputStream(new FileOutputStream(new File(dir, "hitmark.idx")));

            idx.writeShort(list.size());
            dat.writeShort(list.size());

            //PrintWriter writer = new PrintWriter(new File("./info.txt"));

            for (int i = 0; i < list.size(); i++) {
                HitMarkType def = list.list(i);

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
