package com.example.sqlitedemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Declaración de variables miembro de la clase

    private lateinit var edNit: EditText
    private lateinit var edNombre: EditText
    private lateinit var edDireccion: EditText
    private lateinit var edTelefono: EditText
    private lateinit var edEmail: EditText
    private lateinit var edPagina: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: EmpresaAdapter? = null
    private var std: EmpresaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de vistas y variables
        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addEmpresa() }
        btnView.setOnClickListener { getEmpresas() }
        btnUpdate.setOnClickListener { updateEmpresa() }


        // Configuración de botones
        adapter?.setOnclickItem {
            Toast.makeText(this, it.nombre,Toast.LENGTH_SHORT).show()
            // Update record
            edNit.setText(it.nit)
            edNombre.setText(it.nombre)
            edDireccion.setText(it.direccion)
            edTelefono.setText(it.telefono)
            edEmail.setText((it.email))
            edPagina.setText(it.pagina)
            std = it
        }

        adapter?.setOnclickDeleteItem {
            deleteEmpresa(it.nit)
        }

    }

    private fun getEmpresas() {
        val stdList = sqLiteHelper.getAllEmpresa()
        Log.e("pppp", "${stdList.size}")

        //Display data in RecyclerView
        adapter?.addItems(stdList)
    }

    private fun addEmpresa() {
        val nit = edNit.text.toString()
        val nombre = edNombre.text.toString()
        val direccion = edDireccion.text.toString()
        val telefono = edTelefono.text.toString()
        val email = edEmail.text.toString()
        val pagina = edPagina.text.toString()

        if (nit.isEmpty() || nombre.isEmpty() || direccion.isEmpty()|| telefono.isEmpty() || email.isEmpty() || pagina.isEmpty()) {
            Toast.makeText(this, "Please enter required field...", Toast.LENGTH_SHORT).show()
        } else {
            val std = EmpresaModel(nit = nit , nombre = nombre, direccion = direccion , telefono = telefono, email = email, pagina = pagina )
            val status = sqLiteHelper.insertEmpresa(std)
            // Check insert success or not success
            if (status > -1) {
                Toast.makeText(this, "Empresa Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getEmpresas()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateEmpresa(){
        val nit = edNit.text.toString()
        val nombre = edNombre.text.toString()
        val direccion = edDireccion.text.toString()
        val telefono = edTelefono.text.toString()
        val email = edEmail.text.toString()
        val pagina = edPagina.text.toString()

        //Check record not change
        if (nombre == std?.nombre && email == std?.email){
            Toast.makeText( this, "Record not changed...", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = EmpresaModel(nit = std!!.nit, nombre = nombre, direccion = direccion, telefono = telefono, email = email, pagina = pagina )
        val status = sqLiteHelper.updateEmpresa(std)
        if(status > -1){
            clearEditText()
            getEmpresas()
        }else{
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }


    }

    private fun deleteEmpresa(nit: String){
        //if(id== null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setCancelable(true)
        builder.setPositiveButton( "Yes"){ dialog, _ ->
            sqLiteHelper.deleteEmpresaById(nit)
            getEmpresas()
            dialog.dismiss()
        }
        builder.setNegativeButton( "No"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edNombre.setText("")
        edEmail.setText("")
        edNombre.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =  EmpresaAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView() {
        edNombre = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}
