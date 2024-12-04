package net.openrs.cache.util

import net.openrs.util.medium
import net.openrs.util.revisionIsOrAfter
import net.openrs.util.uByte
import net.openrs.util.uShort
import java.io.DataOutputStream
import java.nio.ByteBuffer

data class Sound(
    var id: Int,
    var loops: Int,
    var location: Int,
    var retain: Int
) {
    companion object {
        fun readFrameSound(buffer: ByteBuffer, pre226: Boolean): Sound? {
            // Check if the buffer has enough data
            if (buffer.remaining() < 6) {
                return null
            }

            val id = buffer.uShort // Equivalent to `readUnsignedShort()`
            val var2 = if (revisionIsOrAfter(226)) buffer.uByte else -1 // Equivalent to `readUnsignedByte()`
            val loops = buffer.uByte
            val location = buffer.uByte
            val retain = buffer.uByte

            // Validate the sound data
            return if (id >= 1 && loops >= 1 && location >= 0 && retain >= 0) {
                Sound(id, loops, location, retain)
            } else {
                null
            }
        }

        fun encodeFrameSound(sound: Sound, dos: DataOutputStream, pre220: Boolean) {
            if (pre220) {
                // Combine location, id, and loops into a 24-bit integer
                val payload = (sound.location and 15) or ((sound.id shl 8) or (sound.loops shl 4 and 7))
                dos.writeByte((payload shr 16) and 0xFF) // Upper 8 bits
                dos.writeByte((payload shr 8) and 0xFF)  // Middle 8 bits
                dos.writeByte(payload and 0xFF)         // Lower 8 bits
            } else {
                // Write fields individually for revisions >= 220
                dos.writeShort(sound.id)        // Sound ID (16-bit)
                if (revisionIsOrAfter(226)) {
                    dos.writeByte(0)            // Reserved or additional field for 226+
                }
                dos.writeByte(sound.loops)      // Number of loops (8-bit)
                dos.writeByte(sound.location)   // Location (8-bit)
                dos.writeByte(sound.retain)     // Retain value (8-bit)
            }
        }
    }


    override fun toString(): String {
        return "Sound(id=$id, loops=$loops, location=$location, retain=$retain)"
    }
}
