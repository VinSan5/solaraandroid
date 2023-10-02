package android.example.solaraandroid.POJO

import java.time.LocalDate

data class Morador (
    val id : Int,
    val representante : Boolean,
    val atribuido : Boolean,
    val exame : Boolean,
    val dataRegistro : LocalDate,
    val predio: Predio,
    val apartamento: Apartamento,
    val condominio: Condominio,
    val empresa: Empresa
)