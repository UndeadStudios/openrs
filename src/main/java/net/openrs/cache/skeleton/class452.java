package net.openrs.cache.skeleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class class452 {

    Map field4849;
    final class481 field4848;

    public class452(class481 var1) {
        this.field4848 = var1;
    }

    public int vmethod8375(int var1) {
        if (this.field4849 != null) {
            class482 var2 = (class482)this.field4849.get(var1);
            if (var2 != null) {
                return (Integer)var2.field5004;
            }
        }

        return (Integer)this.field4848.vmethod8966(var1);
    }

    public void vmethod8378(int var1, Object var2) {
        if (this.field4849 == null) {
            this.field4849 = new HashMap();
            this.field4849.put(var1, new class482(var1, var2));
        } else {
            class482 var3 = (class482)this.field4849.get(var1);
            if (var3 == null) {
                this.field4849.put(var1, new class482(var1, var2));
            } else {
                var3.field5004 = var2;
            }
        }

    }

    public Iterator iterator() {
        return this.field4849 == null ? Collections.emptyList().iterator() : this.field4849.values().iterator();
    }
}
