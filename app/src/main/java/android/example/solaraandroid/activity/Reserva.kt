package android.example.solaraandroid.activity


import android.annotation.SuppressLint
import android.example.solaraandroid.POJO.Evento
import android.example.solaraandroid.R
import android.example.solaraandroid.service.ApiService
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Reserva : AppCompatActivity() {

    private lateinit var btnSubmitScdl : Button
    private lateinit var eventName : EditText
    private lateinit var eventDate : LocalDate

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reserva)



        btnSubmitScdl = findViewById(R.id.btnSubmitScdl)
        eventName = findViewById(R.id.eventName)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGrpEspacos)
        var selectedSpace: String = ""

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioBtnChurras -> {
                    selectedSpace = "Churrasqueira"
                }
                R.id.radioBtnFestas -> {
                    selectedSpace = "Salao de Festas"
                }
            }
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val currentDate = LocalDate.now()

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            eventDate = selectedDate
            val difference = ChronoUnit.DAYS.between(currentDate, selectedDate)

            if (difference > 5) {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.scheduleApprove))
            } else {
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.scheduleDenial))
                Toast.makeText(this, "Um evento só pode ser agendado com 5 dias de antecendência", Toast.LENGTH_SHORT).show()
            }


        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userMail = currentUser?.email
        //Instância Retrofit
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        // CRIAR MÉTODO POST NA CLASSE APISERVICE PARA SALVAR EVENTOS
        val apiService = retrofit.create(ApiService::class.java)


        btnSubmitScdl.setOnClickListener {
            val isEventNameEmpty = TextUtils.isEmpty(eventName.text.toString())
            val isRadioButtonNotSelected = radioGroup.checkedRadioButtonId == -1
            val isDateValid = !eventDate.isBefore(LocalDate.now().plusDays(5))



            if (isEventNameEmpty) {
                Toast.makeText(this@Reserva, "O evento precisa de um nome", Toast.LENGTH_LONG).show()
            } else if (isRadioButtonNotSelected) {
                Toast.makeText(this@Reserva, "Selecione um espaço a ser reservado", Toast.LENGTH_LONG).show()
            } else if (!isDateValid) {
                Toast.makeText(this@Reserva, "A data não é válida", Toast.LENGTH_LONG).show()
            } else {
                val evento = Evento(
                    emailmorador = userMail,
                    evento = eventName.text.toString(),
                    espaco = selectedSpace,
                    date = eventDate.toString()
                )

                val call = apiService.postEvent(evento)

                call.enqueue(object : Callback<Response<String>> {
                    override fun onResponse(call: Call<Response<String>>, response: Response<Response<String>>) {
                        if (response.isSuccessful) {
                            val resposta = response.body()
                            Toast.makeText(this@Reserva, "Agendamento feito com sucesso", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@Reserva, "Houve um erro ao agendar", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                        Toast.makeText(this@Reserva, "Ops, ocorreu um erro", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

    }
}