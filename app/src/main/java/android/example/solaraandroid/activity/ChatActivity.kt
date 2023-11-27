package android.example.solaraandroid.activity

import android.example.solaraandroid.POJO.MoradorIdGetter
import android.example.solaraandroid.R
import android.example.solaraandroid.adapter.ConversaAdapter
import android.example.solaraandroid.dto.ListConversasDTO
import android.example.solaraandroid.service.ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.email ?: ""

    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService.GetMoradorId::class.java)
    private val getConversas = retrofit.create(ApiService.GetConversas::class.java)

    private var moradorId: Int = -1

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConversaAdapter
    private lateinit var conversas: List<ListConversasDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {
            val moradorId = buscarMoradorId(currentUserId)
            buscarConversas(moradorId)

            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this@ChatActivity)
            conversas = emptyList()
            adapter = ConversaAdapter(this@ChatActivity, moradorId, conversas)
            runOnUiThread {
                recyclerView.adapter = adapter
            }
        }
    }

    private suspend fun buscarMoradorId(email: String): Int {
        var result: Int = -1
        try {
            val response = apiService.getMoradorId(email)
            if (response.isSuccessful) {
                val moradorIdGetter: MoradorIdGetter? = response.body()
                moradorIdGetter?.let {
                    result = it.id!!
                    println("ID do Morador: $result")
                }
            } else {
                println("Erro na requisição: ${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private fun buscarConversas(moradorId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = getConversas.getConversas(moradorId)
                if (response.isSuccessful) {
                    val result: List<ListConversasDTO>? = response.body()
                    result?.let {
                        runOnUiThread {
                            conversas = it
                            adapter.conversas = conversas
                            adapter.notifyDataSetChanged()
                        }
                    }
                } else {
                    println("Erro na requisição: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
