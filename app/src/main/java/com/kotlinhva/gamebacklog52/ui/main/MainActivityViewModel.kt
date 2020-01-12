package com.kotlinhva.gamebacklog52.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinhva.gamebacklog52.database.GameRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)

    val games = gameRepository.getGames()

}