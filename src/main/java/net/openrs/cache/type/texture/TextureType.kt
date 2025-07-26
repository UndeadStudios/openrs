package net.openrs.cache.type.texture

import net.openrs.cache.type.Type
import java.io.DataOutputStream
import java.io.IOException
import java.nio.ByteBuffer

class TextureType (private val id: Int) : Type {
     var fileIds: IntArray = IntArray(0)
     var field2293: Boolean = false
     var field2301: IntArray = IntArray(0)
     var field2296: IntArray = IntArray(0)
      var field2295: IntArray = IntArray(0)
     var animationSpeed : Int = 0
     var animationDirection : Int = 0
      var averageRGB: Int = -1
    override fun decode(buffer: ByteBuffer) {
        averageRGB = buffer.getShort().toInt() and 0xFFFF;
        field2293 = buffer.get().toInt() and 0xFF == 1
        val count: Int = buffer.get().toInt() and 0xFF

        if (count in 1..4) {
            fileIds = IntArray(count)
            for (index in 0 until count) {
                fileIds[index] = buffer.getShort().toInt() and 0xFFFF;
            }
            if (count > 1) {
                field2301 = IntArray(count - 1)
                for (index in 0 until count - 1) {
                    field2301[index] = buffer.get().toInt() and 0xFF
                }
            }
            if (count > 1) {
                field2296 = IntArray(count - 1)
                for (index in 0 until count - 1) {
                    field2296[index] = buffer.get().toInt() and 0xFF
                }
            }
            field2295 = IntArray(count)
            for (index in 0 until count) {
                field2295[index] = buffer.getInt()
            }
            animationDirection = buffer.get().toInt() and 0xFF
            animationSpeed = buffer.get().toInt() and 0xFF
        } else {
            println("Texture: ${id} Out of range 1..4 [${count}]")
        }
    }
    @Throws(IOException::class)
    override fun encode(dos: DataOutputStream) {

        dos.writeByte(1)
        dos.writeShort(id)

        if(field2293) {
            dos.writeByte(2)
            dos.writeByte(if(field2293) 1 else 0)
        }

        if(!fileIds.contentEquals(IntArray(0))) {
            dos.writeByte(3)
            dos.writeByte(fileIds.size)
        }

        if (!fileIds.contentEquals(IntArray(0))) {
            dos.writeByte(4)
            repeat(fileIds.count()) {
                dos.writeShort(fileIds[it])
            }
        }

        if (!field2301.contentEquals(IntArray(0))) {
            dos.writeByte(5)
            repeat(field2301.count()) {
                dos.writeByte(field2301[it])
            }
        }

        if (!field2296.contentEquals(IntArray(0))) {
            dos.writeByte(6)
            repeat(field2296.count()) {
                dos.writeByte(field2296[it])
            }
        }

        if (!field2295.contentEquals(IntArray(0))) {
            dos.writeByte(7)
            repeat(field2295.count()) {
                dos.writeInt(field2295[it])
            }
        }

        if (animationSpeed != 0) {
            dos.writeByte(8)
            dos.writeShort(animationSpeed)
        }
        if (animationDirection != 0) {
            dos.writeByte(9)
            dos.writeShort(animationDirection)
        }
        if (averageRGB != -1) {
            dos.writeByte(10)
            dos.write24bitInt(averageRGB)
        }
        dos.writeByte(0)
    }
    fun DataOutputStream.write24bitInt(content : Int) {
        writeByte(content shr 16)
        writeByte(content shr 8)
        writeByte(content)
    }
    override fun getID(): Int {
        return id
    }
}
