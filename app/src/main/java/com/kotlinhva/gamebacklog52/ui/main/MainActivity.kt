package com.kotlinhva.gamebacklog52.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlinhva.gamebacklog52.R
import com.kotlinhva.gamebacklog52.database.GameRepository
import com.kotlinhva.gamebacklog52.model.Game
import com.kotlinhva.gamebacklog52.ui.edit.AddActivity
import com.kotlinhva.gamebacklog52.ui.edit.AddActivity.Companion.EXTRA_GAME
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    val SHOW_ADD_REQUEST_CODE = 100
    private val gameList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gameList)

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    private fun initViews() {

        fab.setOnClickListener {
            startAddActivity()
        }

        rvItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvItems.adapter = gameAdapter

        gameAdapter.notifyDataSetChanged()

        makeItemTouchHelper().attachToRecyclerView(rvItems)

    }

    private fun startAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, SHOW_ADD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SHOW_ADD_REQUEST_CODE -> {
                    val gameBacklogItem =
                        data!!.getParcelableExtra<Game>(EXTRA_GAME)
                    mainActivityViewModel.insertGameBacklogItem(gameBacklogItem)
                }
            }
        }
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.games.observe(this, Observer { game ->
            if (game != null) {
                this@MainActivity.gameList.clear()
                this@MainActivity.gameList.addAll(game)
                gameAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnDeleteAllItems -> {
                mainActivityViewModel.deleteAllGameBacklogItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun makeItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val delGame = gameList.removeAt(position)
                mainActivityViewModel.deleteGameBacklogItem(delGame)
            }
        }
        return ItemTouchHelper(callback)
    }
}
