package com.aeg7.gamelog.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeg7.gamelog.api.GameAdapter
import com.aeg7.gamelog.Game
import com.aeg7.gamelog.MainViewModel
import com.aeg7.gamelog.MainViewModelFactory
import com.aeg7.gamelog.R
import com.aeg7.gamelog.api.MainRepository
import com.aeg7.gamelog.database.getDatabase
import com.aeg7.gamelog.databinding.ActivityGameListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GameListMainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var GameListMainActivity = mutableListOf<Game>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGameListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.myGamesRecycler.layoutManager=LinearLayoutManager(this)
        val adapter = GameAdapter(this)
        binding.myGamesRecycler.adapter = adapter
        adapter.setOnclickListener {
            openGameListDetailActivity(it)
        }
        viewModel= ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
        viewModel.gamesList.observe(this, Observer { myGamesList ->
            adapter.submitList(myGamesList)
            handelEmptyList(myGamesList, binding)
        })

        val db = getDatabase(application)
        val repository= MainRepository(db, application)

        runBlocking {
            launch(Dispatchers.IO) {
                adapter.submitList(repository.selectGames())
            }
        }
    }
    private fun handelEmptyList(
        myGamesList: MutableList<Game>,
        binding: ActivityGameListBinding
    ) {
        if (myGamesList.isEmpty()) {
            binding.emptyList.visibility = View.VISIBLE
        } else {
            binding.emptyList.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mygames_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.mygames_menu) {
            val intent= Intent(this, MyGamesListActivity::class.java)
            startActivity(intent)
        }
        if(itemId == R.id.home_menu){
            onResume()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openGameListDetailActivity (game: Game){
        val intent=Intent(this, GameDetailsActivity::class.java)
        intent.putExtra(GameDetailsActivity.GAME_KEY,game)
        startActivity(intent)
    }
}
