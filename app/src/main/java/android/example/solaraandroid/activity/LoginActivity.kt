package android.example.solaraandroid.activity

import android.content.Intent
import android.example.solaraandroid.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var etLoginMail: EditText
    private lateinit var etLoginPw: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)
        etLoginMail = findViewById(R.id.et_login_email)
        etLoginPw = findViewById(R.id.et_login_password)
        btnLogin.setOnClickListener {

            when {
                TextUtils.isEmpty(etLoginMail.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity, "Preencha o email", Toast.LENGTH_LONG).show()
                }

                TextUtils.isEmpty(etLoginPw.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity, "Preencha a senha", Toast.LENGTH_LONG).show()
                }

                else -> {
                    val email: String = etLoginMail.text.toString().trim { it <= ' ' }
                    val pw: String = etLoginPw.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pw).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }
}