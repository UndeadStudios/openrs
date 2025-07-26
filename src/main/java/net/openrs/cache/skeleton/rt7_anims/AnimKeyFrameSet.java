package net.openrs.cache.skeleton.rt7_anims;

import net.openrs.cache.skeleton.Skin;
import net.openrs.cache.util.jagex.core.constants.SerialEnum;
import net.openrs.cache.util.jagex.jagex3.math.Matrix4f;
import net.openrs.cache.util.jagex.jagex3.math.Quaternion;
import net.openrs.util.ByteBufferUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class AnimKeyFrameSet {
   boolean modifies_trans;
   AnimationKeyFrame[][] skeletal_transforms = null;
   public int frameset_id;
   int keyframe_id = 0;
   public AnimationKeyFrame[][] transforms = null;
   public Skin base;

   public static AnimKeyFrameSet[] keyframesetset;
   private int frame_size;
   private int rtog;
   private int rtog2;
   private int var4;
   public static Map<Integer, AnimKeyFrameSet> keyframesetMap = new HashMap<>();

   public static void register(int groupId, AnimKeyFrameSet set) {
      keyframesetMap.put(groupId, set);
   }

   public static AnimKeyFrameSet get(int groupId) {
      return keyframesetMap.get(groupId);
   }

   public static boolean contains(int groupId) {
      return keyframesetMap.containsKey(groupId);
   }

   public static void clear() {
      keyframesetMap.clear();
   }

   public AnimKeyFrameSet() {}

   public static AnimKeyFrameSet init() {
      return null;
   }

   public static SkeletalAnimBase load(int group, ByteBuffer keyframeBuffer) {
      try {
         int baseSize = keyframeBuffer.getInt();
         byte[] baseData = new byte[baseSize];
         keyframeBuffer.get(baseData, 0, baseSize);
         ByteBuffer baseBuffer = ByteBuffer.wrap(baseData);

         int unusedByte = keyframeBuffer.get() & 0xFF;
         int version = keyframeBuffer.get() & 0xFF;
         int baseId = keyframeBuffer.getShort() & 0xFFFF;

         System.out.println("Loading keyframe set " + group + ", base_id: " + baseId + ", version: " + version);

         AnimKeyFrameSet keyframeSet = new AnimKeyFrameSet();
         keyframeSet.frameset_id = group;

         try {
            keyframeSet.base = Skin.decode(baseBuffer, true, baseSize);
         } catch (RuntimeException e) {
            keyframesetMap.remove(group);
            System.err.println("Error decoding base for keyframe set: " + group);
            e.printStackTrace();
            return null;
         }

         try {
            keyframeSet.decode(keyframeBuffer, version);
            register(group, keyframeSet);
         } catch (RuntimeException e) {
            keyframesetMap.remove(group);
            System.err.println("Error decoding keyframes for group: " + group + ". File size: " + keyframeBuffer);
            e.printStackTrace();
            return null;
         }

         return keyframeSet.base.get_skeletal_animbase();

      } catch (Exception e) {
         System.err.println("Error unpacking keyframes for group: " + group);
         e.printStackTrace();
         return null;
      }
   }

   public void encode(DataOutputStream dos) throws IOException {

      dos.writeInt(frameset_id);
      dos.writeInt(keyframe_id);
      dos.writeBoolean(modifies_trans);

      if (transforms != null) {
         dos.writeInt(transforms.length);
         for (AnimationKeyFrame[] transformGroup : transforms) {
            if (transformGroup != null) {
               dos.writeInt(transformGroup.length);
               for (AnimationKeyFrame frame : transformGroup) {
                  if (frame != null) {
                     frame.encode(dos);
                  } else {
                     dos.writeInt(0);
                  }
               }
            } else {
               dos.writeInt(0);
            }
         }
      } else {
         dos.writeInt(0);
      }

      if (base != null) {
         dos.writeBoolean(true);
         base.encode(dos, false);
      } else {
         dos.writeBoolean(false);
      }
   }

   public void decode(ByteBuffer packet, int version) {
      frame_size = packet.get() & 0xFF;
      int before_read = packet.position();
      rtog = packet.getShort() & 0xFFFF;
      rtog2 = packet.getShort() & 0xFFFF;
      this.keyframe_id = packet.get() & 0xFF;
      var4 = packet.getShort() & 0xFFFF;

      this.skeletal_transforms = new AnimationKeyFrame[this.base.get_skeletal_animbase().bones.length][];
      this.transforms = new AnimationKeyFrame[this.base.transforms_count()][];

      for (int var5 = 0; var5 < var4; ++var5) {
         int var7 = packet.get() & 0xFF;
         AnimTransform[] var8 = new AnimTransform[]{AnimTransform.NULL, AnimTransform.VERTEX, AnimTransform.field1210, AnimTransform.COLOUR, AnimTransform.TRANSPARENCY, AnimTransform.field1213};
         AnimTransform var9 = (AnimTransform) SerialEnum.for_id(var8, var7);
         if (var9 == null) var9 = AnimTransform.NULL;

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
         if (AnimTransform.TRANSPARENCY == var9) this.modifies_trans = true;
      }

      int read_size = packet.position() - before_read;
      if (read_size != frame_size) {
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
}
