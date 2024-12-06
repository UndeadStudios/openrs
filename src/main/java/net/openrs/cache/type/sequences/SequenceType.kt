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
package net.openrs.cache.type.sequences

import net.openrs.cache.type.Type
import net.openrs.cache.util.Sound
import net.openrs.cache.util.Sound.Companion.encodeFrameSound
import net.openrs.util.revisionIsOrAfter
import net.openrs.util.revisionIsOrBefore
import net.openrs.util.write24bitInt
import java.io.DataOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

/**
 * @author Kyle Friz
 * @since Oct 18, 2015
 */
class SequenceType(private val id: Int) : Type {
    var chatFrameIds: IntArray? = null
    var priority = -1
    var frameIDs: IntArray? = null
    var frameLengths: IntArray? = null
    private var field2310: MutableMap<Int, MutableList<Sound>>? = null
    var frameSounds: Array<Sound?> = emptyArray()
    var frameStep = -1
    var interleaveLeave: IntArray? = null
    var isStretches = false
    var forcedPriority = 5
    var leftHandItem = -1
    var maxLoops = 99
    var rightHandItem = -1
    var replayMode = 2
    var precedenceAnimating = -1
    private var unknown: BooleanArray? = null
    private var field2174: MutableMap<Int, Sound> = emptyMap<Int, Sound>().toMutableMap()
    private var skeletalRangeBegin = -1
    private var skeletalRangeEnd = -1
    var skeletalId = -1

    override fun decode(buffer: ByteBuffer) {
        while (true) {
            val opcode = buffer.get().toInt() and 0xFF
            if (opcode == 0) {
                break
            }

            when (opcode) {
                1 -> {
                    val count = buffer.short.toInt() and 0xFFFF
                    frameLengths = IntArray(count)
                    frameIDs = IntArray(count)

                    for (i in 0 until count) {
                        frameLengths!![i] = buffer.short.toInt() and 0xFFFF
                    }
                    for (i in 0 until count) {
                        frameIDs!![i] = buffer.short.toInt() and 0xFFFF
                    }
                    for (i in 0 until count) {
                        frameIDs!![i] += (buffer.short.toInt() and 0xFFFF) shl 16
                    }
                }
                2 -> frameStep = buffer.short.toInt() and 0xFFFF
                3 -> {
                    val count = buffer.get().toInt() and 0xFF
                    interleaveLeave = IntArray(count + 1)
                    for (i in 0 until count) {
                        interleaveLeave!![i] = buffer.get().toInt() and 0xFF
                    }
                    interleaveLeave!![count] = 9999999
                }
                4 -> isStretches = true
                5 -> forcedPriority = buffer.get().toInt() and 0xFF
                6 -> leftHandItem = buffer.short.toInt() and 0xFFFF
                7 -> rightHandItem = buffer.short.toInt() and 0xFFFF
                8 -> maxLoops = buffer.get().toInt() and 0xFF
                9 -> precedenceAnimating = buffer.get().toInt() and 0xFF
                10 -> priority = buffer.get().toInt() and 0xFF
                11 -> replayMode = buffer.get().toInt() and 0xFF
                12 -> {
                    val count = buffer.get().toInt() and 0xFF
                    chatFrameIds = IntArray(count)
                    for (i in 0 until count) {
                        chatFrameIds!![i] = buffer.short.toInt() and 0xFFFF
                    }
                    for (i in 0 until count) {
                        chatFrameIds!![i] += (buffer.short.toInt() and 0xFFFF) shl 16
                    }
                }
                13 -> {
                    if (revisionIsOrAfter(226)) {
                        skeletalId = buffer.int
                    } else {
                        val count = buffer.get().toInt() and 0xFF
                        frameSounds = arrayOfNulls(count)
                        for (i in 0 until count) {
                            frameSounds[i] = Sound.readFrameSound(buffer, false)
                        }
                    }
                }
                14 -> {
                    if (revisionIsOrAfter(226)) {
                        val count = buffer.short.toInt() and 0xFFFF
                        field2310 = field2310 ?: mutableMapOf()
                        for (i in 0 until count) {
                            val frameId = buffer.short.toInt() and 0xFFFF
                            val sound = Sound.readFrameSound(buffer, false)
                            if (sound != null) {
                                field2310!!.computeIfAbsent(frameId) { mutableListOf() }.add(sound)
                            }
                        }
                    } else {
                        skeletalId = buffer.int
                    }
                }
                15 -> {
                    if (revisionIsOrAfter(226)) {
                        skeletalRangeBegin = buffer.short.toInt() and 0xFFFF
                        skeletalRangeEnd = buffer.short.toInt() and 0xFFFF
                    } else {
                        val count = buffer.short.toInt() and 0xFFFF
                        field2310 = field2310 ?: mutableMapOf()
                        for (i in 0 until count) {
                            val frameId = buffer.short.toInt() and 0xFFFF
                            val sound = Sound.readFrameSound(buffer, false)
                            if (sound != null) {
                                field2310!!.computeIfAbsent(frameId) { mutableListOf() }.add(sound)
                            }
                        }
                    }
                }
                16 -> {
                    if (revisionIsOrBefore(225)) {
                        skeletalRangeBegin = buffer.short.toInt() and 0xFFFF
                        skeletalRangeEnd = buffer.short.toInt() and 0xFFFF
                    }
                }
                17 -> {
                    unknown = BooleanArray(256) { false }
                    val count = buffer.get().toInt() and 0xFF
                    for (i in 0 until count) {
                        unknown!![buffer.get().toInt() and 0xFF] = true
                    }
                }
            }
        }
    }




    @Throws(IOException::class)
    override fun encode(dos: DataOutputStream) {
        frameLengths?.let { lengths ->
            frameIDs?.let { ids ->
                dos.writeByte(1)
                dos.writeShort(lengths.size)
                lengths.forEach { dos.writeShort(it) }
                ids.forEach { dos.writeShort(it and 0xFFFF) }
                ids.forEach { dos.writeShort(it shr 16) }
            }
        }

        if (frameStep != -1) {
            dos.writeByte(2)
            dos.writeShort(frameStep)
        }

        interleaveLeave?.let { interleave ->
            dos.writeByte(3)
            dos.writeByte(interleave.size - 1)
            for (i in 0 until interleave.size - 1) {
                dos.writeByte(interleave[i])
            }
        }

        if (isStretches) dos.writeByte(4)

        if (forcedPriority != 5) {
            dos.writeByte(5)
            dos.writeByte(forcedPriority)
        }

        if (leftHandItem != -1) {
            dos.writeByte(6)
            dos.writeShort(leftHandItem)
        }

        if (rightHandItem != -1) {
            dos.writeByte(7)
            dos.writeShort(rightHandItem)
        }

        if (maxLoops != 99) {
            dos.writeByte(8)
            dos.writeByte(maxLoops)
        }

        if (precedenceAnimating != -1) {
            dos.writeByte(9)
            dos.writeByte(precedenceAnimating)
        }

        if (priority != -1) {
            dos.writeByte(10)
            dos.writeByte(priority)
        }

        if (replayMode != 2) {
            dos.writeByte(11)
            dos.writeByte(replayMode)
        }

        chatFrameIds?.let { frames ->
            dos.writeByte(12)
            dos.writeByte(frames.size)
            frames.forEach { dos.writeShort(it and 0xFFFF) }
            frames.forEach { dos.writeShort(it shr 16) }
        }

        if (frameSounds.isNotEmpty()) {
            dos.writeByte(13)
            if (revisionIsOrAfter(226)) {
                dos.writeInt(skeletalId)
            } else {
                dos.writeByte(frameSounds.size)
                frameSounds.filterNotNull().forEach { sound ->
                    encodeFrameSound(sound, dos, false)
                }
            }
        }


            if (revisionIsOrAfter(226)) {
                if (skeletalId != -1) {
                    dos.writeByte(14)
                    dos.writeInt(skeletalId)
                }
            } else {
                field2310?.let { fieldMap ->
                dos.writeByte(14)
                dos.writeShort(fieldMap.size)
                    fieldMap.forEach { (key, soundList) ->
                    dos.writeShort(key)
                    soundList.forEach { sound ->
                        encodeFrameSound(sound as Sound, dos, false)
                    }
                }
            }
        }

        if (revisionIsOrAfter(226)) {
            if (skeletalRangeBegin != -1 || skeletalRangeEnd != -1) {
                dos.writeByte(15)
                dos.writeShort(skeletalRangeBegin)
                dos.writeShort(skeletalRangeEnd)
            }
        } else {
            field2310?.let { fieldMap ->
                dos.writeByte(15)
                dos.writeShort(fieldMap.size)
                fieldMap.forEach { (key, soundList) ->
                    dos.writeShort(key)
                    soundList.forEach { sound ->
                        encodeFrameSound(sound as Sound, dos, false)
                    }
                }
            }
        }

        if (!revisionIsOrAfter(226)) {
            if (skeletalRangeBegin != -1 || skeletalRangeEnd != -1) {
                dos.writeByte(16)
                dos.writeShort(skeletalRangeBegin)
                dos.writeShort(skeletalRangeEnd)
            }
        }

        unknown?.let { flags ->
            dos.writeByte(17)
            dos.writeByte(flags.count { it })
            flags.forEachIndexed { index, state ->
                if (state) dos.writeByte(index)
            }
        }
    }






    override fun getID(): Int {
        return id
    }
}
