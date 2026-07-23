package cz.cernilovsky.android.rickandmorty.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.cernilovsky.android.rickandmorty.characters.data.CharactersRoomDataSource
import cz.cernilovsky.android.rickandmorty.characters.data.local.CharacterConverters
import cz.cernilovsky.android.rickandmorty.characters.data.local.CharacterEntity
import cz.cernilovsky.android.rickandmorty.characters.data.local.CharacterRemoteKeyEntity
import cz.cernilovsky.android.rickandmorty.characters.data.local.CharactersMetadataEntity
import cz.cernilovsky.android.rickandmorty.core.db.AppDatabase.Companion.DB_VERSION
import cz.cernilovsky.android.rickandmorty.episode.data.EpisodeRoomDataSource
import cz.cernilovsky.android.rickandmorty.episode.data.local.EpisodeEntity
import cz.cernilovsky.android.rickandmorty.location.data.LocationRoomDataSource
import cz.cernilovsky.android.rickandmorty.location.data.local.LocationEntity

@TypeConverters(CharacterConverters::class)
@Database(
    entities = [
        CharacterEntity::class,
        CharacterRemoteKeyEntity::class,
        CharactersMetadataEntity::class,
        LocationEntity::class,
        EpisodeEntity::class,
    ],
    version = DB_VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersRoomDataSource

    abstract fun locationDao(): LocationRoomDataSource

    abstract fun episodeDao(): EpisodeRoomDataSource

    companion object {
        const val DB_VERSION = 4
        const val DB_NAME = "app_database"
    }
}
