package net.openrs.cache.tools.animation_dumper;

import net.openrs.cache.*;
import net.openrs.cache.skeleton.Skeleton;
import net.openrs.cache.skeleton.Skin;
import net.openrs.cache.skeleton.rt7_anims.AnimKeyFrameSet;
import net.openrs.cache.type.CacheIndex;
import net.openrs.cache.util.CompressionUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Objects;

import static net.openrs.cache.skeleton.Skeleton.decode;

public class AnimationDumper {

    public static boolean headerPacked;

    public static void main(String[] args) throws Exception {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            final File dir = new File("D:/dump/test/");

            if (!dir.exists()) {
                dir.mkdirs();
            }
            AnimKeyFrameSet.init();
            final ReferenceTable skeletonTable = cache.getReferenceTable(CacheIndex.FRAMES);

            final Skeleton[][] skeletons = new Skeleton[skeletonTable.capacity()][];

            for (int mainSkeletonId = 3754; mainSkeletonId < skeletonTable.capacity(); mainSkeletonId++) {

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
            final ByteBuffer skeletonBuffer = archive.getEntry(subSkeletonId);

            if (skeletonBuffer.remaining() == 0) {
                return;
            }

            final int skinId = ((skeletonBuffer.array()[0] & 255) << 8) | (skeletonBuffer.array()[1] & 255);

            final Container skinContainer = cache.read(CacheIndex.FRAMEMAPS, skinId);

            final ByteBuffer skinBuffer = skinContainer.getData();

            if (skinBuffer == null) {
                return;
            }

            final Skin skin = Skin.decode(skinBuffer, false, skinBuffer.remaining());

            if (!headerPacked) {
                System.out.println(skinId);
                if (skin.skeletalAnimBase != null) {
                    dos.writeShort(420);
                } else {
                    dos.writeShort(710);
                }
                dos.writeByte(0x20); // Adds a space before the newline
                        dos.writeByte(0x0A); // Adds 0x0A as the new line
              dos.writeInt(skeletons.length);
                skin.encode(dos, false);
                dos.writeShort(archive.size());
                headerPacked = true;
            }

            dos.writeShort(subSkeletonId);

            if (skin.skeletalAnimBase == null) {
                skeletons[mainSkeletonId][subSkeletonId] = decode(skeletonBuffer, skin, dos);
            } else {
                AnimKeyFrameSet keyFrameSet = AnimKeyFrameSet.load(skinId, skeletonBuffer);
                keyFrameSet.encode(dos);
                // Handle encoding or storing of AnimKeyFrameSet as needed
            }

    }
}
