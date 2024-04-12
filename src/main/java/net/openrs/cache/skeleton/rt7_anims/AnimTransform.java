package net.openrs.cache.skeleton.rt7_anims;


import net.openrs.cache.util.jagex.core.constants.SerialEnum;

public class AnimTransform implements SerialEnum {
   public static final AnimTransform TRANSPARENCY = new AnimTransform(4, 4, null, 1);
   public static final AnimTransform field1210 = new AnimTransform(2, 2, null, 3);
   public static final AnimTransform COLOUR = new AnimTransform(3, 3, null, 6);
   public static final AnimTransform VERTEX = new AnimTransform(1, 1, null, 9);
   public static final AnimTransform field1213 = new AnimTransform(5, 5, null, 3);
   public static final AnimTransform NULL = new AnimTransform(0, 0, null, 0);
   final int field1214;
   final int serial_id;
   final int dimensions;

   AnimTransform(int arg0, int arg1, String arg2, int arg3) {
      this.field1214 = arg0;
      this.serial_id = arg1;
      this.dimensions = arg3;
   }

   public int get_dimensions() {
      return this.dimensions;
   }

   public int serial_id() {
      return this.serial_id;
   }

}
