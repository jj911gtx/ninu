package io.pc7.ninu.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.pc7.ninu.database.model.Device


@Dao
interface Dao {


    @Insert
    suspend fun insert(device: Device)

    @Query("SELECT * FROM Device LIMIT 1")
    suspend fun getDevice(): Device


}