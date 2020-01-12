package com.kotlinhva.gamebacklog52.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinhva.gamebacklog52.R
import com.kotlinhva.gamebacklog52.database.GameRepository
import com.kotlinhva.gamebacklog52.model.Game
import com.kotlinhva.gamebacklog52.ui.edit.AddActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    val SHOW_ADD_REQUEST_CODE = 100
    private val gameList = arrayListOf<Game>()
    private lateinit var gameRepository: GameRepository
    private val gameAdapter = GameAdapter(gameList)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gameRepository = GameRepository(this)

        initViews()
        initViewModel()
    }

    private fun initViews() {

        fab.setOnClickListener {
            startAddActivity()
        }

        rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvItems.adapter = gameAdapter
        rvItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        getGamesFromDatabase()
    }

    private fun startAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, SHOW_ADD_REQUEST_CODE)
    }

    private fun getGamesFromDatabase() {
    }
    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.games.observe(this, Observer {
                Game ->
            if (Game != null) {
                mainScope.launch {
                    val gameList = withContext(Dispatchers.IO) {
                        gameRepository.getGames()
                    }
                    this@MainActivity.gameList.clear()
                    this@MainActivity.gameList.addAll(gameList)
                    this@MainActivity.gameAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}
