package net.openrs.cache.skeleton;

import java.nio.ByteBuffer;

public class class122 {

    public final int field1491;
    public class122 field1500;
    float[][] field1488;
    final class417[] field1489;
    class417[] field1490;
    class417[] field1495;
    class417 field1496 = new class417();
    boolean field1493 = true;
    class417 field1494 = new class417();
    boolean field1486 = true;
    class417 field1487 = new class417();
    float[][] field1497;
    float[][] field1498;
    float[][] field1492;

    public class122(int var1, ByteBuffer var2, boolean var3) {
        this.field1491 = var2.getShort() & 0xFFFF;
        this.field1489 = new class417[var1];
        this.field1490 = new class417[this.field1489.length];
        this.field1495 = new class417[this.field1489.length];
        this.field1488 = new float[this.field1489.length][3];

        for(int var4 = 0; var4 < this.field1489.length; ++var4) {
            this.field1489[var4] = new class417(var2, var3);
            this.field1488[var4][0] = var2.getFloat();
            this.field1488[var4][1] = var2.getFloat();
            this.field1488[var4][2] = var2.getFloat();
        }

        this.method3013();
    }

    void method3013() {
        this.field1497 = new float[this.field1489.length][3];
        this.field1498 = new float[this.field1489.length][3];
        this.field1492 = new float[this.field1489.length][3];
        class417 var1;
        synchronized(class417.field4641) {
            if (class417.field4643 == 0) {
                var1 = new class417();
            } else {
                class417.field4641[--class417.field4643].method7964();
                var1 = class417.field4641[class417.field4643];
            }
        }

        class417 var2 = var1;

        for(int var5 = 0; var5 < this.field1489.length; ++var5) {
            class417 var4 = this.method2998(var5);
            var2.method7915(var4);
            var2.method7922();
            this.field1497[var5] = var2.method7912();
            this.field1498[var5][0] = var4.field4644[12];
            this.field1498[var5][1] = var4.field4644[13];
            this.field1498[var5][2] = var4.field4644[14];
            this.field1492[var5] = var4.method7924();
        }

        var2.method7908();
    }
    class417 method2998(int var1) {
        return this.field1489[var1];
    }

}
