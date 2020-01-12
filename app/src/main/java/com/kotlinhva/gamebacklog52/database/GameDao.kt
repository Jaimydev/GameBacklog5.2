package com.kotlinhva.gamebacklog52.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kotlinhva.gamebacklog52.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM game_backlog_table ORDER BY release_date")
    fun getGames(): LiveData<List<Game>>

    @Update
    suspend fun updateGame(game: Game)

    @Delete
    suspend fun deleteGameBacklogItem(gameBacklog: Game)

    @Query("DELETE FROM game_backlog_table")
    suspend fun deleteAllBacklogItems()
}