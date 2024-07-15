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
            } else if (opcode == 1) {
                val count = buffer.getShort().toInt() and 0xFFFF
                frameLengths = IntArray(count)
                (0 until count).forEach {
                    frameLengths!![it] = buffer.getShort().toInt() and 0xFFFF
                }
                frameIDs = IntArray(count)
                (0 until count).forEach {
                    frameIDs!![it] = buffer.getShort().toInt() and 0xFFFF
                }
                (0 until count).forEach {
                    frameIDs!![it] += buffer.getShort().toInt() and 0xFFFF shl 16
                }
            } else if (opcode == 2) {
                frameStep = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode == 3) {
                val count = buffer.get().toInt() and 0xFF
                interleaveLeave = IntArray(count + 1)
                for (index in 0 until count) {
                    interleaveLeave!![index] = buffer.get().toInt() and 0xFF
                }
                interleaveLeave!![count] = 9999999
            } else if (opcode == 4) {
                isStretches = true
            } else if (opcode == 5) {
                forcedPriority = buffer.get().toInt() and 0xFF
            } else if (opcode == 6) {
                leftHandItem = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode == 7) {
                rightHandItem = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode == 8) {
                maxLoops = buffer.get().toInt() and 0xFF
            } else if (opcode == 9) {
                precedenceAnimating = buffer.get().toInt() and 0xFF
            } else if (opcode == 10) {
                priority = buffer.get().toInt() and 0xFF
            } else if (opcode == 11) {
                replayMode = buffer.get().toInt() and 0xFF
            } else if (opcode == 12) {
                val count = buffer.get().toInt() and 0xFF
                chatFrameIds = IntArray(count)
                (0 until count).forEach {
                    chatFrameIds!![it] = buffer.getShort().toInt() and 0xFFFF
                }
                (0 until count).forEach {
                    chatFrameIds!![it] += buffer.getShort().toInt() and 0xFFFF shl 16
                }
            } else if (opcode == 13) {
                val length: Int = buffer.get().toInt() and 0xFF
                frameSounds = arrayOfNulls(length)

                for (var4 in 0 until length) {
                    frameSounds[var4] = Sound.readFrameSound(buffer, false)
                }
            } else if (opcode == 14) {
                skeletalId = buffer.getInt()
            } else if (opcode == 15) {
                val size: Int = buffer.getShort().toInt() and 0xFFFF
                field2174 = emptyMap<Int, Sound>().toMutableMap()
                for (index in 0 until size) {
                    val frame: Int = buffer.getShort().toInt() and 0xFFFF
                    val sound = Sound.readFrameSound(buffer, false)
                    if (sound != null) {
                        field2174[frame] = sound
                    }
                }
            } else if (opcode == 16) {
                skeletalRangeBegin = buffer.getShort().toInt() and 0xFFFF
                skeletalRangeEnd = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode == 17) {
                unknown = BooleanArray(256)
                for (var3 in unknown!!.indices) {
                    unknown!![var3] = false
                }
                val var3 = buffer.get().toInt() and 0xFF
                for (var4 in 0 until var3) {
                    unknown!![buffer.get().toInt() and 0xFF] = true
                }
            }
        }
    }

    @Throws(IOException::class)
    override fun encode(dos: DataOutputStream) {
        if (frameLengths != null && frameIDs != null) {
            dos.writeByte(1)
            dos.writeShort(frameLengths!!.size)
            for (index in 0 until frameLengths!!.size) {
                dos.writeShort(frameLengths!![index])
            }
            for (index in 0 until frameIDs!!.size) {
                dos.writeShort(frameIDs!![index])
            }
            for (index in 0 until frameIDs!!.size) {
                dos.writeShort(frameIDs!![index] shr 16)
            }
        }
        if (frameStep != -1) {
            dos.writeByte(2)
            dos.writeShort(frameStep)
        }
        if (interleaveLeave != null) {
            dos.writeByte(3)
            dos.writeByte(interleaveLeave!!.size - 1)
            for (index in 0 until interleaveLeave!!.size - 1) {
                dos.writeByte(interleaveLeave!![index])
            }
        }
        if (isStretches) {
            dos.writeByte(4)
        }
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

        if (chatFrameIds != null && chatFrameIds!!.isNotEmpty()) {
            dos.writeByte(12)
            dos.writeByte(chatFrameIds!!.size)
            for (i in 0 until chatFrameIds!!.size) {
                dos.writeShort(chatFrameIds!!.get(i))
            }
            for (i in 0 until chatFrameIds!!.size) {
                dos.writeShort(chatFrameIds!!.get(i) shr 16)
            }
        }
        if (frameSounds.isNotEmpty()) {
            dos.writeByte(13)
            dos.writeByte(frameSounds.size)
            if (revisionIsOrBefore(119)) {
                frameSounds.filterNotNull().forEach {
                    val payload: Int = (it.location and 15) or ((it.id shl 8) or (it.loops shl 4 and 7))
                    dos.write24bitInt(payload)
                }
            } else {
                dos.writeByte(frameSounds.size)
                frameSounds.filterNotNull().forEach {
                    dos.writeShort(it.id)
                    dos.writeByte(it.loops)
                    dos.writeByte(it.location)
                    dos.writeByte(it.retain)
                }
            }
        }
        if (skeletalId != -1) {
            dos.writeByte(14)
            dos.writeInt(skeletalId)
        }
        if (field2174.isNotEmpty()) {
            dos.writeByte(15)
            dos.writeShort(field2174.size)
            if (revisionIsOrBefore(119)) {
                field2174.forEach {
                    dos.writeShort(it.key)
                    val payload: Int = (it.value.location and 15) or (it.value.id shl 8) or (it.value.loops shl 4 and 7)
                    dos.write24bitInt(payload)
                }
            } else {
                field2174.forEach {
                    dos.writeShort(it.key)
                    dos.writeByte(it.value.id)
                    dos.writeByte(it.value.location)
                    dos.writeByte(it.value.retain)
                }
            }
        }
        if (skeletalRangeBegin != -1 || skeletalRangeEnd != -1) {
            dos.writeByte(16)
            dos.writeShort(skeletalRangeBegin)
            dos.writeShort(skeletalRangeEnd)
        }
        if (unknown != null) {
            dos.writeByte(17)

            dos.writeByte(unknown!!.filter { it }.size)
            unknown!!.forEachIndexed { index, state ->
                if(state) {
                    dos.writeByte(index)
                }
            }
        }
    }

    override fun getID(): Int {
        return id
    }
}
