package android.example.solaraandroid.activity

import android.example.solaraandroid.R
import android.example.solaraandroid.adapter.MensagemAdapter
import android.example.solaraandroid.dto.MessageListDTO
import android.example.solaraandroid.service.ApiService
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConversaActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val getMensagensService = retrofit.create(ApiService.GetMensagens::class.java)
    private val apiService = retrofit.create(ApiService::class.java)

    private lateinit var recyclerViewMensagens: RecyclerView
    private lateinit var mensagemAdapter: MensagemAdapter
    private var moradorLogadoId: Int = -1
    private var recipientId: Int = -1

    private lateinit var editTextMensagem: EditText
    private lateinit var btnEnviar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversa)
        recyclerViewMensagens = findViewById(R.id.recyclerViewMensagens)

        val conversaId = intent.getIntExtra("conversaId", -1)
        moradorLogadoId = intent.getIntExtra("moradorLogadoId", -1)
        Log.d("ConversaActivity", "moradorLogadoId: $moradorLogadoId")
        mensagemAdapter = MensagemAdapter(moradorLogadoId)
        recyclerViewMensagens.layoutManager = LinearLayoutManager(this)
        recyclerViewMensagens.adapter = mensagemAdapter

        editTextMensagem = findViewById(R.id.editTextMensagem)
        btnEnviar = findViewById(R.id.btnEnviar)

        btnEnviar.setOnClickListener {
            val conteudoMensagem = editTextMensagem.text.toString().trim()

            if (conteudoMensagem.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    enviarMensagem(conversaId, moradorLogadoId, conteudoMensagem)

                    withContext(Dispatchers.Main) {
                        editTextMensagem.text.clear()
                    }
                }
            } else {
                Toast.makeText(this, "Digite sua mensagem antes de enviar", Toast.LENGTH_SHORT).show()
            }
        }


        if (conversaId != -1 && moradorLogadoId != -1) {
            GlobalScope.launch(Dispatchers.IO) {
                carregarMensagens(conversaId, moradorLogadoId)
            }
        } else {
            Toast.makeText(this, "Erro: ID da conversa não fornecido.", Toast.LENGTH_SHORT).show()
        }

    }

    private suspend fun carregarMensagens(conversaId: Int, moradorId: Int): List<MessageListDTO> {
        try {
            val response = getMensagensService.getMensagens(conversaId)

            if (response.isSuccessful) {
                val mensagens: List<MessageListDTO>? = response.body()

                mensagens?.let {
                    if (it.isNotEmpty()) {
                        val recipientId = it.last().recipientId

                        runOnUiThread {
                            mensagemAdapter.moradorLogadoId = moradorId
                            mensagemAdapter.atualizarLista(it)
                            println("O ID do outro morador é: $recipientId")
                        }
                    }
                }

                return mensagens ?: emptyList()
            } else {
                println("Erro na requisição de mensagens: ${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()
    }




    private suspend fun enviarMensagem(conversaId: Int, moradorLogadoId: Int, conteudo: String) {
        val mensagens = carregarMensagens(conversaId, moradorLogadoId)

        if (mensagens.isNotEmpty()) {
            val recipientId = mensagens.last().recipientId

            val mensagem = MessageListDTO(moradorLogadoId, recipientId, conteudo)

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.enviarMensagem(mensagem)

                    if (response.isSuccessful) {
                        Log.d("ConversaActivity", "Mensagem enviada com sucesso!")
                    } else {
                        Log.e("ConversaActivity", "Erro ao enviar mensagem: ${response.message()}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            Log.e("ConversaActivity", "Erro: Nenhuma mensagem na conversa para obter o recipientId")
        }
    }


}