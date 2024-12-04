package net.openrs.cache.skeleton;

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
        for(int l1 = 0; l1 < skin.count; l1++) {
            int frame_id = animBuf.getShort() & 0xFFFF;
            int frame_size = animBuf.getShort() & 0xFFFF;
            int before_frame = animBuf.remaining();
            int frame_end = frame_size + before_frame;
            if(frame_id == 65535) {
                System.out.println("Empty frame at anim" + skin.id + ", read frameidx" + frame_id +", iterated frameidx" + l1 + ", framecount:" + skin.count);
                continue;
            }
            if(frame_id != l1) {
                System.out.println("Frame id mismatch " + skin.id + ", read frameidx" + frame_id +", iterated frameidx" + l1 + ", framecount:" + skin.count);
                continue;
            }
            animBuf.position(2);
            int i8 = animBuf.get() & 0xFF;
            int i5 = -1;
            int i9 = 0;
            animBuf2.position(animBuf.position() + i8);
            dat.writeByte(i8);

            for (int i3 = 0; i3 < i8; ++i3) {
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
                throw new RuntimeException("Data size mismatch while reading skeleton.");
            } else {
                this.anInt63 = i9;
                this.anIntArray25 = new int[i9];
                this.anIntArray24 = new int[i9];
                this.anIntArray23 = new int[i9];
                this.anIntArray22 = new int[i9];

                for (int i3 = 0; i3 < i9; ++i3) {
                    this.anIntArray25[i3] = staticIntArray34[i3];
                    this.anIntArray24[i3] = staticIntArray31[i3];
                    this.anIntArray23[i3] = staticIntArray32[i3];
                    this.anIntArray22[i3] = staticIntArray33[i3];
                }
            }
        }
    }

    public static Skeleton decode(ByteBuffer animBuf, Skin skin, DataOutputStream dat) throws IOException {
        Skeleton skeleton = new Skeleton();

        ByteBuffer animBuf2 = ByteBuffer.wrap(animBuf.array());
        for(int l1 = 0; l1 < skin.count; l1++) {
            int frame_id = animBuf.getShort() & 0xFFFF;
            int frame_size = animBuf.getShort() & 0xFFFF;
            int before_frame = animBuf.remaining();
            int frame_end = frame_size + before_frame;

            if (frame_id == 65535) {
                System.out.println("Empty frame at anim" + skin.id + ", read frameidx" + frame_id +", iterated frameidx" + l1 + ", framecount:" + skin.count);
            }
            if (frame_id != l1) {
                System.out.println("Frame id mismatch " + skin.id + ", read frameidx" + frame_id +", iterated frameidx" + l1 + ", framecount:" + skin.count);
            }

            int i8 = animBuf.get() & 0xFF;
            int attr_pos = animBuf.position();
            int data_pos = attr_pos + i8;
            int read_attrs = 0;
            int i5 = -1;
            int i9 = 0;
            animBuf2.position(animBuf.position() + i8);
            dat.writeByte(i8);

            for (int i3 = 0; i3 < i8; ++i3) {
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
                    animBuf2.position( data_pos);
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
                    data_pos = animBuf.position();
                }
            }
            if(data_pos != frame_end) {
                System.err.println("Frame decoding read size mismatch pos " + data_pos +", expected: " + frame_end +", " +  skin.id + ", read frameidx" + frame_id +", iterated frameidx" + l1 + ", framecount:" + skin.count);
            }
            skeleton.anInt63 = i9;
            skeleton.anIntArray25 = new int[i9];
            skeleton.anIntArray24 = new int[i9];
            skeleton.anIntArray23 = new int[i9];
            skeleton.anIntArray22 = new int[i9];

            for (int i3 = 0; i3 < i9; ++i3) {
                skeleton.anIntArray25[i3] = staticIntArray34[i3];
                skeleton.anIntArray24[i3] = staticIntArray31[i3];
                skeleton.anIntArray23[i3] = staticIntArray32[i3];
                skeleton.anIntArray22[i3] = staticIntArray33[i3];
            }
        }
        return skeleton;
    }

    // Add the encode method
    public void encode(DataOutputStream dos) throws IOException {
        dos.writeByte(anInt63); // Write the number of transformations

        // Write the transformation data
        for (int i = 0; i < anInt63; i++) {
            dos.writeByte(anIntArray25[i]); // Write transformation index
            dos.writeShort(anIntArray24[i]); // Write transform X
            dos.writeShort(anIntArray23[i]); // Write transform Y
            dos.writeShort(anIntArray22[i]); // Write transform Z
        }
    }
}
