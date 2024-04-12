package net.openrs.cache.util.jagex.jagex3.math;

public final class Quaternion {
   public static Quaternion[] pool = new Quaternion[0];
   public static int pool_limit = 100;
   public static int pool_cursor;
   float y;
   float w;
   float x;
   float z;

   static {
      pool = new Quaternion[100];
      pool_cursor = 0;
      new Quaternion();
   }

    public static Quaternion get() {
       synchronized(pool) {
          if (pool_cursor == 0) {
             return new Quaternion();
          } else {
             pool[--pool_cursor].reset();
             return pool[pool_cursor];
          }
       }
    }

    public void release() {
      synchronized(pool) {
         if (pool_cursor < pool_limit - 1) {
            pool[++pool_cursor - 1] = this;
         }

      }
   }

   public Quaternion() {
      this.reset();
   }

   void set(float arg0, float arg1, float arg2, float arg3) {
      this.x = arg0;
      this.y = arg1;
      this.z = arg2;
      this.w = arg3;
   }

   public void rotate(float arg0, float arg1, float arg2, float arg3) {
      float var6 = (float)Math.sin(arg3 * 0.5F);
      float var7 = (float)Math.cos(arg3 * 0.5F);
      this.x = arg0 * var6;
      this.y = arg1 * var6;
      this.z = var6 * arg2;
      this.w = var7;
   }

   public void reset() {
      this.z = 0.0F;
      this.y = 0.0F;
      this.x = 0.0F;
      this.w = 1.0F;
   }

   public void mul(Quaternion arg0) {
      this.set(arg0.x * this.w + this.x * arg0.w + this.z * arg0.y - this.y * arg0.z, arg0.z * this.x + arg0.y * this.w + (arg0.w * this.y - this.z * arg0.x), this.z * arg0.w + this.y * arg0.x - arg0.y * this.x + arg0.z * this.w, arg0.w * this.w - this.x * arg0.x - arg0.y * this.y - arg0.z * this.z);
   }

   public boolean equals(Object arg0) {
      if (!(arg0 instanceof Quaternion)) {
         return false;
      } else {
         Quaternion var2 = (Quaternion)arg0;
         return var2.x == this.x && this.y == var2.y && this.z == var2.z && var2.w == this.w;
      }
   }

   public int hashCode() {
      boolean var1 = true;
      float var2 = 1.0F;
      float var3 = var2 * 31.0F + this.x;
      float var4 = var3 * 31.0F + this.y;
      float var5 = this.z + 31.0F * var4;
      float var6 = var5 * 31.0F + this.w;
      return (int)var6;
   }

   public String toString() {
      return this.x + "," + this.y + "," + this.z + "," + this.w;
   }

}
