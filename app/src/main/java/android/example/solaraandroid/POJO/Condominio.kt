package android.example.solaraandroid.POJO

import java.time.LocalDate

data class Condominio (
    val id : Int,
    val nome : String,
    val dataRegistro : LocalDate,
    val piscina : Boolean,
    val churrasqueira : Boolean,
    val avisos : String,
    val predio : List<Predio>,
    val empresa: Empresa,
    val usuario : List<User>,
    val funcionarios : List<Funcionario>,
    val apartamento : List<Apartamento>
)