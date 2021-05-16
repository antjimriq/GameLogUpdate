package com.aeg7.gamelog.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.aeg7.gamelog.DatePicker
import com.aeg7.gamelog.Game
import com.aeg7.gamelog.R
import com.aeg7.gamelog.api.MainRepository
import com.aeg7.gamelog.database.getDatabase
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class GameDetailsActivity : AppCompatActivity() {
    companion object{
        const val KEY = "7e8c32c7ac3140cd94a143252b925b94"
        const val GAME_KEY = "game"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.background_purple))

        //Recibimos el intent que se envia desde GameListMainActivity con la función openGameListDetailActivity
        val game = intent.extras?.getParcelable<Game>(GAME_KEY)

        if(game?.logo?.isNotEmpty() == true){
            Glide.with(this).load(game?.logo).into(findViewById(R.id.logoDetails))
        }else{
            Glide.with(this).load("https://www.ferexpo.cl/images/contenido-no-disponible.jpg").into(
                findViewById(
                    R.id.logoDetails
                )
            )
        }
        findViewById<TextView>(R.id.game_title).text = game?.name



        val status = resources.getStringArray(R.array.Status)
        val spinnerStatus:Spinner = findViewById(R.id.game_status)
        if (spinnerStatus != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, status)
            spinnerStatus.adapter=adapter
        }
        val mark= resources.getStringArray(R.array.Mark)
        val spinnerMark:Spinner= findViewById(R.id.mark)
        if (spinnerMark !=null){
            val adapter= ArrayAdapter(this, android.R.layout.simple_spinner_item, mark)
            spinnerMark.adapter=adapter
        }
        val platforms= game?.platform
        val spinnerPlatform:Spinner=findViewById(R.id.platform)
        if (spinnerPlatform != null){
            val adapter= platforms?.let {
                ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
            }
            spinnerPlatform.adapter=adapter
        }

        val datapicker=findViewById<EditText>(R.id.edit_date)

        datapicker.setOnClickListener {
            showDatePickerDialog()
        }


        //Aquí pasaremos los valores del juego seleccionado para que se muestre cada dato en su correpondiente campo
        var index_plat=platforms?.indexOf(game?.preferences?.console.toString())

        if (index_plat != null) {
            findViewById<Spinner>(R.id.platform).setSelection(index_plat)
        }


        var index_mark= mark.indexOf(game?.preferences?.mark)
        if (index_mark != null) {
            findViewById<Spinner>(R.id.mark).setSelection(index_mark)
        }


        var index_status= status.indexOf(game?.preferences?.status)
        if (index_status != null) {
            findViewById<Spinner>(R.id.game_status).setSelection(index_status)
        }

        //Mostrarmos el comentario
        findViewById<EditText>(R.id.comments).setText(game?.preferences?.comments)

        //Si ownership es true el CheckBox aparecerá marcado
        findViewById<CheckBox>(R.id.ownership).isChecked = game?.preferences?.ownership == "true"
        //Repetimos lo mismo para el resto de checkbox
        findViewById<CheckBox>(R.id.physical_copy).isChecked = game?.preferences?.physicalCopy == "true"
        findViewById<CheckBox>(R.id.digital_copy).isChecked = game?.preferences?.digitalCopy == "true"
        findViewById<CheckBox>(R.id.collectors_edition).isChecked = game?.preferences?.collectors == "true"
        findViewById<CheckBox>(R.id.dlc).isChecked = game?.preferences?.extra == "true"

        //Y por ultimo mostramos la fecha en el campo date
        if(game?.preferences?.date != ""){
            findViewById<EditText>(R.id.edit_date).setText(game?.preferences?.date)
        }

/*        findViewById<Button>(R.id.save_button_menu).setOnClickListener{
            saveData(game)
        }*/
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val datapicker=findViewById<EditText>(R.id.edit_date)
        //En Android se empiezan a contar los meses desde cero, por ello sumamos uno para que coincida con nuestro calendario
        datapicker.setText("$day/${month+1}/$year")
    }

    private fun saveData(game: Game?) {
        //Es importante seleccionar el elemento del spinner con selectItem y convertirlo a String
        //Lo mismo con los CheckBox, hay que coger si han sido marcados con isChecked y convertirlo a String
        game?.preferences?.console = findViewById<Spinner>(R.id.platform).selectedItem.toString()
        game?.preferences?.mark = findViewById<Spinner>(R.id.mark).selectedItem.toString()
        game?.preferences?.status = findViewById<Spinner>(R.id.game_status).selectedItem.toString()
        game?.preferences?.comments = findViewById<EditText>(R.id.comments).text.toString()
        game?.preferences?.ownership = findViewById<CheckBox>(R.id.ownership).isChecked.toString()
        game?.preferences?.physicalCopy = findViewById<CheckBox>(R.id.physical_copy).isChecked.toString()
        game?.preferences?.digitalCopy = findViewById<CheckBox>(R.id.digital_copy).isChecked.toString()
        game?.preferences?.collectors = findViewById<CheckBox>(R.id.collectors_edition).isChecked.toString()
        game?.preferences?.extra = findViewById<CheckBox>(R.id.dlc).isChecked.toString()
        game?.preferences?.date = findViewById<EditText>(R.id.edit_date).text.toString()

        //Como se esta editando el juego, ponemos myGame a true
        if (game?.myGame == false){
            game?.myGame=true
        }


        val db = getDatabase(application)
        val repository= MainRepository(db, application)


        runBlocking {
            launch(Dispatchers.IO){
                if (game != null) {
                    repository.updateOneGame(game)
                }
            }
        }


        val intent= Intent(this, MyGamesListActivity::class.java)
        startActivity(intent)

    }

    fun CheckBoxClicked(view: View){
        if (view is CheckBox){
            val checked: Boolean = view.isChecked
            when (view.id){
                R.id.ownership -> {
                    if (checked) {
                        //TODO
                    } else {
                        //TODO
                    }
                }
                R.id.physical_copy -> {
                    if (checked) {
                        //TODO
                    } else {
                        //TODO
                    }
                }
                R.id.digital_copy -> {
                    if (checked) {
                        //TODO
                    } else {
                        //TODO
                    }
                }
                R.id.collectors_edition -> {
                    if (checked) {
                        //TODO
                    } else {
                        //TODO
                    }
                }
                R.id.dlc -> {
                    if (checked) {
                        //TODO
                    } else {
                        //TODO
                    }
                }
            }
        }
    }
    fun showDatePicked(view: View){
        DatePicker().show(supportFragmentManager, "datePicker")
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gamedetails_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.mygames_menu) {
            val intent= Intent(this, MyGamesListActivity::class.java)
            startActivity(intent)
        }
        if(itemId == R.id.home_menu){
            openMainActivity()
        }
        if (itemId == R.id.save_button_menu){
            val game = intent.extras?.getParcelable<Game>(GAME_KEY)
            saveData(game)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun openMainActivity(){
        val intent=Intent(this, GameListMainActivity::class.java)
        startActivity(intent)
    }

}