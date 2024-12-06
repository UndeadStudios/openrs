package net.openrs.cache.util.jagex.jagex3.math;

import java.io.DataOutputStream;
import java.io.IOException;

public class Matrix34f {
    float m01;
   float m10;
   float m20;
   float m12;
   float m11;
   float m21;
   float m02;
   float m00;
   float m22;
   float m03;
   float m13;
   float m23;

   public static final Matrix34f IDENTITY = new Matrix34f();
   Matrix34f() {
      this.set_identity();
   }

   void set_identity() {
      this.m23 = 0.0F;
      this.m13 = 0.0F;
      this.m03 = 0.0F;
      this.m12 = 0.0F;
      this.m02 = 0.0F;
      this.m21 = 0.0F;
      this.m01 = 0.0F;
      this.m20 = 0.0F;
      this.m10 = 0.0F;
      this.m22 = 1.0F;
      this.m11 = 1.0F;
      this.m00 = 1.0F;
   }
   public void encode(DataOutputStream dos) throws IOException {
      // Write each matrix component as a float
      dos.writeFloat(m00);
      dos.writeFloat(m01);
      dos.writeFloat(m02);
      dos.writeFloat(m03);
      dos.writeFloat(m10);
      dos.writeFloat(m11);
      dos.writeFloat(m12);
      dos.writeFloat(m13);
      dos.writeFloat(m20);
      dos.writeFloat(m21);
      dos.writeFloat(m22);
      dos.writeFloat(m23);
   }

   void rotate_x(float arg0) {
      float var3 = (float)Math.cos(arg0);
      float var4 = (float)Math.sin(arg0);
      float var5 = this.m10;
      float var6 = this.m11;
      float var7 = this.m12;
      float var8 = this.m13;
      this.m10 = var3 * var5 - this.m20 * var4;
      this.m20 = var4 * var5 + var3 * this.m20;
      this.m11 = var3 * var6 - var4 * this.m21;
      this.m21 = var4 * var6 + this.m21 * var3;
      this.m12 = var3 * var7 - this.m22 * var4;
      this.m22 = this.m22 * var3 + var4 * var7;
      this.m13 = var8 * var3 - var4 * this.m23;
      this.m23 = var4 * var8 + var3 * this.m23;
   }

   void rotate_y(float arg0) {
      float var3 = (float)Math.cos(arg0);
      float var4 = (float)Math.sin(arg0);
      float var5 = this.m00;
      float var6 = this.m01;
      float var7 = this.m02;
      float var8 = this.m03;
      this.m00 = var3 * var5 + this.m20 * var4;
      this.m20 = this.m20 * var3 - var4 * var5;
      this.m01 = var6 * var3 + this.m21 * var4;
      this.m21 = this.m21 * var3 - var4 * var6;
      this.m02 = this.m22 * var4 + var7 * var3;
      this.m22 = this.m22 * var3 - var7 * var4;
      this.m03 = var4 * this.m23 + var8 * var3;
      this.m23 = var3 * this.m23 - var4 * var8;
   }

   void rotate_z(float arg0) {
      float var3 = (float)Math.cos(arg0);
      float var4 = (float)Math.sin(arg0);
      float var5 = this.m00;
      float var6 = this.m01;
      float var7 = this.m02;
      float var8 = this.m03;
      this.m00 = var3 * var5 - var4 * this.m10;
      this.m10 = var4 * var5 + this.m10 * var3;
      this.m01 = var3 * var6 - this.m11 * var4;
      this.m11 = var6 * var4 + var3 * this.m11;
      this.m02 = var3 * var7 - this.m12 * var4;
      this.m12 = this.m12 * var3 + var7 * var4;
      this.m03 = var8 * var3 - var4 * this.m13;
      this.m13 = var8 * var4 + this.m13 * var3;
   }

   void translate(float arg0, float arg1, float arg2) {
      this.m03 += arg0;
      this.m13 += arg1;
      this.m23 += arg2;
   }

   public String toString() {
      return this.m00 + "," + this.m01 + "," + this.m02 + "," + this.m03 + "\n" + this.m10 + "," + this.m11 + "," + this.m12 + "," + this.m13 + "\n" + this.m20 + "," + this.m21 + "," + this.m22 + "," + this.m23;
   }

}
