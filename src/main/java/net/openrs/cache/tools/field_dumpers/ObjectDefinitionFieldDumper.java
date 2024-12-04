package net.openrs.cache.tools.field_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.objects.ObjectType;
import net.openrs.cache.type.objects.ObjectTypeList;
import net.openrs.cache.util.reflect.ClassFieldPrinter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ObjectDefinitionFieldDumper {

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            ObjectTypeList list = new ObjectTypeList();
            list.initialize(cache);

            File dir = new File("E:/dump/fields");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            try(PrintWriter writer = new PrintWriter(new File(dir, "obj_fields.txt"))) {
                for (int i = 0; i < list.size(); i++) {
                    ObjectType type = list.list(i);

                    if (type == null || type.name == null) {
                        continue;
                    }

                    try {
                        ClassFieldPrinter printer = new ClassFieldPrinter();
                        printer.setDefaultObject(new ObjectType(i));
                        printer.printFields(type);

                        writer.print(printer.getBuilder().toString());
                    } catch (Exception ex) {

                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
