package net.openrs.cache.skeleton;

public class class416 {
    public static class416[] field4635 = new class416[0];
    static int field4634;
    float field4639;
    float field4636;
    float field4637;
    float field4638;
    static {
        class365.method7022(100);
        new class416();
    }
    public class416() {
        this.method7882();
    }

    public void method7894() {
        synchronized(field4635) {
            if (class388.field4510 < field4634 - 1) {
                field4635[++class388.field4510 - 1] = this;
            }

        }
    }

    void method7881(float var1, float var2, float var3, float var4) {
        this.field4636 = var1;
        this.field4637 = var2;
        this.field4638 = var3;
        this.field4639 = var4;
    }

    public void method7897(float var1, float var2, float var3, float var4) {
        float var5 = (float)Math.sin((double)(var4 * 0.5F));
        float var6 = (float)Math.cos((double)(0.5F * var4));
        this.field4636 = var1 * var5;
        this.field4637 = var2 * var5;
        this.field4638 = var5 * var3;
        this.field4639 = var6;
    }

    public final void method7882() {
        this.field4638 = 0.0F;
        this.field4637 = 0.0F;
        this.field4636 = 0.0F;
        this.field4639 = 1.0F;
    }

    public final void method7883(class416 var1) {
        this.method7881(var1.field4637 * this.field4638 + this.field4639 * var1.field4636 + var1.field4639 * this.field4636 - this.field4637 * var1.field4638, this.field4636 * var1.field4638 + this.field4637 * var1.field4639 - var1.field4636 * this.field4638 + var1.field4637 * this.field4639, var1.field4639 * this.field4638 + var1.field4636 * this.field4637 - this.field4636 * var1.field4637 + var1.field4638 * this.field4639, var1.field4639 * this.field4639 - this.field4636 * var1.field4636 - var1.field4637 * this.field4637 - var1.field4638 * this.field4638);
    }

    public boolean equals(Object var1) {
        if (!(var1 instanceof class416)) {
            return false;
        } else {
            class416 var2 = (class416)var1;
            return this.field4636 == var2.field4636 && var2.field4637 == this.field4637 && var2.field4638 == this.field4638 && var2.field4639 == this.field4639;
        }
    }

    public int hashCode() {
        boolean var1 = true;
        float var2 = 1.0F;
        var2 = var2 * 31.0F + this.field4636;
        var2 = this.field4637 + 31.0F * var2;
        var2 = this.field4638 + var2 * 31.0F;
        var2 = 31.0F * var2 + this.field4639;
        return (int)var2;
    }

    public String toString() {
        return this.field4636 + "," + this.field4637 + "," + this.field4638 + "," + this.field4639;
    }
}
