package net.openrs.cache.skeleton;

import java.nio.ByteBuffer;

final class class462 implements class459 {

    public void vmethod8518(Object var1, ByteBuffer var2) {
        this.method8493((Long)var1, var2);
    }

    public Object vmethod8517(ByteBuffer var1) {
        return var1.getLong();
    }

    void method8493(Long var1, ByteBuffer var2) {
        var2.getLong(Math.toIntExact(var1));
    }
}