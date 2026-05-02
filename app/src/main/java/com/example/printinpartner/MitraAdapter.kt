package com.example.printinpartner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MitraAdapter(
    private val listMitra: ArrayList<Mitra>,
    private val onLongClick: (Mitra, Int) -> Unit
    ) : RecyclerView.Adapter<MitraAdapter.MitraViewHolder>() {

    class MitraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvItemNama)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvItemAlamat)
        val tvHarga: TextView = itemView.findViewById(R.id.tvItemHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MitraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mitra, parent, false)
        return MitraViewHolder(view)
    }

    override fun onBindViewHolder(holder: MitraViewHolder, position: Int) {
        val mitra = listMitra[position]
        holder.tvNama.text = mitra.namaToko
        holder.tvAlamat.text = mitra.alamat
        holder.tvHarga.text = "Harga: Rp ${mitra.harga} /lembar"

        holder.itemView.setOnLongClickListener {
            onLongClick(mitra, holder.adapterPosition)
            true
        }
    }

    override fun getItemCount(): Int = listMitra.size
}