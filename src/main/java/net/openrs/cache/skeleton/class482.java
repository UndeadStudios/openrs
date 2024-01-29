package net.openrs.cache.skeleton;

public class class482 {
    public final int field5003;
    public Object field5004;

    public class482(int var1) {
        this.field5003 = var1;
    }

    public class482(int var1, Object var2) {
        this.field5003 = var1;
        this.field5004 = var2;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object var1) {
        if (!(var1 instanceof class482)) {
            return false;
        } else {
            class482 var2 = (class482)var1;
            if (var2.field5004 == null && this.field5004 != null) {
                return false;
            } else if (this.field5004 == null && var2.field5004 != null) {
                return false;
            } else {
                return this.field5003 == var2.field5003 && var2.field5004.equals(this.field5004);
            }
        }
    }
}
