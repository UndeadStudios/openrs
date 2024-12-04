package net.openrs.cache.tools.config_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.overlays.OverlayType;
import net.openrs.cache.type.overlays.OverlayTypeList;
import net.openrs.cache.type.underlays.UnderlayType;
import net.openrs.cache.type.underlays.UnderlayTypeList;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FloorDumper {

    public static void main(String[] args) {

        File dir = new File("E:/dump");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try(Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {

            UnderlayTypeList underlays = new UnderlayTypeList();
            OverlayTypeList overlays = new OverlayTypeList();

            underlays.initialize(cache);
            overlays.initialize(cache);

            final int totalCount = underlays.size() + overlays.size();

            try(DataOutputStream dat = new DataOutputStream(new FileOutputStream(new File(dir,"flo.dat")))) {

                dat.writeShort(underlays.size());

                int count = 0;

                System.out.println(String.format("encoding %d underlays", underlays.size()));

                for (int i = 0; i < underlays.size(); i++) {
                    UnderlayType underlay = underlays.list(i);

                    if (underlay != null) {
                        underlay.encode(dat);
                    } else {
                        dat.writeByte(0);
                    }

                    double progress = ((double) (count + 1) / totalCount) * 100;

                    System.out.println(String
                            .format("%.2f%s", progress, "%"));

                    count++;

                }

                dat.writeShort(overlays.size());

                System.out.println(String.format("encoding %d overlays", overlays.size()));

                for (int i = 0; i < overlays.size(); i++) {
                    OverlayType overlay = overlays.list(i);

                    if (overlay != null) {
                        overlay.encode(dat);
                    } else {
                        dat.writeByte(0);
                    }

                    double progress = ((double) (count + 1) / totalCount) * 100;

                    System.out.println(String
                            .format("%.2f%s", progress, "%"));

                    count++;

                }

                System.out
                        .println(String
                                .format("Dumped %d underlays and %d overlays.",
                                        underlays.size(),
                                        overlays.size()));


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
