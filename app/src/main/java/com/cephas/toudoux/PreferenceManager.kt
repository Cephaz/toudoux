package com.cephas.toudoux

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "MyPreferences",
        Context.MODE_PRIVATE
    )

    private val LAST_RESET_DATE_KEY = "last_reset_date"

    fun saveStrikeThroughState(key: String, isStriked: Boolean) {
        sharedPreferences.edit().putBoolean(key, isStriked).apply()
    }

    fun getStrikeThroughState(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    private fun saveLastResetDate() {
        val currentDate = getCurrentDate()
        sharedPreferences.edit().putString(LAST_RESET_DATE_KEY, currentDate).apply()
    }

    private fun getLastResetDate(): String {
        return sharedPreferences.getString(LAST_RESET_DATE_KEY, "") ?: ""
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun hasDateChanged(): Boolean {
        val lastResetDate = getLastResetDate()
        val currentDate = getCurrentDate()
        return lastResetDate != currentDate
    }

    fun resetPreferencesIfDateChanged() {
        if (hasDateChanged()) {
            resetAllPreferences()
            saveLastResetDate()
        }
    }

    private fun resetAllPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}