package android.example.solaraandroid.POJO

import java.time.LocalDate

data class Empresa (
    val id: Int,
    val nome : String,
    val enderecoCompleto: String,
    val numero : String,
    val cidade : String,
    val rua : String,
    val cnpj : String,
    val cep : String,
    val uf : String,
    val dataRegistro : String,
    val condominio: List<String>,
    val user : List<String>,
    val apartamentos : List<String>,
    val predio : List<String>,
    val funcionario : List<String>,
    val morador : List<String>
)