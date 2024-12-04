package net.openrs.cache.tools.config_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.identkits.IdentkitType;
import net.openrs.cache.type.identkits.IdentkitTypeList;
import net.openrs.cache.type.varbits.VarBitType;
import net.openrs.cache.type.varbits.VarBitTypeList;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Identkitto317Dumper {
    public static void main(String[] args) throws IOException {
        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            IdentkitTypeList list = new IdentkitTypeList();
            list.initialize(cache);

            DataOutputStream dat = new DataOutputStream(new FileOutputStream(new File(dir, "idk.dat")));

            dat.writeShort(list.size());

            for (int i = 0; i < list.size(); i++) {
                IdentkitType def = list.list(i);

                if (def != null) {
                    def.encode(dat);
                } else {
                    dat.writeByte(0);
                }

                //System.out.println(i + " " + dat.size());

                double progress = ((double) (i + 1) / list.size()) * 100;
                System.out.println(String.format("%.2f%s", progress, "%"));

            }

            dat.close();

        }
    }
}
