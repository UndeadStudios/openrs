package net.openrs.cache.skeleton.rt7_anims;


import java.nio.ByteBuffer;

public class SkeletalAnimBase {
   AnimationBone[] bones;
   int max_connections;

   public SkeletalAnimBase(ByteBuffer packet, int count) {
      this.bones = new AnimationBone[count];
      this.max_connections = packet.get() & 0xFF;

      for(int var3 = 0; var3 < this.bones.length; ++var3) {
         AnimationBone var4 = new AnimationBone(this.max_connections, packet, false);
         this.bones[var3] = var4;
      }

      this.init_parents();
   }

   void init_parents() {
      for(int b = 0; b < 800; ++b) {
         AnimationBone bone = this.bones[b];
         if (bone.parent_id >= 0) {
            bone.parent = this.bones[bone.parent_id];
         }
      }

   }

   public int get_num_bones() {
      return this.bones.length;
   }

   public AnimationBone get_bone(int arg0) {
      return arg0 >= this.get_num_bones() ? null : this.bones[arg0];
   }

   AnimationBone[] get_bones() {
      return this.bones;
   }

   public void animate(AnimKeyFrameSet arg0, int arg1) {
      this.animate(arg0, arg1, null, false);
   }

   public void animate(AnimKeyFrameSet arg0, int tick, boolean[] flow_control, boolean tween) {
      int frame = arg0.get_keyframeid();
      int var7 = 0;
      AnimationBone[] var8 = this.get_bones();

      for(int i = 0; i < var8.length; ++i) {
         AnimationBone bone = var8[i];
         if (flow_control == null || flow_control[var7] == tween) {
            arg0.apply_transforms(tick, bone, var7, frame);
         }

         ++var7;
      }

   }

}
