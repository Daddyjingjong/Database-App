package com.example.dataapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteConfig(context:Context):SQLiteOpenHelper(context,DATABASE_NAME,null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PROFESSION TEXT,RESIDENCE TEXT,PASSWORD TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
    }

    fun insertData(name:String,profession:String,residence:String,password:String){
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_2, name)
        contentValues.put(COL_3, profession)
        contentValues.put(COL_4, residence)
        contentValues.put(COL_5, password)

        db.insert(TABLE_NAME,null,contentValues)

    }

    val alldata:Cursor
        get(){
            val db = this.writableDatabase
            val response = db.rawQuery("SELECT * FROM" + TABLE_NAME,null)
            return response
        }



    fun updateData(id:String ,name:String,profession:String,residence:String,password:String):Boolean{

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_2, name)
        contentValues.put(COL_3, profession)
        contentValues.put(COL_4, residence)
        contentValues.put(COL_5, password)

        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        db.close()
        return true
    }


    fun deleteData(name:String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COL_2 + "=?", arrayOf(name))
    }




    companion object{
        val DATABASE_NAME = "appdb.DB"
        val TABLE_NAME = "users"
        val COL_1 = "ID"
        val COL_2 = "NAME"
        val COL_3 = "PROFESSION"
        val COL_4 = "RESIDENCE"
        val COL_5 = "PASSWORD"
    }


}