package net.openrs.cache.tools.config_dumpers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.sequences.SequenceType;
import net.openrs.cache.type.sequences.SequenceTypeList;

public class SeqTo317Converter {

    public static void main(String[] args) throws Exception {
        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            SequenceTypeList list = new SequenceTypeList();
            list.initialize(cache);

            DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(dir, "seq.dat")));

            dos.writeShort(list.size());

            for (int i = 0; i < list.size(); i++) {
                SequenceType seq = list.list(i);

                if (seq != null) {
                    seq.encode(dos);
                }

                dos.writeByte(0);

               // System.out.println(i + " " + dos.size());

                double progress = ((double) (i + 1) / list.size()) * 100;
                System.out.println(String.format("%.2f%s", progress, "%"));

            }

            dos.close();
        }
    }

}
