package net.openrs.cache.skeleton;

import java.nio.ByteBuffer;
public class class217 {

    class122[] field2517;

    int field2516;

    public class217(ByteBuffer var1, int var2) {
        this.field2517 = new class122[var2];
        this.field2516 = var1.get() & 0xFF;

        for(int var3 = 0; var3 < this.field2517.length; ++var3) {
            class122 var4 = new class122(this.field2516, var1, false);
            this.field2517[var3] = var4;
        }

        this.method4430();
    }

    void method4430() {
        class122[] var1 = this.field2517;

        for(int var2 = 0; var2 < var1.length; ++var2) {
            class122 var3 = var1[var2];
            if (var3.field1491 >= 0) {
                var3.field1500 = this.field2517[var3.field1491];
            }
        }

    }

}
