package net.openrs.cache.tools.animation_dumper;

import net.openrs.cache.*;
import net.openrs.cache.skeleton.Skeleton;
import net.openrs.cache.skeleton.Skin;
import net.openrs.cache.skeleton.rt7_anims.AnimKeyFrameSet;
import net.openrs.cache.skeleton.rt7_anims.SkeletalAnimBase;
import net.openrs.cache.type.CacheIndex;
import net.openrs.cache.util.CompressionUtils;

import java.io.*;
import java.nio.ByteBuffer;

import static net.openrs.cache.skeleton.rt7_anims.AnimKeyFrameSet.*;

public class AnimationDumper {

    public static boolean headerPacked;
    private static boolean highrev = false;

    public static void main(String[] args) throws Exception {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            final File dir = new File("D:/dump/index2/");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            final ReferenceTable skeletonTable = cache.getReferenceTable(CacheIndex.FRAMES);

            final Skeleton[][] skeletons = new Skeleton[skeletonTable.capacity()][];

            for (int mainSkeletonId = 0; mainSkeletonId < skeletons.length; mainSkeletonId++) {

                if (skeletonTable.getEntry(mainSkeletonId) == null) {
                    continue;
                }

                final ByteArrayOutputStream bos = new ByteArrayOutputStream();

                try (DataOutputStream dat = new DataOutputStream(bos)) {
                    Archive skeletonArchive = Archive.decode(cache.read(CacheIndex.FRAMES, mainSkeletonId).getData(), skeletonTable.getEntry(mainSkeletonId).size());
                    if (skeletonArchive == null) {
                        continue;
                    }

                    final int subSkeletonCount = skeletonArchive.size();

                    headerPacked = false;

                    skeletons[mainSkeletonId] = new Skeleton[subSkeletonCount];

                    for (int subSkeletonId = 0; subSkeletonId < subSkeletonCount; subSkeletonId++) {
                        readNext(cache, dat, skeletonArchive, mainSkeletonId, subSkeletonId, skeletons);
                    }
                }

                try (FileOutputStream fos = new FileOutputStream(new File(dir, mainSkeletonId + ".gz"))) {
                    fos.write(CompressionUtils.gzip(bos.toByteArray()));
                }

                double progress = (double) (mainSkeletonId + 1) / skeletonTable.capacity() * 100;

                System.out.println(String.format("%.2f%s", progress, "%"));

            }

            System.out.println(String.format("Dumped %d skeletons.", skeletonTable.capacity()));
        }

    }

    public static void readNext(Cache cache, DataOutputStream dos, Archive archive, int mainSkeletonId, int subSkeletonId, Skeleton[][] skeletons) throws IOException {
        try {
            final ByteBuffer skeletonBuffer = archive.getEntry(subSkeletonId);

            if (skeletonBuffer.remaining() == 0) {
                return;
            }

            final int skinId = ((skeletonBuffer.array()[0] & 255) << 8) | (skeletonBuffer.array()[1] & 255);

            final Container skinContainer = cache.read(CacheIndex.FRAMEMAPS, skinId);

            final ByteBuffer skinBuffer = skinContainer.getData();
            int before_read = skinBuffer.position();
            if (skinBuffer == null) {
                return;
            }

            final Skin skin = Skin.decode(skinBuffer, false, skinBuffer.capacity());

            if (!headerPacked) {
                dos.writeByte(skin.count);

                for (int i = 0; i < skin.count; ++i) {
                    dos.writeByte(skin.transformationTypes[i]);
                }

                for (int i = 0; i < skin.count; ++i) {
                    dos.writeByte(skin.skinList[i].length);
                }

                for (int i = 0; i < skin.count; ++i) {
                    for (int j = 0; j < skin.skinList[i].length; ++j) {
                        dos.writeByte(skin.skinList[i][j]);
                    }
                }
                int read1_size = skinBuffer.position() - before_read;
                if(!highrev) {
                    if (read1_size != skinBuffer.capacity()) {
                        try {
                            int size = skinBuffer.getShort() & 0xFFFF;
                            if (size > 0) {
                                dos.writeShort(skin.field2523.bones.length);
                            }
                        } catch (Throwable t) {
                            System.err.println("Tried to load base because there was extra base data but skeletal failed to load.");
                            t.printStackTrace();
                        }
                    }
                    int read2_size = skinBuffer.position() - before_read;

                    if(read2_size != skinBuffer.capacity()) {
                        throw new RuntimeException("base data size mismatch: " + read2_size + ", expected " + skinBuffer.capacity());
                    }
                }
                dos.writeShort(archive.size());
                headerPacked = true;
            }

            dos.writeShort(subSkeletonId);
            skeletons[mainSkeletonId][subSkeletonId] = load(skin.id, skinContainer.encode().array());
        } catch (Exception e) {

        }
    }


}
