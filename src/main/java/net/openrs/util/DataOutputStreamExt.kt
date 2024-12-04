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