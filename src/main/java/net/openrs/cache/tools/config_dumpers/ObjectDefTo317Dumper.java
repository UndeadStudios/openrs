package net.openrs.cache.tools.config_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.objects.ObjectType;
import net.openrs.cache.type.objects.ObjectTypeList;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ObjectDefTo317Dumper {

    public static void main(String[] args) throws IOException {
        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            ObjectTypeList list = new ObjectTypeList();
            list.initialize(cache);

            DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(dir, "loc.dat")));
            DataOutputStream idx = new DataOutputStream(new FileOutputStream(new File(dir, "loc.idx")));

            dos.writeShort(list.size());
            idx.writeShort(list.size());

            for (int i = 0; i < list.size(); i++) {

                ObjectType def = list.list(i);

                int start = dos.size();

                if (def != null) {
                    def.encode(dos);
                }

                dos.writeByte(0);

                //System.out.println(i + " " + dos.size());

                int end = dos.size();

                idx.writeShort(end - start);

                double progress = ((double) (i + 1) / list.size()) * 100;
                System.out.println(String.format("%.2f%s", progress, "%"));
            }


            dos.close();
            idx.close();

        }
    }

}
