/**
 * Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.openrs.cache.tools.model_dumpers

import net.openrs.cache.Cache
import net.openrs.cache.Constants
import net.openrs.cache.FileStore
import net.openrs.cache.type.npcs.NpcTypeList
import net.openrs.cache.util.CompressionUtils
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * Dumps only models for npcs
 *
 * @author Freyr
 *
 * @since 04/01/2017
 */
object NpcModelDumper {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val directory = File(Constants.MODEL_PATH + "/npcs")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        Cache(FileStore.open(Constants.CACHE_PATH)).use { cache ->
            val list = NpcTypeList()
            list.initialize(cache)
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
            }
            val table = cache.getReferenceTable(7)
            val itr = set.iterator()
            var count = 0
            val size = set.size
            while (itr.hasNext()) {
                val i = itr.next()
                if (table.getEntry(i) == null) {
                    continue
                }
                val container = cache.read(7, i)
                val bytes = ByteArray(container.data.limit())
                container.data[bytes]
                DataOutputStream(
                    FileOutputStream(
                        File(
                            directory,
                            "$i.gz"
                        )
                    )
                ).use { dos -> dos.write(CompressionUtils.gzip(bytes)) }
                count++
                val progress = count.toDouble() / size * 100
                System.out.printf("%.2f%s\n", progress, "%")
                itr.remove()
            }
        }
    }
}
