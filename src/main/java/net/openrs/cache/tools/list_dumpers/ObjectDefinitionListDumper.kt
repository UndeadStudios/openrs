package net.openrs.cache.tools.list_dumpers

import net.openrs.cache.Cache
import net.openrs.cache.Constants
import net.openrs.cache.FileStore
import net.openrs.cache.type.items.ItemType
import net.openrs.cache.type.items.ItemTypeList
import net.openrs.cache.type.npcs.NpcTypeList
import net.openrs.cache.type.objects.ObjectTypeList
import net.openrs.cache.util.reflect.ClassFieldPrinter
import java.io.File
import java.io.PrintWriter

object ObjectDefinitionListDumper {
    @JvmStatic
    fun main(args: Array<String>) {
            Cache(FileStore.open(Constants.CACHE_PATH)).use { cache ->
                val list = ObjectTypeList()
                list.initialize(cache)
                PrintWriter(File("E:/dump/lists/object_list.txt")).use { writer ->
                    for (i in 0 until list.size()) {
                        val type = list.list(i)
                        if (type == null || type.name == null) {
                            continue
                        }
                        writer.println("$i: ${type.name}")
                    }
                }
            }
        }
}
