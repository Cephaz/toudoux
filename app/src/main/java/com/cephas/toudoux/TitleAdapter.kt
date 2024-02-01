package com.cephas.toudoux

import android.graphics.Paint
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TitleAdapter(private val titles: List<String>, private val preferenceManager: PreferenceManager) :
    RecyclerView.Adapter<TitleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_title, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = titles[position]

        val spannableString = SpannableString(title)
        val key = "strike_state_$position"

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val isStriked = toggleStrikeThrough(holder.textViewTitle, key)
                preferenceManager.saveStrikeThroughState(key, isStriked)
            }
        }

        holder.textViewTitle.text = spannableString
        holder.textViewTitle.setOnClickListener {
            val isStriked = toggleStrikeThrough(holder.textViewTitle, key)
            preferenceManager.saveStrikeThroughState(key, isStriked)
        }

        val isStriked = preferenceManager.getStrikeThroughState(key)
        if (isStriked) {
            holder.textViewTitle.paintFlags = holder.textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.textViewTitle.paintFlags = holder.textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    private fun toggleStrikeThrough(textView: TextView, key: String): Boolean {
        val isStriked = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG != 0
        textView.paintFlags = if (isStriked) {
            textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        } else {
            textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        return !isStriked
    }
}
