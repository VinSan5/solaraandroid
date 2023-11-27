package android.example.solaraandroid.adapter

import android.example.solaraandroid.R
import android.example.solaraandroid.dto.MessageListDTO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MensagemAdapter(var moradorLogadoId: Int) :
    RecyclerView.Adapter<MensagemAdapter.MensagemViewHolder>() {

    private var mensagens: List<MessageListDTO> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem, parent, false)
        return MensagemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        val mensagem = mensagens[position]
        holder.bind(mensagem, moradorLogadoId)
    }

    override fun getItemCount(): Int = mensagens.size

    class MensagemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val conteudoTextView: TextView = itemView.findViewById(R.id.conteudoTextView)

        fun bind(mensagem: MessageListDTO, moradorLogadoId: Int) {
            conteudoTextView.text = mensagem.conteudo

            val params = conteudoTextView.layoutParams as ConstraintLayout.LayoutParams

            if (mensagem.senderId == moradorLogadoId) {
                params.startToStart = ConstraintLayout.LayoutParams.UNSET
                params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            } else {
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.endToEnd = ConstraintLayout.LayoutParams.UNSET
            }

            conteudoTextView.layoutParams = params
        }
    }

    fun atualizarLista(novaLista: List<MessageListDTO>) {
        this.mensagens = novaLista
        notifyDataSetChanged()
    }
}