package android.example.solaraandroid.service

import android.example.solaraandroid.POJO.Empresa
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("empresa/getEmpresa")
    fun getEmpresa(): Call<List<Empresa>> //Retorna uma lista das empresas
}