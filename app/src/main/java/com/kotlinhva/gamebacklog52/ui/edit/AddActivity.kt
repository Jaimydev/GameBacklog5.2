package com.kotlinhva.gamebacklog52.ui.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlinhva.gamebacklog52.R
import com.kotlinhva.gamebacklog52.model.Game
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.content_edit.*
import java.lang.Integer.parseInt
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException
import java.util.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add game"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    private fun initViews() {
        fab.setOnClickListener {
            saveGame()
        }
    }

    private fun saveGame() {
        if (isValidGame()) {
            try {
                val localDate = getLocalDate()
                val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                val game = Game(
                    etTitle.text.toString(), etPlatform.text.toString(), date
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_GAME, game)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } catch (e: DateTimeParseException) {
                Toast.makeText(this, getString(R.string.date_invalid_error), Toast.LENGTH_SHORT)
                    .show()
                return
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getLocalDate(): LocalDate {
        val day = parseInt(etDay.text.toString());
        val month = parseInt(etMonth.text.toString());
        val year = parseInt(etYear.text.toString());

        return LocalDate.of(year, month, day)

    }

    private fun isValidGame(): Boolean {
        return when {
            etTitle.text.toString().isBlank() -> {
                Toast.makeText(this, getString(R.string.title_invalid_error), Toast.LENGTH_SHORT)
                    .show()
                false
            }
            etPlatform.text.toString().isBlank() -> {
                Toast.makeText(
                    this,
                    getString(R.string.platform_invalid_error),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            etDay.text.toString().isBlank() || etMonth.text.toString().isBlank() || etYear.text.toString().isBlank() -> {
                Toast.makeText(this, getString(R.string.date_invalid_error), Toast.LENGTH_SHORT)
                    .show()
                false
            }
            else -> true
        }
    }

    companion object {
        const val EXTRA_GAME = "EXTRA_GAME"
    }

}
