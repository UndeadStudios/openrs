package net.openrs.cache.tools.list_dumpers

import net.openrs.cache.Cache
import net.openrs.cache.Constants
import net.openrs.cache.FileStore
import net.openrs.cache.type.npcs.NpcTypeList
import java.io.File
import java.io.PrintWriter
import java.util.*

object NpcDefinitionListDumper {
    @JvmStatic
    fun main(args: Array<String>) {
            Cache(FileStore.open(Constants.CACHE_PATH)).use { cache ->
                val list = NpcTypeList()
                list.initialize(cache)
                PrintWriter(File("E:/dump/lists/npc_list.txt")).use { writer ->
                    val set: MutableSet<Int> = HashSet()
                    for (i in 0 until list.size()) {
                        val npc = list.list(i) ?: continue
                        val models = npc.models
                        if (models != null) {
                            Arrays.stream(models).forEach { e: Int -> set.add(e) }
                        }
                        val headModels = npc.models_2
                        if (headModels != null) {
                            Arrays.stream(headModels).forEach { e: Int -> set.add(e) }
                        }
                        val container = cache.read(7, i)
                        val bytes = ByteArray(container.data.limit())
                        container.data[bytes]
                        writer.println("${npc.id}: ${npc.name}")
                        }
                }
            }
        }
}
