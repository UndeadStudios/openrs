package net.openrs.cache.skeleton.rt7_anims;

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
   public int frameset_id;// same as keyframe_id prob
   int keyframe_id = 0;
   public AnimationKeyFrame[][] transforms = null;
   public Skin base;


   public static AnimKeyFrameSet[] keyframesetset;
   private int frame_size;
   private int rtog;
   private int rtog2;
   private int var4;

   public AnimKeyFrameSet() {}

   public static AnimKeyFrameSet init() {
      keyframesetset = new AnimKeyFrameSet[5500];
      return null;
   }
   public static SkeletalAnimBase load(int group, ByteBuffer keyframeBuffer) {
      try {

         // Read base size and extract base data
         int baseSize = keyframeBuffer.getInt();

         byte[] baseData = new byte[baseSize];
         keyframeBuffer.get(baseData, 0, baseSize);
         ByteBuffer baseBuffer = ByteBuffer.wrap(baseData);

         // Read remaining information from keyframe buffer
         int unusedByte = keyframeBuffer.get() & 0xFF; // Ensure you understand the purpose of this byte
         int version = keyframeBuffer.get() & 0xFF;
         int baseId = keyframeBuffer.getShort() & 0xFFFF;

         System.out.println("Loading keyframe set " + group + ", base_id: " + baseId + ", version: " + version);

         // Initialize keyframe set
         AnimKeyFrameSet keyframeSet = keyframesetset[group] = new AnimKeyFrameSet();
         keyframeSet.frameset_id = group;

         // Decode the base
         try {
            keyframeSet.base = Skin.decode(baseBuffer, false, baseSize);
         } catch (RuntimeException e) {
            keyframesetset[group] = null;
            System.err.println("Error decoding base for keyframe set: " + group);
            e.printStackTrace();
            return null;
         }

         // Decode keyframes
         try {
            keyframeSet.decode(keyframeBuffer, version);
         } catch (RuntimeException e) {
            keyframesetset[group] = null;
            System.err.println("Error decoding keyframes for group: " + group + ". File size: " + keyframeBuffer);
            e.printStackTrace();
            return null;
         }

         // Return the resulting Skeleton object
         return keyframeSet.base.get_skeletal_animbase();

      } catch (Exception e) {
         System.err.println("Error unpacking keyframes for group: " + group);
         e.printStackTrace();
         return null;
      }
   }

   public void encode(DataOutputStream dos) throws IOException {
      // Write the frame set ID
      dos.writeInt(frameset_id);

      // Write the keyframe ID
      dos.writeInt(keyframe_id);

      // Write the modifies_trans flag
      dos.writeBoolean(modifies_trans);

      // Write the count of transforms
      if (transforms != null) {
         dos.writeInt(transforms.length);

         // Write details of each transform
         for (AnimationKeyFrame[] transformGroup : transforms) {
            if (transformGroup != null) {
               dos.writeInt(transformGroup.length); // Number of frames in this transform group
               for (AnimationKeyFrame frame : transformGroup) {
                  if (frame != null) {
                     frame.encode(dos); // Assuming AnimationKeyFrame has an encode method
                  } else {
                     dos.writeInt(0); // No frame data
                  }
               }
            } else {
               dos.writeInt(0); // No transforms in this group
            }
         }
      } else {
         dos.writeInt(0); // No transforms at all
      }

      // Write the base skin if exists
      if (base != null) {
         dos.writeBoolean(true);
         base.encode(dos, false); // Assuming Skin has an encode method that accepts a DataOutputStream and a boolean for 'highrev'
      } else {
         dos.writeBoolean(false);
      }
   }

   public  void decode(ByteBuffer packet, int version) {
       frame_size = packet.getInt();
      int before_read = packet.position();
       rtog = packet.getShort() & 0xFFFF;//starttick
       rtog2 = packet.getShort() & 0xFFFF;
      this.keyframe_id = packet.get() & 0xFF;//keyframe
       var4 = packet.getShort() & 0xFFFF;
      this.skeletal_transforms = new AnimationKeyFrame[this.base.get_skeletal_animbase().bones.length][];
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
   public boolean ghas_keyframeid() {
      return this.keyframe_id != 0;
   }
   public boolean modifies_alpha() {
      return this.modifies_trans;
   }


   public void encode(DataOutputStream dos, int version) throws IOException {
      // Write the frame size
      dos.writeInt(frame_size);

      // Write the rtog and rtog2 values (e.g., start tick and end tick)
      dos.writeShort(rtog);
      dos.writeShort(rtog2);

      // Write the keyframe ID
      dos.writeByte(keyframe_id);

      // Write the count of transformation groups (var4)
      dos.writeShort(var4);

      // Encode skeletal transforms
      if (skeletal_transforms != null) {
         dos.writeBoolean(true);
         dos.writeInt(skeletal_transforms.length);
         for (AnimationKeyFrame[] transformGroup : skeletal_transforms) {
            if (transformGroup != null) {
               dos.writeInt(transformGroup.length);
               for (AnimationKeyFrame keyFrame : transformGroup) {
                  if (keyFrame != null) {
                     keyFrame.encode(dos); // Assuming AnimationKeyFrame has its own encode method
                  } else {
                     dos.writeBoolean(false); // Indicate no frame present
                  }
               }
            } else {
               dos.writeInt(0); // No transforms in this group
            }
         }
      } else {
         dos.writeBoolean(false); // No skeletal transforms
      }

      // Encode non-skeletal transforms
      if (transforms != null) {
         dos.writeBoolean(true);
         dos.writeInt(transforms.length);
         for (AnimationKeyFrame[] transformGroup : transforms) {
            if (transformGroup != null) {
               dos.writeInt(transformGroup.length);
               for (AnimationKeyFrame keyFrame : transformGroup) {
                  if (keyFrame != null) {
                     keyFrame.encode(dos); // Assuming AnimationKeyFrame has its own encode method
                  } else {
                     dos.writeBoolean(false); // Indicate no frame present
                  }
               }
            } else {
               dos.writeInt(0); // No transforms in this group
            }
         }
      } else {
         dos.writeBoolean(false); // No transforms
      }

      // Encode the base (Skin) if present
      if (base != null) {
         dos.writeBoolean(true);
         base.encode(dos, false); // Assuming Skin has an encode method
      } else {
         dos.writeBoolean(false);
      }
   }

}
