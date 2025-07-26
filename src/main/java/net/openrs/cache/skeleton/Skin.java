package net.openrs.cache.skeleton;

import net.openrs.cache.skeleton.rt7_anims.AnimationBone;
import net.openrs.cache.skeleton.rt7_anims.SkeletalAnimBase;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Skin {

    public int id;
    public int count;
    public int[] transformationTypes;
    public int[][] skinList;
    public SkeletalAnimBase skeletalAnimBase;

    public static Skin decode(ByteBuffer buffer, boolean highrev, int bufferSize) {
        Skin skin = new Skin();
        int start = buffer.position();

        // 1. Read transform count
        skin.count = highrev ? buffer.getShort() & 0xFFFF : buffer.get() & 0xFF;
        skin.transformationTypes = new int[skin.count];
        skin.skinList = new int[skin.count][];

        // 2. Read transformation types
        for (int i = 0; i < skin.count; ++i) {
            skin.transformationTypes[i] = highrev ? buffer.getShort() & 0xFFFF : buffer.get() & 0xFF;
        }

        // 3. Read skin label counts
        for (int i = 0; i < skin.count; ++i) {
            int labelCount = highrev ? buffer.getShort() & 0xFFFF : buffer.get() & 0xFF;
            skin.skinList[i] = new int[labelCount];
        }

        // 4. Read skin label values
        for (int i = 0; i < skin.count; ++i) {
            for (int j = 0; j < skin.skinList[i].length; ++j) {
                skin.skinList[i][j] = highrev ? buffer.getShort() & 0xFFFF : buffer.get() & 0xFF;
            }
        }

        // 5. Attempt skeletalAnimBase if present (lowrev only)
        int readSize = buffer.position() - start;
        if (!highrev && readSize < bufferSize) {
            try {
                int skeletalSize = buffer.getShort() & 0xFFFF;
                if (skeletalSize > 0) {
                    skin.skeletalAnimBase = new SkeletalAnimBase(buffer, skeletalSize);
                }
            } catch (Throwable t) {
                System.err.println("⚠️ Failed to load SkeletalAnimBase (ID: " + skin.id + ")");
                t.printStackTrace();
            }
        }

        // 6. Optional strict size check
        int finalSize = buffer.position() - start;
        if (finalSize != bufferSize) {
            System.err.printf("⚠️ Warning: mismatch reading skin ID %d – expected %d bytes, read %d bytes\n", skin.id, bufferSize, finalSize);
            // You may choose to throw here in strict mode
        }

        return skin;
    }

    public void encode(DataOutputStream dos, boolean highrev) throws IOException {
        // 1. Write transform count
        if (highrev) {
            dos.writeShort(count);
        } else {
            dos.writeByte(count);
        }

        // 2. Write transformation types
        for (int transformationType : transformationTypes) {
            if (highrev) {
                dos.writeShort(transformationType);
            } else {
                dos.writeByte(transformationType);
            }
        }

        // 3. Write label counts
        for (int[] labels : skinList) {
            if (highrev) {
                dos.writeShort(labels.length);
            } else {
                dos.writeByte(labels.length);
            }
        }

        // 4. Write label values
        for (int[] labels : skinList) {
            for (int label : labels) {
                if (highrev) {
                    dos.writeShort(label);
                } else {
                    dos.writeByte(label);
                }
            }
        }

        // 5. Write skeletalAnimBase if present (lowrev only)
        if (!highrev && skeletalAnimBase != null) {
            dos.writeShort(skeletalAnimBase.bones.length);
            for (AnimationBone bone : skeletalAnimBase.bones) {
                if (bone != null) {
                    bone.encode(dos);
                }
            }
        }
    }

    public SkeletalAnimBase get_skeletal_animbase() {
        return skeletalAnimBase;
    }

    public int transforms_count() {
        return count;
    }
}
