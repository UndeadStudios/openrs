package net.openrs.cache.skeleton;

import net.openrs.util.ByteBufferUtils;

import java.nio.ByteBuffer;

final class class464 implements class459 {

    public void vmethod8518(Object var1, ByteBuffer var2) {
        this.method8524((String) var1, var2);
    }


    public Object vmethod8517(ByteBuffer var1) {
        return ByteBufferUtils.emcodeStringCp1252(var1);
    }


    void method8524(String var1, ByteBuffer var2) {
        ByteBufferUtils.emcodeStringCp1252(var2);
    }
}