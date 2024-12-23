package io.pc7.ninu.data.ble.mapper

fun Int.toByteArray(): ByteArray{
        val byteValue: Byte = this.toByte()
        return byteArrayOf(byteValue)
    }