package net.openrs.util

import java.io.DataOutputStream


fun DataOutputStream.write24bitInt(content : Int) {
    writeByte(content shr 16)
    writeByte(content shr 8)
    writeByte(content)
}

fun DataOutputStream.writeString(content : String) {
    write(content.toByteArray())
    writeByte(10)
}
fun DataOutputStream.writeSmartByteOrShort(value: Int) {
    if (value in -64..63) {
        writeByte(value + 0x40)
    } else {
        writeShort(value + 0xc000)
    }
}
fun DataOutputStream.writeShort(value: Int) {
    writeByte((value shr 8) and 0xFF) // Write the high byte
    writeByte(value and 0xFF)         // Write the low byte
}


