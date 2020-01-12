package com.kotlinhva.gamebacklog52.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kotlinhva.gamebacklog52.model.Game

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM Game")
    fun getGames(): LiveData<List<Game>?>

    @Update
    suspend fun updateGame(game: Game)

}