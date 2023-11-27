package android.example.solaraandroid.view

import android.example.solaraandroid.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConversaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewIdConversa: TextView = itemView.findViewById(R.id.textViewIdConversa)
    val textViewContato: TextView = itemView.findViewById(R.id.textViewContato)
}