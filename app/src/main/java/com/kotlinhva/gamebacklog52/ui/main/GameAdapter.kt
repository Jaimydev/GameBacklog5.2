package com.kotlinhva.gamebacklog52.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.kotlinhva.gamebacklog52.R
import com.kotlinhva.gamebacklog52.model.Game
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat


class GameAdapter(private val Games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = Games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(
        Games[position]
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvTitle.text = "${game.title}"
            itemView.tvPlatform.text = game.platform
            itemView.tvReleaseDate.text = DateFormat.getDateTimeInstance().format(game.releaseDate)
        }
    }
}