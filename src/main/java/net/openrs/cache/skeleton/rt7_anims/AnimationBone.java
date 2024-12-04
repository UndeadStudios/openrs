package net.openrs.cache.skeleton.rt7_anims;


import net.openrs.cache.util.jagex.jagex3.math.Matrix4f;
import net.openrs.util.ByteBufferUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AnimationBone {
   boolean needs_pose_to_skin_transform = true;
   boolean needs_bone_to_pose_transform = true;
   Matrix4f bone_pose_transform = new Matrix4f();
   Matrix4f skinning_matrix = new Matrix4f();
   Matrix4f bone_transforms = new Matrix4f();
   Matrix4f[] world_matrix;
   Matrix4f[] inverse_local_matrix;
   float[][] translations;
   float[][] rotations;
   float[][] scale;
   float[][] bone_vertices;
   final Matrix4f[] local_matrix;
   public AnimationBone parent;
   public int parent_id;

   public AnimationBone(int frame_count, ByteBuffer packet, boolean compact) {
      this.parent_id = ByteBufferUtils.get_short(packet);
      this.local_matrix = new Matrix4f[frame_count];
      this.world_matrix = new Matrix4f[this.local_matrix.length];
      this.inverse_local_matrix = new Matrix4f[this.local_matrix.length];
      this.bone_vertices = new float[this.local_matrix.length][3];

      for(int frame_id = 0; frame_id < this.local_matrix.length; ++frame_id) {
         this.local_matrix[frame_id] = new Matrix4f(packet, compact);
         this.bone_vertices[frame_id][0] = ByteBufferUtils.get_float(packet);
         this.bone_vertices[frame_id][1] = ByteBufferUtils.get_float(packet);
         this.bone_vertices[frame_id][2] = ByteBufferUtils.get_float(packet);
      }

      this.load_local_space();
   }
   public AnimationBone(int frame_count, DataOutputStream packet, boolean compact) throws IOException {
      packet.writeShort(this.parent_id);
      this.local_matrix = new Matrix4f[frame_count];
      this.world_matrix = new Matrix4f[this.local_matrix.length];
      this.inverse_local_matrix = new Matrix4f[this.local_matrix.length];
      this.bone_vertices = new float[this.local_matrix.length][3];

      for(int frame_id = 0; frame_id < this.local_matrix.length; ++frame_id) {
         this.local_matrix[frame_id] = new Matrix4f();
         packet.writeFloat(this.bone_vertices[frame_id][0]);
         packet.writeFloat(this.bone_vertices[frame_id][1]);
         packet.writeFloat(this.bone_vertices[frame_id][2]);
      }

      this.load_local_space();
   }
   void load_local_space() {
      this.rotations = new float[this.local_matrix.length][3];
      this.translations = new float[this.local_matrix.length][3];
      this.scale = new float[this.local_matrix.length][3];
      Matrix4f var2 = Matrix4f.take();

      for(int i = 0; i < this.local_matrix.length; ++i) {
         Matrix4f local = this.get_local_matrix(i);
         var2.set(local);
         var2.invert();
         this.rotations[i] = var2.get_euler_angles_yxz_inverse();
         this.translations[i][0] = local.values[12];
         this.translations[i][1] = local.values[13];
         this.translations[i][2] = local.values[14];
         this.scale[i] = local.get_scale();
      }

      var2.release();
   }

   Matrix4f get_local_matrix(int arg0) {
      return this.local_matrix[arg0];
   }

   Matrix4f get_world_matrix(int frame_id) {
      if (null == this.world_matrix[frame_id]) {
         this.world_matrix[frame_id] = new Matrix4f(this.get_local_matrix(frame_id));
         if (null != this.parent) {
            this.world_matrix[frame_id].mul(this.parent.get_world_matrix(frame_id));
         } else {
            this.world_matrix[frame_id].mul(Matrix4f.IDENTITY);
         }
      }

      return this.world_matrix[frame_id];
   }

   Matrix4f get_inverse_local_matrix(int arg0) {
      if (null == this.inverse_local_matrix[arg0]) {
         this.inverse_local_matrix[arg0] = new Matrix4f(this.get_world_matrix(arg0));
         this.inverse_local_matrix[arg0].invert();
      }

      return this.inverse_local_matrix[arg0];
   }

   void set_bone_transform(Matrix4f arg0) {
      this.bone_transforms.set(arg0);
      this.needs_bone_to_pose_transform = true;
      this.needs_pose_to_skin_transform = true;
   }

   Matrix4f bone_transforms() {
      return this.bone_transforms;
   }

   Matrix4f get_relative_bone_pose() {
      try {
         if (this.needs_bone_to_pose_transform) {
            this.bone_pose_transform.set(this.bone_transforms());
            if (this.parent != null) {
               this.bone_pose_transform.mul(this.parent.get_relative_bone_pose());
            }

            this.needs_bone_to_pose_transform = false;
         }
      } catch(StackOverflowError e) {
         System.out.println("parent " + parent_id + ", " + parent + ", parent parent: " + (parent != null ? parent.parent_id : -1)+ ", bone_pose_transform: " + bone_pose_transform);
         System.exit(1);
      }

      return this.bone_pose_transform;
   }

   public Matrix4f get_skinning(int frame_id) {
      if (this.needs_pose_to_skin_transform) {
         this.skinning_matrix.set(this.get_inverse_local_matrix(frame_id));
         this.skinning_matrix.mul(this.get_relative_bone_pose());
         this.needs_pose_to_skin_transform = false;
      }

      return this.skinning_matrix;
   }

   float[] get_rotation(int arg0) {
      return this.rotations[arg0];
   }

   float[] get_translation(int arg0) {
      return this.translations[arg0];
   }

   float[] get_scale(int arg0) {
      return this.scale[arg0];
   }


   public void encode(DataOutputStream dos) throws IOException {
      // Write the parent ID
      dos.writeShort(parent_id);

      // Write the number of frames
      dos.writeInt(local_matrix.length);

      // Encode local matrices and vertex data
      for (int frameId = 0; frameId < local_matrix.length; frameId++) {
         // Encode the local matrix
         local_matrix[frameId].encode(dos); // Assuming Matrix4f has an `encode` method

         // Write vertex data
         dos.writeFloat(bone_vertices[frameId][0]);
         dos.writeFloat(bone_vertices[frameId][1]);
         dos.writeFloat(bone_vertices[frameId][2]);
      }
   }

}
