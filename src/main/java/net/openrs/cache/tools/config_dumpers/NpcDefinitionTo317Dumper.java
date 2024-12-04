package net.openrs.cache.tools.config_dumpers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.npcs.NpcType;
import net.openrs.cache.type.npcs.NpcTypeList;

public class NpcDefinitionTo317Dumper {

    public static void main(String[] args) throws Exception {

        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            NpcTypeList list = new NpcTypeList();
            list.initialize(cache);

            DataOutputStream dat = new DataOutputStream(new FileOutputStream(new File(dir, "npc.dat")));
            DataOutputStream idx = new DataOutputStream(new FileOutputStream(new File(dir, "npc.idx")));

            dat.writeShort(list.size());
            idx.writeShort(list.size());

            for (int i = 0; i < list.size(); i++) {

                NpcType def = list.list(i);

                int start = dat.size();

                if (def != null) {
                    def.encode(dat);
                }

                dat.writeByte(0);

                //System.out.println(i + " " + dat.size());

                int end = dat.size();

                idx.writeShort(end - start);

                double progress = ((double) (i + 1) / list.size()) * 100;
                System.out.println(String.format("%.2f%s", progress, "%"));
            }

            dat.close();
            idx.close();
        }


    }

}
