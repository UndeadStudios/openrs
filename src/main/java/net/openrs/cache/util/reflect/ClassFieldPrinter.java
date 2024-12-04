package net.openrs.cache.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ClassFieldPrinter {

    private Map<String, Object> defaultFields = new HashMap<>();

    private StringBuilder builder = new StringBuilder();

    public void setDefaultObject(Object obj) {
        defaultFields.clear();
        try {
            final Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {

                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                field.setAccessible(true);

                final String name = field.getName();

                final Object value = field.get(obj);

                if (value == null) {
                    continue;
                }

                defaultFields.put(name, value);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printFields(Object obj) throws Exception {
        printFields(obj, "", false);
    }

    public void printFields(Object obj, String prefix) throws Exception {
        printFields(obj, prefix, false);
    }

    public void printFields(Object obj, String prefix, boolean printDefaults) throws Exception {

        final Field[] fields = obj.getClass().getDeclaredFields();

        final Map<String, Object> map = new TreeMap<>();

        for (Field field : fields) {

            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            final String name = field.getName();

            final Object value = field.get(obj);

            if (value == null) {
                continue;
            }

            map.put(name, value);

        }

        if (!map.isEmpty()) {
            builder.append(String.format("case %d:\n", (int)map.getOrDefault("id", -1)));
        }

        for (Map.Entry<String, Object> set : map.entrySet()) {
            String key = set.getKey();
            Object value = set.getValue();

            if (key.equalsIgnoreCase("id")) {
                continue;
            }

            final String text = convert(value);

            if (!printDefaults) {
                String text2 = convert(defaultFields.get(key));

                if (text.equals(text2)) {
                    continue;
                }
            }

            if (prefix.isEmpty()) {
                builder.append(String.format("\t%s = %s\n", key, text));
            } else {
                builder.append(String.format("\t%s%s = %s\n", prefix, key, text));
            }

        }

        builder.append("break;\n");
        builder.append("\n");
    }

    private static String convert(Object value) {
        if (value == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        if (value instanceof String[]) {
            builder.append("new String[] {");

            String[] array = (String[])value;

            for (int i = 0; i < array.length; i++) {
                String s = array[i];
                builder.append("\"").append(s).append("\"");
                if (i < array.length - 1) {
                    builder.append(", ");
                }
            }
            builder.append("};");
        } else if (value instanceof short[]) {
            builder.append("new short[] {");
            short[] array = (short[]) value;
            for (int i = 0; i < array.length; i++) {
                short s = array[i];
                builder.append(s);
                if (i < array.length - 1) {
                    builder.append(",");
                }
            }
            builder.append("};");
        } else if (value instanceof int[]) {
            builder.append("new int[] {");
            int[] array = (int[]) value;
            for (int i = 0; i < array.length; i++) {
                int s = array[i];
                builder.append(s);
                if (i < array.length - 1) {
                    builder.append(",");
                }
            }
            builder.append("};");
        } else {
            builder.append(value.toString()).append(";");
        }

        return builder.toString();
    }

    public StringBuilder getBuilder() {
        return builder;
    }

}