package android.example.solaraandroid.android.example.solaraandroid

import android.annotation.SuppressLint
import android.example.solaraandroid.POJO.Empresa
import android.example.solaraandroid.R
import android.example.solaraandroid.service.ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Consultar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consultar)

        val textView = findViewById<TextView>(R.id.dadosDaApi)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.getEmpresa()

        call.enqueue(object : Callback<List<Empresa>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<List<Empresa>>, response: Response<List<Empresa>>) {
                if (response.isSuccessful) {
                    val empresa = response.body()
                    if (empresa != null && empresa.isNotEmpty()) {
                        val empresa = empresa[0]

                        textView.text = "ID: ${empresa.id}\nNúmero: ${empresa.nome}\nCidade: ${empresa.cidade}"
                    } else {
                        textView.text = "Nenhuma empresa encontrada."
                    }
                } else {
                    textView.text = "Erro na chamada à API."
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<List<Empresa>>, t: Throwable) {
                textView.text = "Houve um problema"
            }
        })

    }
}