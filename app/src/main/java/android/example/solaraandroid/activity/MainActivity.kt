package android.example.solaraandroid.activity


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.example.solaraandroid.R
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null) {
            val nomeUsuario = currentUser.email //pega o email do usuário, porque a gente quer o email do usuário
            if (nomeUsuario != null) {
                val welcomeText = findViewById<TextView>(R.id.welcomeText)
                welcomeText.text = "Seja bem-vindo\n$nomeUsuario" //diz "oi oi" pro usuário
            }
        }


        val startReserva = findViewById<Button>(R.id.select_reserva)
        startReserva.setOnClickListener {
            val intent = Intent(this, Reserva::class.java)
            startActivity(intent)
        }

        val startChat = findViewById<Button>(R.id.chat)
        startChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

    }

}
