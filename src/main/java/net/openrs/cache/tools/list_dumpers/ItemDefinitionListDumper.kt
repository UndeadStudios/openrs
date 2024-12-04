package net.openrs.cache.tools.list_dumpers

import net.openrs.cache.Cache
import net.openrs.cache.Constants
import net.openrs.cache.FileStore
import net.openrs.cache.type.items.ItemType
import net.openrs.cache.type.items.ItemTypeList
import net.openrs.cache.util.reflect.ClassFieldPrinter
import java.io.File
import java.io.PrintWriter

object ItemDefinitionListDumper {
    @JvmStatic
    fun main(args: Array<String>) {
        Cache(FileStore.open(Constants.CACHE_PATH)).use { cache ->
            val list = ItemTypeList()
            list.initialize(cache)
            PrintWriter(File("E:/dump/lists/item_list.txt")).use { writer ->
                for (i in 0 until list.size()) {
                    val type = list.list(i)
                    if (type == null || type.name == null) {
                        continue
                    }
                    writer.println("$i: ${type.name}, inv_model: ${type.inventoryModel}, male_model: ${type.maleModel}, female_model: ${type.femaleModel}, male_model1: ${type.maleModel1}, female_model1: ${type.femaleModel1}, examine: ${type.getExamine()}")
                }
            }
        }
    }
}