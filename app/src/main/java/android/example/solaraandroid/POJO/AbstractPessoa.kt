package android.example.solaraandroid.POJO

data class AbstractPessoa (
    val id : Int,
    val nome : String,
    val idade : Int,
    val cpf : String,
    val rg : String,
    val cep : String,
    val apartamento: Apartamento,
    val condominio: Condominio,
    val empresa: Empresa
)