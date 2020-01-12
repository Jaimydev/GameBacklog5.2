package com.kotlinhva.gamebacklog52.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kotlinhva.gamebacklog52.database.GameRepository
import com.kotlinhva.gamebacklog52.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val GameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val Game = MutableLiveData<Game?>()
    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean>()

    fun updateGame() {
        if (isGameValid()) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    GameRepository.updateGame(Game.value!!)
                }
                success.value = true
            }
        }
    }

    private fun isGameValid(): Boolean {
        return when {
            Game.value == null -> {
                error.value = "Game must not be null"
                false
            }
            Game.value!!.title.isBlank() -> {
                error.value = "Title must not be empty"
                false
            }
            else -> true
        }
    }

}