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

    public Skin() {}

    public static Skin decode(ByteBuffer buffer, boolean highrev, int bufferSize) {
        final Skin skin = new Skin();
        int beforeRead = buffer.position();

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

        int readSize = buffer.position() - beforeRead;
        if (!highrev && readSize != bufferSize) {
            try {
                int skeletalSize = buffer.getShort() & 0xFFFF;
                if (skeletalSize > 0) {
                    skin.skeletalAnimBase = new SkeletalAnimBase(buffer,  0);
                }
            } catch (Throwable t) {
                System.err.println("Error loading SkeletalAnimBase. Additional skeletal data found but failed to load.");
                t.printStackTrace();
            }

            int finalReadSize = buffer.position() - beforeRead;
            if (finalReadSize != bufferSize) {
                throw new RuntimeException("Data size mismatch: read " + finalReadSize + ", expected " + bufferSize);
            }
        }

        return skin;
    }

    public void encode(DataOutputStream dos, boolean highrev) throws IOException {
        // Write the count of transformation types
        if (highrev) {
            dos.writeShort(count); // Write as an unsigned short for highrev
        } else {
            dos.writeByte(count); // Write as an unsigned byte for lowrev
        }

        // Write each transformation type
        for (int transformationType : transformationTypes) {
            if (highrev) {
                dos.writeShort(transformationType);
            } else {
                dos.writeByte(transformationType);
            }
        }

        // Write each label's length
        for (int i = 0; i < count; i++) {
            if (highrev) {
                dos.writeShort(skinList[i].length);
            } else {
                dos.writeByte(skinList[i].length);
            }
        }

        // Write the labels themselves
        for (int i = 0; i < count; i++) {
            for (int label : skinList[i]) {
                if (highrev) {
                    dos.writeShort(label);
                } else {
                    dos.writeByte(label);
                }
            }
        }

        // Check for skeletal data and write it if it exists and highrev is false
        if (get_skeletal_animbase() != null) {
            // Write the size of the skeletal base
            dos.writeShort(get_skeletal_animbase().bones.length);

            // Write each bone's data using its encode method
            for (int i = 0; i < get_skeletal_animbase().bones.length; i++) {
                AnimationBone bone = get_skeletal_animbase().bones[i];
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
