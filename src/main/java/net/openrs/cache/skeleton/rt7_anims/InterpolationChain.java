package net.openrs.cache.skeleton.rt7_anims;

public class InterpolationChain {
   public float[] weights;
   public int interpolation_count;

   public InterpolationChain(float[] weights, int arg1) {
      this.weights = weights;
      this.interpolation_count = arg1;
   }

   public static float calc_cumulative_weight(float[] weights, int count, float arg2) {
      float chain = weights[count];

      for(int i = count - 1; i >= 0; --i) {
         chain = arg2 * chain + weights[i];
      }

      return chain;
   }

   static float[] scale_weights(int keyframe_count, float[] weights) {
      float[] new_weights = new float[1 + keyframe_count];

      for(int i = 1; i <= keyframe_count; ++i) {
         new_weights[i - 1] = weights[i] * (float)i;
      }
      return new_weights;
   }
}
