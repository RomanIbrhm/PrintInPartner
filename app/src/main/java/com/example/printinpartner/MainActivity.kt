package com.example.printinpartner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val etNamaToko = findViewById<EditText>(R.id.etNamaToko)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val etJenisPrint = findViewById<EditText>(R.id.etJenisPrint)
        val etHarga = findViewById<EditText>(R.id.etHarga)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnLihatData = findViewById<Button>(R.id.btnLihatData)

        btnSimpan.setOnClickListener {
            val nama = etNamaToko.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val jenis = etJenisPrint.text.toString().trim()
            val hargaStr = etHarga.text.toString().trim()

            // Validasi Input
            if (nama.isEmpty() || alamat.isEmpty() || hargaStr.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan ke SQLite
            val status = dbHelper.addMitra(Mitra(0, nama, alamat, jenis, hargaStr.toInt()))
            if (status > -1) {
                Toast.makeText(this, "Mitra berhasil disimpan!", Toast.LENGTH_SHORT).show()
                etNamaToko.text.clear()
                etAlamat.text.clear()
                etJenisPrint.text.clear()
                etHarga.text.clear()
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
            }
        }

        btnLihatData.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}
