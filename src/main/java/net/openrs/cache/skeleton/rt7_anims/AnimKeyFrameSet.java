package net.openrs.cache.skeleton.rt7_anims;

import net.openrs.cache.skeleton.Skeleton;
import net.openrs.cache.skeleton.Skin;
import net.openrs.cache.util.jagex.core.constants.SerialEnum;
import net.openrs.cache.util.jagex.jagex3.math.Matrix4f;
import net.openrs.cache.util.jagex.jagex3.math.Quaternion;
import net.openrs.util.ByteBufferUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AnimKeyFrameSet {
   boolean modifies_trans;
   AnimationKeyFrame[][] skeletal_transforms = null;
   int frameset_id;// same as keyframe_id prob
   int keyframe_id = 0;
   public AnimationKeyFrame[][] transforms = null;
   public Skin base;


   public static AnimKeyFrameSet[] keyframesetset;
   private int frame_size;
   private int rtog;
   private int rtog2;
   private int var4;

   public AnimKeyFrameSet() {}

   public static void init() {
      keyframesetset = new AnimKeyFrameSet[4500];
   }
   public static Skeleton load(int group, byte[] fileData){
      try {
         ByteBuffer keyframe_buffer = ByteBuffer.wrap(fileData);
         int baseSize = keyframe_buffer.getInt();//need to write this
         byte[] base_data = new byte[baseSize];
         keyframe_buffer.get(base_data, 0, baseSize);
         ByteBuffer base_buffer = ByteBuffer.wrap(base_data);

         // Should always be 1?
         //int keyframesets_in_group = 1;//keyframe_buffer.get_unsignedbyte();//meed to write this

         int version = keyframe_buffer.get() & 0xFF;
         int base_id = keyframe_buffer.getShort() & 0xFFFF;
         System.out.println("Loading keyframeset " + group + ", base_id " + base_id + ", version " + version);
         AnimKeyFrameSet keyframe = keyframesetset[group] = new AnimKeyFrameSet();
         keyframe.frameset_id = group;
         try {
            keyframe.base = Skin.decode(base_buffer, false, baseSize);
         } catch (RuntimeException e) {
            keyframesetset[group] = null;
            System.err.println(e.getMessage());
            System.err.println("Error1 unpacking base for keyframe " + group);
            e.printStackTrace();
         }
         try {
            keyframe.decode(keyframe_buffer, version);
         } catch(RuntimeException exception) {
            keyframesetset[group] = null;
            System.err.println("Error1 unpacking keyframes " + group + " file size from cache = " + fileData);
            exception.printStackTrace();
         }
      } catch(Exception exception) {
         System.err.println("Error2 unpacking keyframes " + group);
         exception.printStackTrace();
      }
      return null;
   }

   void encode(ByteBuffer packet, int version, DataOutputStream dos) throws IOException {
      dos.writeInt(frame_size);
      int before_read = packet.position();
      dos.writeShort(rtog);
      dos.writeShort(rtog2);
      dos.writeByte(keyframe_id);
      dos.writeShort(var4);
      this.skeletal_transforms = new AnimationKeyFrame[this.base.get_skeletal_animbase().get_num_bones()][];
      this.transforms = new AnimationKeyFrame[this.base.transforms_count()][];

      for(int var5 = 0; var5 < var4; ++var5) {
         int var7 = packet.get() & 0xFF;
         AnimTransform[] var8 = new AnimTransform[]{AnimTransform.NULL, AnimTransform.VERTEX, AnimTransform.field1210, AnimTransform.COLOUR, AnimTransform.TRANSPARENCY, AnimTransform.field1213};
         AnimTransform var9 = (AnimTransform) SerialEnum.for_id((SerialEnum[]) var8, var7);
         if (var9 == null) {
            var9 = AnimTransform.NULL;
         }

         int var14 = ByteBufferUtils.get_short(packet);
         AnimationChannel var10 = AnimationChannel.lookup_by_id(packet.get() & 0xFF);
         AnimationKeyFrame var11 = new AnimationKeyFrame();
         var11.deserialise(packet, version);
         int count = var9.get_dimensions();
         AnimationKeyFrame[][] transforms;
         if (AnimTransform.VERTEX == var9) {
            transforms = this.skeletal_transforms;
         } else {
            transforms = this.transforms;
         }

         if (transforms[var14] == null) {
            transforms[var14] = new AnimationKeyFrame[count];
         }

         transforms[var14][var10.get_component()] = var11;
         if (AnimTransform.TRANSPARENCY == var9) {
            this.modifies_trans = true;
         }
      }
      int read_size = packet.position() - before_read;

      if(read_size != frame_size) {
         throw new RuntimeException("AnimKeyFrameSet size mismatch! keyframe " + keyframe_id + ", frame size: " + frame_size + ", actual read: " + read_size);

      }

   }
   void decode(ByteBuffer packet, int version) {
       frame_size = packet.getInt();
      int before_read = packet.position();
       rtog = packet.getShort() & 0xFFFF;//starttick
       rtog2 = packet.getShort() & 0xFFFF;
      this.keyframe_id = packet.get() & 0xFF;//keyframe
       var4 = packet.getShort() & 0xFFFF;
      this.skeletal_transforms = new AnimationKeyFrame[this.base.get_skeletal_animbase().get_num_bones()][];
      this.transforms = new AnimationKeyFrame[this.base.transforms_count()][];

      for(int var5 = 0; var5 < var4; ++var5) {
         int var7 = packet.get() & 0xFF;
         AnimTransform[] var8 = new AnimTransform[]{AnimTransform.NULL, AnimTransform.VERTEX, AnimTransform.field1210, AnimTransform.COLOUR, AnimTransform.TRANSPARENCY, AnimTransform.field1213};
         AnimTransform var9 = (AnimTransform) SerialEnum.for_id((SerialEnum[]) var8, var7);
         if (var9 == null) {
            var9 = AnimTransform.NULL;
         }

         int var14 = ByteBufferUtils.get_short(packet);
         AnimationChannel var10 = AnimationChannel.lookup_by_id(packet.get() & 0xFF);
         AnimationKeyFrame var11 = new AnimationKeyFrame();
         var11.deserialise(packet, version);
         int count = var9.get_dimensions();
         AnimationKeyFrame[][] transforms;
         if (AnimTransform.VERTEX == var9) {
            transforms = this.skeletal_transforms;
         } else {
            transforms = this.transforms;
         }

         if (transforms[var14] == null) {
            transforms[var14] = new AnimationKeyFrame[count];
         }

         transforms[var14][var10.get_component()] = var11;
         if (AnimTransform.TRANSPARENCY == var9) {
            this.modifies_trans = true;
         }
      }
      int read_size = packet.position() - before_read;

      if(read_size != frame_size) {
         throw new RuntimeException("AnimKeyFrameSet size mismatch! keyframe " + keyframe_id + ", frame size: " + frame_size + ", actual read: " + read_size);

      }

   }

   public int get_keyframeid() {
      return this.keyframe_id;
   }

   public boolean modifies_alpha() {
      return this.modifies_trans;
   }

   public void apply_transforms(int tick, AnimationBone bone, int transform_index, int keyframe) {
      Matrix4f bone_matrix = Matrix4f.take();
      this.apply_rotation_transforms(bone_matrix, transform_index, bone, tick);
      this.apply_scale_transforms(bone_matrix, transform_index, bone, tick);
      this.apply_translation_transforms(bone_matrix, transform_index, bone, tick);
      bone.set_bone_transform(bone_matrix);
      bone_matrix.release();
   }

   void apply_rotation_transforms(Matrix4f trans_matrix, int transform_index, AnimationBone bone, int tick) {
      float[] rotations = bone.get_rotation(this.keyframe_id);
      float x = rotations[0];
      float y = rotations[1];
      float z = rotations[2];
      if (null != this.skeletal_transforms[transform_index]) {
         AnimationKeyFrame xrot_transform = this.skeletal_transforms[transform_index][0];
         AnimationKeyFrame yrot_transform = this.skeletal_transforms[transform_index][1];
         AnimationKeyFrame zrot_transform = this.skeletal_transforms[transform_index][2];
         if (xrot_transform != null) {
            x = xrot_transform.get_value(tick);
         }

         if (yrot_transform != null) {
            y = yrot_transform.get_value(tick);
         }

         if (zrot_transform != null) {
            z = zrot_transform.get_value(tick);
         }
      }

      Quaternion xrot = Quaternion.get();
      xrot.rotate(1.0F, 0.0F, 0.0F, x);
      Quaternion yrot = Quaternion.get();
      yrot.rotate(0.0F, 1.0F, 0.0F, y);
      Quaternion zrot = Quaternion.get();
      zrot.rotate(0.0F, 0.0F, 1.0F, z);
      Quaternion var13 = Quaternion.get();
      var13.mul(zrot);
      var13.mul(xrot);
      var13.mul(yrot);
      Matrix4f var14 = Matrix4f.take();
      var14.set_rotation(var13);
      trans_matrix.mul(var14);
      xrot.release();
      yrot.release();
      zrot.release();
      var13.release();
      var14.release();
   }

   void apply_translation_transforms(Matrix4f trans_matrix, int transform_index, AnimationBone bone, int tick) {
      float[] var6 = bone.get_translation(this.keyframe_id);
      float var7 = var6[0];
      float var8 = var6[1];
      float var9 = var6[2];
      if (this.skeletal_transforms[transform_index] != null) {
         AnimationKeyFrame var10 = this.skeletal_transforms[transform_index][3];
         AnimationKeyFrame var11 = this.skeletal_transforms[transform_index][4];
         AnimationKeyFrame var12 = this.skeletal_transforms[transform_index][5];
         if (var10 != null) {
            var7 = var10.get_value(tick);
         }

         if (null != var11) {
            var8 = var11.get_value(tick);
         }

         if (null != var12) {
            var9 = var12.get_value(tick);
         }
      }

      trans_matrix.values[12] = var7;
      trans_matrix.values[13] = var8;
      trans_matrix.values[14] = var9;
   }

   void apply_scale_transforms(Matrix4f transform_matrix, int transform_index, AnimationBone bone, int tick) {
      float[] var6 = bone.get_scale(this.keyframe_id);
      float var7 = var6[0];
      float var8 = var6[1];
      float var9 = var6[2];
      if (this.skeletal_transforms[transform_index] != null) {
         AnimationKeyFrame xscale_op = this.skeletal_transforms[transform_index][6];
         AnimationKeyFrame yscale_op = this.skeletal_transforms[transform_index][7];
         AnimationKeyFrame zscale_op = this.skeletal_transforms[transform_index][8];
         if (xscale_op != null) {
            var7 = xscale_op.get_value(tick);
         }

         if (yscale_op != null) {
            var8 = yscale_op.get_value(tick);
         }

         if (zscale_op != null) {
            var9 = zscale_op.get_value(tick);
         }
      }

      Matrix4f var13 = Matrix4f.take();
      var13.set_scale(var7, var8, var9);
      transform_matrix.mul(var13);
      var13.release();
   }

}
