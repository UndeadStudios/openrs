package net.openrs.cache.tools.field_dumpers;

import java.io.File;
import java.io.PrintWriter;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.items.ItemType;
import net.openrs.cache.type.items.ItemTypeList;
import net.openrs.cache.util.reflect.ClassFieldPrinter;

public class ItemDefinitionFieldDumper {

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            ItemTypeList list = new ItemTypeList();
            list.initialize(cache);

            try(PrintWriter writer = new PrintWriter(new File("E:/dump/fields/items.txt"))) {
                ClassFieldPrinter printer = new ClassFieldPrinter();
                for (int i = 0; i < list.size(); i++) {
                    ItemType type = list.list(i);

                    if (type == null || type.getName() == null) {
                        continue;
                    }

                    try {
                        printer.setDefaultObject(new ItemType(i));
                        printer.printFields(type, "type.");
                    } catch (Exception ignored) {

                    }

                }

                writer.write(printer.getBuilder().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
