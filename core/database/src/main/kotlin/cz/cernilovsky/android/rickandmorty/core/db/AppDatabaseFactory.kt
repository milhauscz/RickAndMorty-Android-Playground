package cz.cernilovsky.android.rickandmorty.core.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

data class DatabaseConfig(
    val allowDestructiveMigration: Boolean,
)

fun getAppDatabase(
    context: Context,
    allowDestructiveMigration: Boolean,
): AppDatabase =
    Room
        .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .apply {
            // Only wipe the DB on a schema change in debug builds; release builds must migrate.
            if (allowDestructiveMigration) {
                fallbackToDestructiveMigration(dropAllTables = true)
            }
        }.build()
