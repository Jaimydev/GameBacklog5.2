package com.kotlinhva.gamebacklog52.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.kotlinhva.gamebacklog52.R
import com.kotlinhva.gamebacklog52.model.Game
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat


class GameAdapter(private val Games: LiveData<List<Game>>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        )
    }

    private lateinit var mainActivityViewModel: MainActivityViewModel


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

    override fun getItemCount(): Int = Games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(Games[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvTitle.text = "${game.title}"
            itemView.tvReleaseDate.text = DateFormat.getDateTimeInstance().format(game.platform)
        }
    }
}