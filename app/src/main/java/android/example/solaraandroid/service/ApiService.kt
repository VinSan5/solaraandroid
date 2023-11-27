package android.example.solaraandroid.service

import android.example.solaraandroid.POJO.Evento
import android.example.solaraandroid.POJO.Mensagem
import android.example.solaraandroid.POJO.MoradorIdGetter
import android.example.solaraandroid.dto.ListConversasDTO
import android.example.solaraandroid.dto.MensagemDTO
import android.example.solaraandroid.dto.MessageListDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("eventos/salvarEvento")
    fun postEvent(@Body event: Evento): Call<Response<String>>

    interface GetMensagens {
        @GET("/conversa/{conversaId}/mensagens")
        suspend fun getMensagens(@Path("conversaId") conversaId: Int): Response<List<MessageListDTO>>
    }

    interface GetMoradorId{
        @GET("/morador/getId?")
        suspend fun getMoradorId(@Query("email") email: String): Response<MoradorIdGetter>
    }

    interface GetConversas{
        @GET("/conversa/conversaList/{moradorId}")
        suspend fun getConversas(@Path("moradorId") id: Int): Response<List<ListConversasDTO>>
    }

    @POST("/enviarMensagem")
    suspend fun enviarMensagem(@Body message: MessageListDTO): Response<String>

}