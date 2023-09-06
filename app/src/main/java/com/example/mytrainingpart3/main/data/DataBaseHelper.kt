package com.example.mytrainingpart3.main.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context:Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){

    companion object {
        val TAG = DataBaseHelper::class.simpleName

        private const val DB_NAME = "wordDB"

        private const val DB_VERSION = 1
    }
        //SQL文
    private val createTableSql =
        "CREATE TABLE word_data (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)"//クリエイト文

    override fun onCreate(db: SQLiteDatabase?){

        //命令文呼び出し
        db?.execSQL(createTableSql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        if(oldVersion < newVersion){
            //バージョンの変更時の処理
        }
    }

}