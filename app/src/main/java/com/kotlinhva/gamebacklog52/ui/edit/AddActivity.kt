package com.kotlinhva.gamebacklog52.ui.edit

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlinhva.gamebacklog52.R
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var addViewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add game"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener {

            addViewModel.Game.value?.apply {
             //   title = etTitle.text.toString()
            //    lastUpdated = Date()
            //    text = etGame.text.toString()
            }

            addViewModel.updateGame()
        }
    }

    private fun initViewModel() {
        addViewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)
        addViewModel.Game.value = intent.extras?.getParcelable(EXTRA_Game)!!

        addViewModel.Game.observe(this, Observer { Game ->
            if (Game != null) {
           //     etTitle.setText(Game.title)
             //   etGame.setText(Game.text)
            }
        })

        addViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        addViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> { // Used to identify when the user has clicked the back button
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_Game = "EXTRA_Game"
    }

}
