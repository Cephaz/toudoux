package com.example.toudoux

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val titlesList = getTitlesList()

        val preferenceManager = PreferenceManager(this)
        preferenceManager.resetPreferencesIfDateChanged()
        val adapter = TitleAdapter(titlesList, preferenceManager)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getTitlesList(): List<String> {
        val sharedPreferences = getSharedPreferences("MyTitles", Context.MODE_PRIVATE)

        val titlesSet = sharedPreferences.getStringSet("titles", HashSet())

        return titlesSet?.toList() ?: emptyList()
    }
}