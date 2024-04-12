package net.openrs.cache.util.jagex.jagex3.math;

public class Vector3f {
   float z;
   float x;
   float y;

   static {
      new Vector3f(0.0F, 0.0F, 0.0F);
      new Vector3f(1.0F, 1.0F, 1.0F);
      new Vector3f(1.0F, 0.0F, 0.0F);
      new Vector3f(0.0F, 1.0F, 0.0F);
      new Vector3f(0.0F, 0.0F, 1.0F);
      method2079(100);
   }

   Vector3f(float arg0, float arg1, float arg2) {
      this.x = arg0;
      this.y = arg1;
      this.z = arg2;
   }

   static void method2079(int arg0) {
   }

   final float length() {
      return (float)Math.sqrt(this.z * this.z + this.y * this.y + this.x * this.x);
   }

   public String toString() {
      return this.x + ", " + this.y + ", " + this.z;
   }
}
