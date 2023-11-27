package android.example.solaraandroid.adapter

import android.content.Context
import android.content.Intent
import android.example.solaraandroid.R
import android.example.solaraandroid.activity.ConversaActivity
import android.example.solaraandroid.dto.ListConversasDTO
import android.example.solaraandroid.view.ConversaViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ConversaAdapter(private val context: Context, private val moradorLogadoId: Int, var conversas: List<ListConversasDTO>) :
    RecyclerView.Adapter<ConversaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_conversa, parent, false)
        return ConversaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversaViewHolder, position: Int) {
        val conversa = conversas[position]

        holder.textViewIdConversa.text = "Id da Conversa: ${conversa.id}"
        holder.textViewContato.text = "Contato: ${conversa.nomeDestinatario}"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ConversaActivity::class.java)
            intent.putExtra("conversaId", conversa.id)
            intent.putExtra("moradorLogadoId", moradorLogadoId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return conversas.size
    }

    fun updateConversas(newConversas: List<ListConversasDTO>) {
        conversas = newConversas
        notifyDataSetChanged()
    }

}
