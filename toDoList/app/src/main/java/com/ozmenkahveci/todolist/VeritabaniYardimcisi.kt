package com.ozmenkahveci.todolist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VeritabaniYardimcisi(context: Context): SQLiteOpenHelper(context,"toDoList.sqlite",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE todolist(list_id INTEGER PRIMARY KEY AUTOINCREMENT,list_todo TEXT,list_date TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS todolist")
        onCreate(db)
    }

}