package com.example.mytrainingpart3.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mytrainingpart3.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initView()
    }

    private fun initView(){

        val btnDB : Button = findViewById(R.id.button_db)

        btnDB.setOnClickListener{

            Log.i("DB操作","DB操作")

            val intent = Intent(this, DataProcessActivity::class.java)
            startActivity(intent)
        }
    }
}