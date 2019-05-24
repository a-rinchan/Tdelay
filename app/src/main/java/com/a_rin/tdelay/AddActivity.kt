package com.a_rin.tdelay

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner

class AddActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //MainActivityに戻る
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        } ?: IllegalAccessException("Toolbar cannot be null")


        val pref = getSharedPreferences("line_name",Context.MODE_PRIVATE)

        //SharedPreferenceへの保存
        val button = findViewById<Button>(R.id.save)
        button.setOnClickListener {
            //Spinnerオブジェクトを取得
            val spinner = findViewById<Spinner>(R.id.spinner)
            //選択されている値の取得
            val trainName = spinner.getSelectedItem().toString()
            //プリファレンスに保存する
            pref.edit().putString("trainName", trainName).commit()

            finish()
        }
    }
}