package net.openrs.cache.util;

public class ArrayUtils {

    public static <T> boolean isEmpty(T[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                return false;
            }
        }
        return true;
    }
    public static byte[] toByteArray(String s) {
        byte[] data = new byte[s.length()];

        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)s.charAt(i);
        }

        return data;
    }

    public static <T1, T2> boolean arraysMatch(T1[] array1, T2[] array2) {
        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

}
