package com.example.printinpartner

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var rvMitra: RecyclerView
    private lateinit var adapter: MitraAdapter
    private lateinit var listData: ArrayList<Mitra>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        dbHelper = DatabaseHelper(this)
        rvMitra = findViewById(R.id.rvMitra)
        rvMitra.layoutManager = LinearLayoutManager(this)

        // Muat data awal
        loadData()
    }

    private fun loadData() {
        listData = dbHelper.getAllMitra()

        // Memasukkan data ke adapter sekaligus menangkap aksi Long Click
        adapter = MitraAdapter(listData) { mitraTerpilih, posisi ->
            tampilkanDialogHapus(mitraTerpilih, posisi)
        }
        rvMitra.adapter = adapter
    }

    private fun tampilkanDialogHapus(mitra: Mitra, position: Int) {
        // Membuat Pop-up Konfirmasi
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus Mitra")
        builder.setMessage("Apakah kamu yakin ingin menghapus '${mitra.namaToko}' dari daftar?")

        // Tombol Ya
        builder.setPositiveButton("Hapus") { dialog, _ ->
            val hapusBerhasil = dbHelper.deleteMitra(mitra.id)

            if (hapusBerhasil > 0) {
                // Hapus data dari array dan refresh tampilan RecyclerView
                listData.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        // Tombol Batal
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}