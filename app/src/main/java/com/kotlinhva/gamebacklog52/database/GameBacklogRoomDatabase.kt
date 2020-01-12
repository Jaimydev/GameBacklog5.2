package com.kotlinhva.gamebacklog52.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kotlinhva.gamebacklog52.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameBacklogRoomDatabase : RoomDatabase() {

    abstract fun GameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAMEBACKLOG_DATABASE"

        @Volatile
        private var INSTANCE: GameBacklogRoomDatabase? = null

        fun getDatabase(context: Context): GameBacklogRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(GameBacklogRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GameBacklogRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    INSTANCE?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.GameDao().insertGame(Game("Diablo 3", "PS4, PC", Date() ))
                                        }
                                    }
                                }
                            })
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}