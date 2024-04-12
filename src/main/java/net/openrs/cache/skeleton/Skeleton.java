package net.openrs.cache.skeleton;

import net.openrs.cache.skeleton.rt7_anims.SkeletalAnimBase;
import net.openrs.util.ByteBufferUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Skeleton {

    int anInt63 = -1;
    boolean aBool5 = false;
    int[] anIntArray25;
    int[] anIntArray24;
    int[] anIntArray23;
    int[] anIntArray22;
    static int[] staticIntArray34 = new int[500];
    static int[] staticIntArray31 = new int[500];
    static int[] staticIntArray32 = new int[500];
    static int[] staticIntArray33 = new int[500];

    public Skeleton() {

    }

    public Skeleton(byte[] animBytes, Skin skin, DataOutputStream dat) throws IOException {

        ByteBuffer animBuf = ByteBuffer.wrap(animBytes);
        ByteBuffer animBuf2 = ByteBuffer.wrap(animBytes);

        animBuf.position(2);

        int i8 = animBuf.get() & 0xFF;
        int i5 = -1;
        int i9 = 0;
        animBuf2.position(animBuf.position() + i8);

        dat.writeByte(i8);

        int i3;
        for (i3 = 0; i3 < i8; ++i3) {
            int opcode = animBuf.get() & 0xFF;
            dat.writeByte(opcode);
            if (opcode > 0) {
                if (skin.transformationTypes[i3] != 0) {
                    for (int var12 = i3 - 1; var12 > i5; --var12) {
                        if (skin.transformationTypes[var12] == 0) {
                            staticIntArray34[i9] = var12;
                            staticIntArray31[i9] = 0;
                            staticIntArray32[i9] = 0;
                            staticIntArray33[i9] = 0;
                            ++i9;
                            break;
                        }
                    }
                }

                staticIntArray34[i9] = i3;
                short var121 = 0;
                if (skin.transformationTypes[i3] == 3) {
                    var121 = 128;
                }

                if ((opcode & 1) != 0) {
                    staticIntArray31[i9] = ByteBufferUtils.getSignedSmart(animBuf2);
                    dat.writeShort(staticIntArray31[i9]);
                } else {
                    staticIntArray31[i9] = var121;
                }

                if ((opcode & 2) != 0) {
                    staticIntArray32[i9] = ByteBufferUtils.getSignedSmart(animBuf2);
                    dat.writeShort(staticIntArray32[i9]);
                } else {
                    staticIntArray32[i9] = var121;
                }

                if ((opcode & 4) != 0) {
                    staticIntArray33[i9] = ByteBufferUtils.getSignedSmart(animBuf2);
                    dat.writeShort(staticIntArray33[i9]);
                } else {
                    staticIntArray33[i9] = var121;
                }

                i5 = i3;
                ++i9;
                if (skin.transformationTypes[i3] == 5) {
                    this.aBool5 = true;
                }
            }
        }

        if (animBytes.length != animBuf2.position()) {
            throw new RuntimeException();
        } else {
            this.anInt63 = i9;
            this.anIntArray25 = new int[i9];
            this.anIntArray24 = new int[i9];
            this.anIntArray23 = new int[i9];
            this.anIntArray22 = new int[i9];

            for (i3 = 0; i3 < i9; ++i3) {
                this.anIntArray25[i3] = staticIntArray34[i3];
                this.anIntArray24[i3] = staticIntArray31[i3];
                this.anIntArray23[i3] = staticIntArray32[i3];
                this.anIntArray22[i3] = staticIntArray33[i3];
            }

        }
    }

    public static Skeleton decode(ByteBuffer animBuf, Skin skin, DataOutputStream dat) throws IOException {

        Skeleton skeleton = new Skeleton();

        ByteBuffer animBuf2 = ByteBuffer.wrap(animBuf.array());

        animBuf.position(2);

        int i8 = animBuf.get() & 0xFF;
        int i5 = -1;
        int i9 = 0;
        animBuf2.position(animBuf.position() + i8);

        dat.writeByte(i8);

        int i3;
        for (i3 = 0; i3 < i8; ++i3) {
            int opcode = animBuf.get() & 0xFF;
            dat.writeByte(opcode);
            if (opcode > 0) {
                if (skin.transformationTypes[i3] != 0) {
                    for (int var12 = i3 - 1; var12 > i5; --var12) {
                        if (skin.transformationTypes[var12] == 0) {
                            staticIntArray34[i9] = var12;
                            staticIntArray31[i9] = 0;
                            staticIntArray32[i9] = 0;
                            staticIntArray33[i9] = 0;
                            ++i9;
                            break;
                        }
                    }
                }

                staticIntArray34[i9] = i3;
                short var121 = 0;
                if (skin.transformationTypes[i3] == 3) {
                    var121 = 128;
                }

                if ((opcode & 1) != 0) {
                    staticIntArray31[i9] = ByteBufferUtils.getSignedSmart(animBuf2);
                    dat.writeShort(staticIntArray31[i9]);

                } else {
                    staticIntArray31[i9] = var121;
                }

                if ((opcode & 2) != 0) {
                    staticIntArray32[i9] = ByteBufferUtils.getSignedSmart(animBuf2);
                    dat.writeShort(staticIntArray32[i9]);

                } else {
                    staticIntArray32[i9] = var121;
                }

                if ((opcode & 4) != 0) {
                    staticIntArray33[i9] = ByteBufferUtils.getSignedSmart(animBuf2);
                    dat.writeShort(staticIntArray33[i9]);

                } else {
                    staticIntArray33[i9] = var121;
                }

                i5 = i3;
                ++i9;
                if (skin.transformationTypes[i3] == 5) {
                    skeleton.aBool5 = true;
                }
            }
        }

        if (animBuf.array().length != animBuf2.position()) {
            throw new RuntimeException();
        } else {
            skeleton.anInt63 = i9;
            skeleton.anIntArray25 = new int[i9];
            skeleton.anIntArray24 = new int[i9];
            skeleton.anIntArray23 = new int[i9];
            skeleton.anIntArray22 = new int[i9];

            for (i3 = 0; i3 < i9; ++i3) {
                skeleton.anIntArray25[i3] = staticIntArray34[i3];
                skeleton.anIntArray24[i3] = staticIntArray31[i3];
                skeleton.anIntArray23[i3] = staticIntArray32[i3];
                skeleton.anIntArray22[i3] = staticIntArray33[i3];
            }

        }

        return skeleton;

    }

}