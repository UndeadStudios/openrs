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
package net.openrs.cache.type.hitmarks

import net.openrs.cache.type.Type
import net.openrs.util.ByteBufferUtils
import java.io.DataOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import net.openrs.util.write24bitInt
import net.openrs.util.writeString

/**
 *
 * Created by Kyle Fricilone on Jun 1, 2017.
 */
class HitMarkType(private val id: Int) : Type {
    private var anInt2125 = -1
    private var anInt2126 = 16777215
    private var anInt2127 = -1
    private var anInt2133 = -1

    /**
     * @return the spriteId
     */
    var spriteId: Int = -1
        private set
    private var anInt2131 = -1
    private var anInt2123 = 0
    private var aString2134: String? = ""
    private var anInt2139 = 70
    private var anInt2122 = 0
    private var anInt2119 = -1
    private var anInt2135 = -1
    private var anInt2136 = 0
    private var anInt2138 = -1
    private var anInt2132 = -1
    private var anIntArray2137: IntArray? = null

    /*
      * (non-Javadoc)
      * 
      * @see net.openrs.cache.type.Type#decode(java.nio.ByteBuffer)
      */
    override fun decode(buffer: ByteBuffer) {
        while (true) {
            val opcode = buffer.get().toInt() and 0xFF
            if (opcode == 0) break

            if (opcode == 1) {
                anInt2125 = ByteBufferUtils.getSmartInt(buffer)
            } else if (opcode == 2) {
                anInt2126 = ByteBufferUtils.get24Int(buffer)
            } else if (opcode == 3) {
                anInt2127 = ByteBufferUtils.getSmartInt(buffer)
            } else if (opcode == 4) {
                anInt2133 = ByteBufferUtils.getSmartInt(buffer)
            } else if (opcode == 5) {
                spriteId = ByteBufferUtils.getSmartInt(buffer)
            } else if (opcode == 6) {
                anInt2131 = ByteBufferUtils.getSmartInt(buffer)
            } else if (opcode == 7) {
                anInt2123 = buffer.getShort().toInt()
            } else if (opcode == 8) {
                aString2134 = ByteBufferUtils.getPrefixedString(buffer)
            } else if (opcode == 9) {
                anInt2139 = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode == 10) {
                anInt2122 = buffer.getShort().toInt()
            } else if (opcode == 11) {
                anInt2119 = 0
            } else if (opcode == 12) {
                anInt2135 = buffer.get().toInt() and 0xFF
            } else if (opcode == 13) {
                anInt2136 = buffer.getShort().toInt()
            } else if (opcode == 14) {
                anInt2119 = buffer.getShort().toInt() and 0xFFFF
            } else if (opcode == 17 || opcode == 18) {
                anInt2138 = buffer.getShort().toInt() and 0xFFFF
                if (anInt2138 == '\uffff'.code) {
                    anInt2138 = -1
                }

                anInt2132 = buffer.getShort().toInt() and 0xFFFF
                if (anInt2132 == '\uffff'.code) {
                    anInt2132 = -1
                }
                var var32 = -1 // L: 83
                if (opcode == 18) { // L: 84
                    var32 = buffer.getShort().toInt() and 0xFFFF // L: 85
                    if (var32 == '\uffff'.code) { // L: 86
                        var32 = -1
                    }
                }
                val length = buffer.get().toInt() and 0xFFFF
                anIntArray2137 = IntArray(length + 2)

                for (var3 in 0..length) {
                    anIntArray2137!![var3] = buffer.getShort().toInt() and 0xFFFF
                    if (anIntArray2137!![var3] == '\uffff'.code) {
                        anIntArray2137!![var3] = -1
                    }
                }

                anIntArray2137!![length + 1] = var32
            }
        }
    }

    @Override
    override fun encode(dos: DataOutputStream) {
        if (anInt2125 != 0) {
            dos.writeByte(1)
            dos.write24bitInt(anInt2125)
        }
        if (anInt2126 != 0) {
            dos.writeByte(2)
            dos.write24bitInt(anInt2126)
        }
        if (anInt2127 != 0) {
            dos.writeByte(3)
            dos.write24bitInt(anInt2127)
        }
        if (anInt2133 != 0) {
            dos.writeByte(4)
            dos.write24bitInt(anInt2133)
        }
        if (spriteId != 0) {
            dos.writeByte(5)
            dos.write24bitInt(spriteId)
        }
        if (anInt2131 != 0) {
            dos.writeByte(6)
            dos.write24bitInt(anInt2131)
        }
        if (anInt2123 != 0) {
            dos.writeByte(7)
            dos.writeShort(anInt2123)
        }
        if (aString2134 != null) {
            dos.writeByte(8)
            dos.writeString(aString2134!!)
        }
        if (anInt2139 != 0) {
            dos.writeByte(9)
            dos.writeShort(anInt2139)
        }
        if (anInt2122 != 0) {
            dos.writeByte(10)
            dos.writeShort(anInt2122)
        }
        if (anInt2119 != 0) {
            dos.writeByte(11)
        }
        if (anInt2135 != 0) {
            dos.writeByte(12)
            dos.writeByte(anInt2135)
        }
        if (anInt2136 != 0) {
            dos.writeByte(13)
            dos.writeShort(anInt2136)
        }
        if (anInt2119 != 0) {
            dos.writeByte(14)
            dos.writeShort(anInt2119)
        }
        if (anInt2138 != -1 || anInt2132 != -1 || anIntArray2137 != null) {
            dos.writeByte(17)
            dos.writeShort(if (anInt2138 == -1) 0xFFFF else anInt2138)
            dos.writeShort(if (anInt2132 == -1) 0xFFFF else anInt2132)

            if (anIntArray2137 != null) {
                dos.writeByte(anIntArray2137!!.size - 2)
                for (i in 0 until anIntArray2137!!.size - 1) {
                    dos.writeShort(if (anIntArray2137!![i] == -1) 0xFFFF else anIntArray2137!![i])
                }
                dos.writeShort(anIntArray2137!![anIntArray2137!!.size - 1])
            }
        }
        dos.writeByte(0) // End marker
    }


    /*
	 * (non-Javadoc)
	 * 
	 * @see net.openrs.cache.type.Type#getID()
	 */
    override fun getID(): Int {
        return id
    }
}
