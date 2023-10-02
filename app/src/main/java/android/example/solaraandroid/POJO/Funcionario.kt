package android.example.solaraandroid.POJO

import java.time.LocalDate
import java.time.LocalDateTime

data class Funcionario (
    val id : Int,
    val funcao : String,
    val salario : Double,
    val dataRegistro : LocalDate,
    val horaEntrada : LocalDateTime,
    val horaSaida : LocalDateTime,
    val pessoa: AbstractPessoa,
    val user: User,
    val empresa: Empresa,
    val condominio: Condominio
)