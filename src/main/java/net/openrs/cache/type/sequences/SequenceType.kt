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
import net.openrs.util.ByteBufferUtils
import java.io.DataOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

/**
 * @author Kyle Friz
 * @since Oct 18, 2015
 */
class SequenceType<class202>(private val id: Int) : Type {
    var anIntArray2118: IntArray? = null
    var priority = -1
    var frameIDs: IntArray? = null
    var frameLengths: IntArray? = null
    var frameSounds: IntArray? = null
    var frameStep = -1
    var interleaveLeave: IntArray? = null
    var isStretches = false
    var forcedPriority = 5
    var leftHandItem = -1
    var maxLoops = 99
    var var8: Boolean = false
    var var9: Boolean = false
    var field2250: Boolean = false
    var var11: Int = 0
    var var17: Int = 0
    var var18: Int = 0
    var var19: Int = 0
    var rightHandItem = -1
    var replayMode = 2
    var precedenceAnimating = -1
    private var unknown: BooleanArray? = null
    private var field2174: MutableMap<Int?, Int?> = HashMap()
    private var skeletalRangeBegin = 0
    private var skeletalRangeEnd = 0
    var skeletalId = -1
    override fun decode(buffer: ByteBuffer) {
        while (true) {
            val opcode = buffer.get().toInt() and 0xFF
            if (opcode == 0) {
                break
            } else if (opcode == 1) {
                val count = buffer.getShort().toInt() and 0xFFFF
                frameLengths = IntArray(count)
                frameIDs = IntArray(count)
                for (i in 0 until count) {
                    frameLengths!![i] = buffer.getShort().toInt() and 0xFFFF
                }
                for (i in 0 until count) {
                    frameIDs!![i] = buffer.getShort().toInt() and 0xFFFF
                }
                for (i in 0 until count) {
                    frameIDs!![i] += buffer.getShort().toInt() and 0xFFFF shl 16
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
                anIntArray2118 = IntArray(count)
                for (i in 0 until count) {
                    anIntArray2118!![i] = buffer.getShort().toInt() and 0xFFFF
                }
                for (i in 0 until count) {
                    anIntArray2118!![i] += buffer.getShort().toInt() and 0xFFFF shl 16
                }
            } else if (opcode == 13) {
               val var3 = buffer.get().toInt() and 0xFF
                this.frameSounds = IntArray(var3)

                for (i in 0 until var3) {
                    var var13: IntArray
                    var var14: IntArray
                    val function = {
                        var14 = this.frameSounds!!
                        if (buffer != null) {
                            var8 = false
                            var9 = false
                            val var10 = false
                            var11 = 0
                            if (!field2250) {
                                val var12: Int =  ByteBufferUtils.get24Int(buffer)
                                var19 = var12 and 15
                                var17 = var12 shr 8
                                var18 = var12 shr 4 and 7
                            } else {
                                var17 = buffer.getShort().toInt() and 0xFFFF
                                var18 = buffer.get().toInt() and 0xFF
                                var19 = buffer.get().toInt() and 0xFF
                                var11 = buffer.get().toInt() and 0xFF
                            }

                        }
                    }
                }
            } else if (opcode == 14) {
                skeletalId = buffer.getInt()
            } else if (opcode == 15) {
                val var3 = buffer.get().toInt() and 0xFF
                this.frameSounds = IntArray(var3)

                for (i in 0 until var3) {
                    var var13: IntArray
                    var var14: IntArray
                    val function = {
                        var14 = this.frameSounds!!
                        if (buffer != null) {
                            var8 = false
                            var9 = false
                            val var10 = false
                            var11 = 0
                            if (!field2250) {
                                val var12: Int =  ByteBufferUtils.get24Int(buffer)
                                var19 = var12 and 15
                                var17 = var12 shr 8
                                var18 = var12 shr 4 and 7
                            } else {
                                var17 = buffer.getShort().toInt() and 0xFFFF
                                var18 = buffer.get().toInt() and 0xFF
                                var19 = buffer.get().toInt() and 0xFF
                                var11 = buffer.get().toInt() and 0xFF
                            }

                        }
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
            for (i in frameLengths!!.indices) {
                dos.writeShort(frameLengths!![i])
            }
            for (i in frameIDs!!.indices) {
                dos.writeShort(frameIDs!![i])
            }
            for (i in frameIDs!!.indices) {
                dos.writeShort(frameIDs!![i] shr 16)
            }
        }
        if (frameStep != -1) {
            dos.writeByte(2)
            dos.writeShort(frameStep)
        }
        if (interleaveLeave != null) {
            dos.writeByte(3)
            dos.writeByte(interleaveLeave!!.size - 1)
            for (i in interleaveLeave!!.indices) {
                dos.writeByte(interleaveLeave!![i])
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
        if (anIntArray2118 != null && anIntArray2118!!.size > 0) {
            dos.writeByte(12)
            dos.writeByte(anIntArray2118!!.size)
            for (i in anIntArray2118!!.indices) {
                dos.writeShort(anIntArray2118!![i])
            }
            for (i in anIntArray2118!!.indices) {
                dos.writeShort(anIntArray2118!![i] shr 16)
            }
        }
        if (frameSounds != null && frameSounds!!.size > 0) {
            dos.writeByte(13)
            dos.writeByte(frameSounds!!.size)
            for (i in frameSounds!!.indices) {
                dos.writeByte(frameSounds!![i] shr 16)
                dos.writeByte(frameSounds!![i] shr 8)
                dos.writeByte(frameSounds!![i])
            }
        }
        if (skeletalId != -1) {
            dos.writeByte(14)
            dos.writeInt(skeletalId)
        }
        if (!field2174.entries.isEmpty()) {
            dos.writeByte(15)
            dos.writeShort(field2174.size)
            for ((key, value) in field2174) {
                dos.writeShort(key!!)
                dos.writeByte(value!! shr 16)
                dos.writeByte(value shr 8)
                dos.writeByte(value)
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
