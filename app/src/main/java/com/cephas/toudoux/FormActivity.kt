package com.cephas.toudoux

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val etTitle: EditText = findViewById(R.id.etTitle)
        val btnSave: Button = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()

            addTitleToList(title)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addTitleToList(title: String) {
        val sharedPreferences = getSharedPreferences("MyTitles", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val titlesSet = HashSet(sharedPreferences.getStringSet("titles", HashSet()))

        titlesSet.add(title)

        editor.putStringSet("titles", titlesSet)
        editor.apply()
    }
}