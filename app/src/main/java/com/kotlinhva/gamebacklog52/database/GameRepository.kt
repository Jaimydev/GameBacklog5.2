package com.kotlinhva.gamebacklog52.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.kotlinhva.gamebacklog52.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameBacklogRoomDatabase.getDatabase(context)
        gameDao = database!!.GameDao()
    }

    fun getGames(): LiveData<List<Game>> {
        return gameDao.getGames()
    }

    suspend fun updateGame(Game: Game) {
        gameDao.updateGame(Game)
    }

    suspend fun deleteGameBacklogItem(Game: Game) {
        gameDao.deleteGameBacklogItem(Game)
    }

    suspend fun deleteAllBacklogItems() {
        gameDao.deleteAllBacklogItems()
    }

    suspend fun insertGameBacklogItem(Game: Game) {
        gameDao.insertGame(Game)
    }
}