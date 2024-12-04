package net.openrs.util

import java.nio.ByteBuffer

val ByteBuffer.uShort: Int get() = this.short.toInt() and 0xffff
val ByteBuffer.uByte: Int get() = this.get().toInt() and 0xff
val ByteBuffer.byte: Byte get() = this.get()
val ByteBuffer.rsString: String get() = getString(this)
val ByteBuffer.medium: Int get() = this.get().toInt() and 0xff shl 16 or (this.get().toInt() and 0xff shl 8) or (this.get().toInt() and 0xff)

val ByteBuffer.shortSmart : Int get() {
    val peek = uByte
    return if (peek < 128) peek - 64 else (peek shl 8 or uByte) - 49152
}

fun ByteBuffer.readByteArray(length: Int): ByteArray {
    val array = ByteArray(length)
    get(array)
    return array
}

fun ByteBuffer.readParams(): MutableMap<Int, String> {
    val params : MutableMap<Int,String> = mutableMapOf()
    (0 until uByte).forEach { _ ->
        val string: Boolean = (uByte) == 1
        val key: Int = medium
        val value: Any = if (string) { rsString } else { int }
        params[key] = value.toString()
    }
    return params
}
/**
 * What Revision the user wants to dump.
 */
var revision : Int = -1
fun revisionID() = 227

fun revisionIsOrAfter(rev : Int) = rev <= revisionID()
fun revisionIsOrBefore(rev : Int) = rev >= revisionID()

fun getString(buffer: ByteBuffer): String {
    val builder = StringBuilder()
    var b: Int
    while ((buffer.uByte).also { b = it } != 0) {
        builder.append(b.toChar())
    }
    return builder.toString()
}