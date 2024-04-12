package net.openrs.cache.skeleton;

import net.openrs.cache.skeleton.rt7_anims.SkeletalAnimBase;

import java.nio.ByteBuffer;

public class Skin {

    public int id;
    public int count;
    public int[] transformationTypes;
    public int[][] skinList;
    public SkeletalAnimBase field2523;

    public Skin() {

    }

    public static Skin encode(ByteBuffer buffer, boolean highrev, int buffer_size) {
        final Skin skin = new Skin();
        int before_read = buffer.position();
        skin.count = buffer.get() & 0xFF;
        skin.transformationTypes = new int[skin.count];
        skin.skinList = new int[skin.count][];

        for (int i = 0; i < skin.count; ++i) {
            skin.transformationTypes[i] = buffer.get() & 0xFF;
        }

        for (int i = 0; i < skin.count; ++i) {
            skin.skinList[i] = new int[buffer.get() & 0xFF];
        }

        for (int i = 0; i < skin.count; ++i) {
            for (int j = 0; j < skin.skinList[i].length; ++j) {
                skin.skinList[i][j] = buffer.get() & 0xFF;
            }
        }
        int read1_size = buffer.position() - before_read;
        if(!highrev) {
            if (read1_size != buffer_size) {
                try {
                    int size = buffer.getShort() & 0xFFFF;
                    if (size > 0) {
                        skin.field2523 = new SkeletalAnimBase(buffer, size);
                    }
                } catch (Throwable t) {
                    System.err.println("Tried to load base because there was extra base data but skeletal failed to load.");
                    t.printStackTrace();
                }
            }
            int read2_size = buffer.position() - before_read;

            if(read2_size != buffer_size) {
                throw new RuntimeException("base data size mismatch: " + read2_size + ", expected " + buffer_size);
            }
        }
        return skin;
    }

    public static Skin decode(ByteBuffer buffer, boolean highrev, int buffer_size) {
        final Skin skin = new Skin();
        int before_read = buffer.position();
        skin.count = buffer.get() & 0xFF;
        skin.transformationTypes = new int[skin.count];
        skin.skinList = new int[skin.count][];

        for (int i = 0; i < skin.count; ++i) {
            skin.transformationTypes[i] = buffer.get() & 0xFF;
        }

        for (int i = 0; i < skin.count; ++i) {
            skin.skinList[i] = new int[buffer.get() & 0xFF];
        }

        for (int i = 0; i < skin.count; ++i) {
            for (int j = 0; j < skin.skinList[i].length; ++j) {
                skin.skinList[i][j] = buffer.get() & 0xFF;
            }
        }
        int read1_size = buffer.position() - before_read;
        if(!highrev) {
            if (read1_size != buffer_size) {
                try {
                    int size = buffer.getShort() & 0xFFFF;
                    if (size > 0) {
                        skin.field2523 = new SkeletalAnimBase(buffer, size);
                    }
                } catch (Throwable t) {
                    System.err.println("Tried to load base because there was extra base data but skeletal failed to load.");
                    t.printStackTrace();
                }
            }
            int read2_size = buffer.position() - before_read;

            if(read2_size != buffer_size) {
                throw new RuntimeException("base data size mismatch: " + read2_size + ", expected " + buffer_size);
            }
        }
        return skin;
    }
    public SkeletalAnimBase get_skeletal_animbase() {
        return field2523;
    }

    public int transforms_count() {
        return count;
    }
}
