package com.example.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "empresa.db"
        private const val TBL_EMPRESA = "tbl_empresa"
        private const val NIT = "nit"
        private const val NOMBRE = "nombre"
        private const val DIRECCION = "direccion"
        private const val TELEFONO = "telefono"
        private const val EMAIL = "email"
        private const val PAGINA = "pagina"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblEmpresa = ("CREATE TABLE " + TBL_EMPRESA + " ("
                + NIT + " INTEGER NOT NULL UNIQUE, " + NOMBRE + " TEXT, " + DIRECCION + " TEXT "
                + TELEFONO + " TEXT " + EMAIL + " TEXT, " + PAGINA + " TEXT " +
                "PRIMARY KEY(" + NIT + "))"
                )
        db?.execSQL(createTblEmpresa)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL( "DROP TABLE IF EXISTS $TBL_EMPRESA")
        onCreate(db)
    }

    fun insertEmpresa(std: EmpresaModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NIT, std.nit)
        contentValues.put(NOMBRE, std.nombre)
        contentValues.put(DIRECCION, std.direccion)
        contentValues.put(TELEFONO, std.telefono)
        contentValues.put(PAGINA, std.pagina)


        val success = db.insert(TBL_EMPRESA, null, contentValues)
        db.close()
        return success
    }

    fun getAllEmpresa(): ArrayList<EmpresaModel>{
        val stdList: ArrayList<EmpresaModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_EMPRESA"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            return ArrayList()
        }

        var nit: Int
        var nombre: String
        var direccion: String
        var telefono: String
        var email: String
        var pagina: String

        if(cursor.moveToFirst()){
            do{
                nit = cursor.getInt(cursor.getColumnIndex("nit"))
                nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                direccion = cursor.getString(cursor.getColumnIndex("direccion"))
                telefono = cursor.getString(cursor.getColumnIndex("telefono"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                pagina = cursor.getString(cursor.getColumnIndex("pagina"))

                val std = EmpresaModel(nit = nit, nombre = nombre, direccion = direccion, telefono = telefono, email = email, pagina = pagina)
                stdList.add(std)
            }while (cursor.moveToNext())
        }

        return stdList

    }

    fun updateEmpresa(std: EmpresaModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NIT, std.nit)
        contentValues.put(NOMBRE, std.nombre)
        contentValues.put(DIRECCION, std.direccion)
        contentValues.put(TELEFONO, std.telefono)
        contentValues.put(EMAIL, std.email)
        contentValues.put(PAGINA, std.pagina)

        val success = db.update(TBL_EMPRESA, contentValues, "nit=" +std.nit, null)
        db.close()
        return success
    }

    fun deleteEmpresaById(nit: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NIT, nit)

        val success = db.delete(TBL_EMPRESA, "nit=$nit", null)
        db.close()
        return success
    }


}
