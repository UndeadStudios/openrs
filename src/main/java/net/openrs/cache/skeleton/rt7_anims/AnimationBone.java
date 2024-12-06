package net.openrs.cache.skeleton.rt7_anims;


import net.openrs.cache.util.jagex.jagex3.math.Matrix4f;
import net.openrs.util.ByteBufferUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AnimationBone {
   int parent_id;
   float[][] bone_vertices;
   Matrix4f[] local_matrix;
   // Omit other fields if they're unnecessary for encoding

   // Constructor for reading data without using it
   public AnimationBone(int frame_count, ByteBuffer packet, boolean compact) {
      this.parent_id = ByteBufferUtils.get_short(packet);
      this.local_matrix = new Matrix4f[frame_count];
      this.bone_vertices = new float[frame_count][3];

      for (int frame_id = 0; frame_id < frame_count; ++frame_id) {
         // Parse the local matrix
         this.local_matrix[frame_id] = new Matrix4f(packet, compact);

         // Parse the vertex data
         this.bone_vertices[frame_id][0] = ByteBufferUtils.get_float(packet);
         this.bone_vertices[frame_id][1] = ByteBufferUtils.get_float(packet);
         this.bone_vertices[frame_id][2] = ByteBufferUtils.get_float(packet);
      }
   }

   // Encode method for saving data
   public void encode(DataOutputStream dos) throws IOException {
      // Write parent ID
      dos.writeShort(parent_id);

      // Write each frame's data
      for (int frame_id = 0; frame_id < local_matrix.length; ++frame_id) {
         // Write local matrix
         local_matrix[frame_id].encode(dos);

         // Write vertex data
         dos.writeFloat(bone_vertices[frame_id][0]);
         dos.writeFloat(bone_vertices[frame_id][1]);
         dos.writeFloat(bone_vertices[frame_id][2]);
      }
   }
}
