package net.openrs.cache.skeleton;

import java.util.Map;

public class class388 {
    public static int field4510;

    static class421 field4512;

    final class387 this$0;

    class388(class387 var1) {
        this.this$0 = var1;
    }

    int method7459(Map.Entry var1, Map.Entry var2) {
        return ((Float)var2.getValue()).compareTo((Float)var1.getValue());
    }

    public boolean equals(Object var1) {
        return super.equals(var1);
    }

    public int compare(Object var1, Object var2) {
        return this.method7459((Map.Entry)var1, (Map.Entry)var2);
    }

    public static int method7467(int var0) {
        return var0 & 16383;
    }
}
