package net.openrs.cache.skeleton.rt7_anims;


import net.openrs.cache.util.jagex.core.constants.SerialEnum;

public class InterpolationType implements SerialEnum {
   public static final InterpolationType STEP_INTERPOLATION = new InterpolationType(0, 0);
   public static final InterpolationType LINEAR_INTERPOLATION = new InterpolationType(1, 1);
   public static final InterpolationType FORWARDS_INTERPOLATION = new InterpolationType(4, 4);
   public static final InterpolationType CUBICSPLINE_INTERPOLATION = new InterpolationType(3, 3);
   public static final InterpolationType BACKWARDS_INTERPOLATION = new InterpolationType(2, 2);
   final int ordinal;
   final int serial_id;

   static InterpolationType[] values() {
      return new InterpolationType[]{STEP_INTERPOLATION, LINEAR_INTERPOLATION, BACKWARDS_INTERPOLATION, CUBICSPLINE_INTERPOLATION, FORWARDS_INTERPOLATION};
   }

   InterpolationType(int arg0, int arg1) {
      this.ordinal = arg0;
      this.serial_id = arg1;
   }

   public static InterpolationType lookup_by_id(int arg0) {
      InterpolationType var2 = (InterpolationType) SerialEnum.for_id(values(), arg0);
      if (null == var2) {
         var2 = STEP_INTERPOLATION;
      }

      return var2;
   }

   public int serial_id() {
      return this.serial_id;
   }
}
