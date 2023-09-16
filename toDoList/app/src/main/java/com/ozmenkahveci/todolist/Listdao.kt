package com.ozmenkahveci.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context

class Listdao {
    fun listEkle(vt:VeritabaniYardimcisi,list_todo:String,list_date:String){
        val db =vt.writableDatabase

        val values = ContentValues()
        values.put("list_todo",list_todo)
        values.put("list_date",list_date)

        db.insertOrThrow("todolist",null,values)

        db.close()
    }
    fun listSil(vt:VeritabaniYardimcisi,list_id:Int){

        val db = vt.writableDatabase
        db.delete("todolist","list_id =?", arrayOf(list_id.toString()))
        db.close()

    }
    fun listGuncelle(vt:VeritabaniYardimcisi,list_id: Int,list_todo: String,list_date: String){

        val db = vt.writableDatabase

        val values = ContentValues()
        values.put("list_todo",list_todo)
        values.put("list_date",list_date)

        db.update("todolist",values,"list_id =?", arrayOf(list_id.toString()))

        db.close()
    }
    @SuppressLint("Range", "Recycle")
    fun tumList(vt:VeritabaniYardimcisi): ArrayList<Lists>{
        val db = vt.writableDatabase

        val todolistList = ArrayList<Lists>()
        val c = db.rawQuery("SELECT * FROM todolist",null)

        while (c.moveToNext()){
            val list = Lists(c.getInt(c.getColumnIndex("list_id"))
                ,c.getString(c.getColumnIndex("list_todo"))
                ,c.getString(c.getColumnIndex("list_date")))
            todolistList.add(list)
        }
        return todolistList
    }
    fun isRecordExists(vt:VeritabaniYardimcisi): Boolean {
        val db = vt.readableDatabase
        val query = "SELECT * FROM todolist WHERE list_id IS NOT NULL"
        val cursor = db.rawQuery(query, null)
        val recordExists = cursor.count > 0
        cursor.close()
        db.close()
        return recordExists
    }
}