package io.pc7.ninu.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.pc7.ninu.database.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


@Database(entities = [
    Device::class,

], version = 1)
@ConstructedBy(PerfumeDatabaseConstructor::class)
abstract class PerfumeDatabase : RoomDatabase() {
    abstract fun getDao(): Dao
}


@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PerfumeDatabaseConstructor : RoomDatabaseConstructor<PerfumeDatabase> {
    override fun initialize(): PerfumeDatabase
}


fun getRoomDatabase(
    builder: RoomDatabase.Builder<PerfumeDatabase>
): PerfumeDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}