package io.pc7.ninu.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.pc7.ninu.database.PerfumeDatabase
import io.pc7.ninu.database.PerfumeDatabaseConstructor

class DatabaseBuilder(private val context: Context) {
    fun build(): PerfumeDatabase{
        return Room.databaseBuilder(
            context = context,
            klass = PerfumeDatabase::class.java,
            name = "Perfume"
        ).build()

    }
}