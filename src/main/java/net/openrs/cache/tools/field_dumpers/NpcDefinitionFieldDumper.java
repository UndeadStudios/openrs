package net.openrs.cache.tools.field_dumpers;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.type.npcs.NpcType;
import net.openrs.cache.type.npcs.NpcTypeList;
import net.openrs.cache.util.reflect.ClassFieldPrinter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class NpcDefinitionFieldDumper {

    public static void main(String[] args) {
        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            NpcTypeList list = new NpcTypeList();
            list.initialize(cache);

            ClassFieldPrinter printer = new ClassFieldPrinter();
            printer.setDefaultObject(new NpcType(0));
            try(PrintWriter writer = new PrintWriter(new File("E:/dump/fields/npcdef_fields.txt"))) {
                for (int i = 0; i < list.size(); i++) {
                    NpcType type = list.list(i);

                    if (type == null || type.name == null) {
                        continue;
                    }

                    printer.printFields(type, "type.");

                }
                writer.print(printer.getBuilder());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
