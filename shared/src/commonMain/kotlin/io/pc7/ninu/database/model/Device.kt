package io.pc7.ninu.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Device(
    val macAddress: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
