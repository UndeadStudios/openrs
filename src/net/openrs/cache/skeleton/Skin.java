package net.openrs.cache.skeleton;

import java.nio.ByteBuffer;

public class Skin {

    public int id;
    public int count;
    public int[] transformationTypes;
    public int[][] skinList;

    public Skin() {

    }

    public static Skin encode(ByteBuffer buffer) {
        final Skin skin = new Skin();
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
        return skin;
    }

    public static Skin decode(ByteBuffer buffer) {
        final Skin skin = new Skin();
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
        if(buffer.capacity() < buffer.position()) {
            int var4 = buffer.getShort() & 0xFFFF;
            if(var4 > 0) {
                //this.field2541 = new class219(var3, var4);
            }
        }
        return skin;
    }

}
