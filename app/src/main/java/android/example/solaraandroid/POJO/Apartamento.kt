package android.example.solaraandroid.POJO

data class Apartamento (
    val id : Int,
    val numero : Int,
    val visitante : List<AbstractPessoa>,
    val morador : List<Morador>,
    val condominio: Condominio,
    val empresa: Empresa,
    val predio: Predio
)