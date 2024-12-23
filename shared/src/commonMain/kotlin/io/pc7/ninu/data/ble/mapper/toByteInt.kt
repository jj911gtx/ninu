package io.pc7.ninu.data.ble.mapper

fun ByteArray.toByteInt(): Int{
        return this[0].toInt()
    }