package net.openrs.cache.skeleton.rt7_anims;

import net.openrs.util.ByteBufferUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AnimKey {
   AnimKey next;
   float value;
   float start_val1 = Float.MAX_VALUE;
   float start_val2 = Float.MAX_VALUE;
   float end_val1 = Float.MAX_VALUE;
   float end_val2 = Float.MAX_VALUE;
   int tick;

   public AnimKey() {
   }

   void deserialise(ByteBuffer arg0, int version) {
      this.tick = ByteBufferUtils.get_short(arg0);
      this.value = ByteBufferUtils.get_float(arg0);
      this.start_val1 = ByteBufferUtils.get_float(arg0);
      this.start_val2 = ByteBufferUtils.get_float(arg0);
      this.end_val1 = ByteBufferUtils.get_float(arg0);
      this.end_val2 = ByteBufferUtils.get_float(arg0);
   }

   public void encode(DataOutputStream dos) throws IOException {
      dos.writeInt(tick);
      dos.writeFloat(value);
      dos.writeFloat(start_val1);
      dos.writeFloat(start_val2);
      dos.writeFloat(end_val1);
      dos.writeFloat(end_val2);
   }
}
