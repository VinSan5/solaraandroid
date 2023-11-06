package android.example.solaraandroid.service

import android.example.solaraandroid.POJO.Empresa
import android.example.solaraandroid.POJO.Evento
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("empresa/getEmpresa")
    fun getEmpresa(): Call<List<Empresa>> //Retorna uma lista das empresas

    @POST("eventos/salvarEvento")
    fun postEvent(@Body event: Evento): Call<String>

}