package com.example.printinpartner

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "PrintInDatabase"
        private const val TABLE_MITRA = "MitraTable"
        private const val KEY_ID = "id"
        private const val KEY_NAMA = "nama_toko"
        private const val KEY_ALAMAT = "alamat"
        private const val KEY_JENIS = "jenis_print"
        private const val KEY_HARGA = "harga"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_MITRA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAMA + " TEXT,"
                + KEY_ALAMAT + " TEXT,"
                + KEY_JENIS + " TEXT,"
                + KEY_HARGA + " INTEGER" + ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MITRA")
        onCreate(db)
    }

    fun addMitra(mitra: Mitra): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAMA, mitra.namaToko)
        values.put(KEY_ALAMAT, mitra.alamat)
        values.put(KEY_JENIS, mitra.jenisPrint)
        values.put(KEY_HARGA, mitra.harga)

        val success = db.insert(TABLE_MITRA, null, values)
        db.close()
        return success
    }

    fun getAllMitra(): ArrayList<Mitra> {
        val mitraList = ArrayList<Mitra>()
        val selectQuery = "SELECT  * FROM $TABLE_MITRA"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val mitra = Mitra(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    namaToko = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAMA)),
                    alamat = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ALAMAT)),
                    jenisPrint = cursor.getString(cursor.getColumnIndexOrThrow(KEY_JENIS)),
                    harga = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_HARGA))
                )
                mitraList.add(mitra)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return mitraList
    }

    fun deleteMitra(id: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_MITRA, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
        return success
    }
}