package io.pc7.ninu.data.ble.mapper

val Boolean.int
        get() = if (this) 1 else 0