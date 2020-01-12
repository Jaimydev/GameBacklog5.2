package com.kotlinhva.gamebacklog52.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinhva.gamebacklog52.database.GameRepository
import com.kotlinhva.gamebacklog52.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)

    val games = gameRepository.getGames()

    fun insertGameBacklogItem(game: Game) {
        CoroutineScope(Dispatchers.IO).launch {
            gameRepository.insertGameBacklogItem(game)
        }
    }

    fun deleteGameBacklogItem(game: Game) {
        CoroutineScope(Dispatchers.IO).launch {
            gameRepository.deleteGameBacklogItem(game)
        }
    }

    fun deleteAllGameBacklogItems() {
        CoroutineScope(Dispatchers.IO).launch {
            gameRepository.deleteAllBacklogItems()
        }
    }
}