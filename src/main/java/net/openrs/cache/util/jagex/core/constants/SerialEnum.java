package net.openrs.cache.util.jagex.core.constants;

public interface SerialEnum {

    static SerialEnum for_id(SerialEnum[] values, int id) {
        for(int var4 = 0; var4 < values.length; ++var4) {
            SerialEnum val = values[var4];
            if (id == val.serial_id()) {
                return val;
            }
        }

        return null;
    }

    int serial_id();
}
