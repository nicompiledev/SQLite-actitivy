package com.example.sqlitedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmpresaAdapter : RecyclerView.Adapter<EmpresaAdapter.EmpresaViewHolder>() {

    private var stdList: ArrayList<EmpresaModel> = ArrayList()
    private var onClickItem:((EmpresaModel) -> Unit)? = null
    private var onClickDeleteItem:((EmpresaModel) -> Unit)? = null

    fun addItems(items: ArrayList<EmpresaModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnclickItem(callback: (EmpresaModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnclickDeleteItem(callback: (EmpresaModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent, false)
        return EmpresaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class EmpresaViewHolder (var view : View) : RecyclerView.ViewHolder(view) {
        private var nit = view.findViewById<TextView>(R.id.tvNit)
        private var nombre = view.findViewById<TextView>(R.id.tvNombre)
        private var direccion = view.findViewById<TextView>(R.id.tvDireccion)
        private var telefono = view.findViewById<TextView>(R.id.tvTelefono)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var pagina = view.findViewById<TextView>(R.id.tvPagina)
        var btnDelete: Button = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: EmpresaModel) {
            nit.text = std.nit.toString()
            nombre.text = std.nombre
            direccion.text = std.direccion
            telefono.text = std.telefono
            email.text =std.email
            pagina.text = std.pagina
        }
    }
}
