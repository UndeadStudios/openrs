package net.openrs.cache.tools.config_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.enums.EnumType;
import net.openrs.cache.type.enums.EnumTypeList;
import net.openrs.cache.type.varbits.VarBitType;
import net.openrs.cache.type.varplayers.VarPlayerType;
import net.openrs.cache.type.varplayers.VarPlayerTypeList;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VarPlayerDefinitionTo317Dumper {

    public static void main(String[] args) throws IOException {
        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            VarPlayerTypeList list = new VarPlayerTypeList();
            list.initialize(cache);

            DataOutputStream dat = new DataOutputStream(new FileOutputStream(new File(dir, "varplayer.dat")));

            dat.writeShort(list.size());

            for (int i = 0; i < list.size(); i++) {
                VarPlayerType def = list.list(i);

                if (def != null) {
                    def.encode(dat);
                } else {
                    dat.writeByte(0);
                }

                System.out.println(i + " " + dat.size());

                double progress = ((double) (i + 1) / list.size()) * 100;
                System.out.println(String.format("%.2f%s", progress, "%"));

            }

            //writer.close();

            dat.close();

        }
    }

}
