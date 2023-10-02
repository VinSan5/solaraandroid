package android.example.solaraandroid.POJO

import java.time.LocalDate

data class User (
    val id : Int,
    val email : String,
    val senha : String,
    val dataRegistro : LocalDate,
    val funcionario : Funcionario,
    val empresa: Empresa,
    val condominio: Condominio
)