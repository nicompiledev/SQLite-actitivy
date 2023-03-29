package com.example.sqlitedemo
import java.util.*


data class EmpresaModel(
    var nit: String = "",
    var nombre: String = "",
    var email: String = "",
    var direccion: String = "",
    var telefono: String = "",
    var pagina: String = ""

) {
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }

}