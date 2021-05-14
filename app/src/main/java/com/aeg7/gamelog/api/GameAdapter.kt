package com.aeg7.gamelog.api

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aeg7.gamelog.Game
import com.aeg7.gamelog.GlideApp
import com.aeg7.gamelog.R
import com.bumptech.glide.Glide

class GameAdapter(val context: Context):ListAdapter<Game, GameAdapter.ViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem==newItem
        }
    }
    private lateinit var onItemClickListener: (game:Game) -> Unit
    fun setOnclickListener (onItemClickListener: (game:Game)-> Unit){
        this.onItemClickListener=onItemClickListener
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_game_list_detail, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val game = getItem(position)
            holder.bind(game)
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val logoImage = view.findViewById<ImageView>(R.id.game_logo)
        private val nameText = view.findViewById<TextView>(R.id.game_name)
        private val plattformText = view.findViewById<TextView>(R.id.game_plattform)
        private val arrow=view.findViewById<ImageView>(R.id.arrow)
        fun bind(game: Game) {
            nameText.text = game.name
            plattformText.text = game.platform.joinToString("/")

            if(game?.logo?.isNotEmpty() == true){
                Glide.with(context).load(game.logo).into(logoImage)
            }else{
                Glide.with(context).load("https://www.ferexpo.cl/images/contenido-no-disponible.jpg").into(logoImage)
            }


            arrow.setOnClickListener {
                onItemClickListener(game)
            }
        }

    }
        }
