package android.example.solaraandroid.POJO

import java.time.LocalDate

data class Predio (
    val id : Int,
    val numero : Int,
    val andar : Int,
    val numeroApartamento : Int,
    val referencia : String,
    val dataRegistro : LocalDate,
    val morador : List<Morador>,
    val condominio: Condominio,
    val apartamento : List<Apartamento>,
    val empresa: Empresa
)