package com.a_rin.tdelay

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.a_rin.tdelay.model.Line

class DetailActivity : AppCompatActivity(){

    lateinit var adapter: ArrayAdapter<Line>

     override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //MainActivityに戻る
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        } ?: IllegalAccessException("Toolbar cannot be null")

         val pref = getSharedPreferences("line_name", Context.MODE_PRIVATE)

         //路線名を表示しようとしている
        val nameTextView = findViewById<TextView>(R.id.name)

         val name = getIntent().getStringExtra("name")
         nameTextView.setText(name)

         //削除ボタン(SharedPreferenceから)
         val button = findViewById<Button>(R.id.deleate)
         button.setOnClickListener {
             if (name.equals(pref.getString("trainName",""))){
                 pref.edit().remove("trainName").apply()
            }
             finish()
         }
    }


}