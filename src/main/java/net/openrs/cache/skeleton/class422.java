package net.openrs.cache.skeleton;

import java.nio.ByteBuffer;

public abstract class class422 {
    protected abstract class424 vmethod8461(int var1);

 //   public int method8001() {
       // return super.field3035;
   // }

   // public Object vmethod8966(int var1) {
     //   class424 var2 = this.vmethod8461(var1);
      //  return var2 != null && var2.method8024() ? var2.method8017() : null;
  //  }

    public class482 method8003(ByteBuffer var1) {
        int var2 = var1.getShort() & 0xFFFF;
        class424 var3 = this.vmethod8461(var2);
        class482 var4 = new class482(var2);
        Class var5 = var3.field4674.field4901;
        if (var5 == Integer.class) {
            var4.field5004 = var1.getInt();
        } else if (var5 == Long.class) {
            var4.field5004 = var1.getLong();
        } else if (var5 == String.class) {
            //var4.field5004 = var1.readStringCp1252NullCircumfixed();
        } else {
        }

        return var4;
    }
}
