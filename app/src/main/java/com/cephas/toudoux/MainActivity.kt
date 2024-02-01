package com.cephas.toudoux

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGoToForm: Button = findViewById(R.id.btnGoToForm)
        btnGoToForm.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val titlesList = getTitlesList()

        val preferenceManager = PreferenceManager(this)
        preferenceManager.resetPreferencesIfDateChanged()
        val adapter = TitleAdapter(titlesList, preferenceManager)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnDeleteAll: Button = findViewById(R.id.btnDeleteAll)
        btnDeleteAll.setOnClickListener {
            removeAllTitles(recyclerView)
        }
    }

    private fun getTitlesList(): List<String> {
        val sharedPreferences = getSharedPreferences("MyTitles", Context.MODE_PRIVATE)

        val titlesSet = sharedPreferences.getStringSet("titles", HashSet())

        return titlesSet?.toList() ?: emptyList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeAllTitles(recyclerView: RecyclerView) {
        val sharedPreferences = getSharedPreferences("MyTitles", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("titles")
        editor.apply()

        val adapter = recyclerView.adapter as? TitleAdapter
        adapter?.notifyDataSetChanged()
    }
}