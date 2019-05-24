package com.a_rin.tdelay

import android.arch.lifecycle.ViewModel
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.a_rin.tdelay.model.Line
import kotlinx.android.synthetic.main.activity_detail.*

class MainActivity : AppCompatActivity() {

    val trainTetsudoRepository = TrainTetsudoRepository()
    lateinit var binding: com.a_rin.tdelay.databinding.ActivityMainBinding
    lateinit var adapter: ArrayAdapter<String>
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            val intent = Intent(this,AddActivity::class.java)
            startActivity(intent)

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        val listView = findViewById<ListView>(R.id.list_iiew)
        adapter = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1)
        listView.adapter = adapter

        listView.setOnItemClickListener{parent,view, position, id->
            val intent = Intent(this,DetailActivity::class.java)
            //DetailActivityに何番目の要素なのか渡したい
            val name = adapter.getItem(position)
            intent.putExtra("name",name)
            startActivity(intent)
        }

        trainTetsudoRepository.apiLineFetch()

            .subscribe({
                handler.post {
                    Log.d("tushin","complete1")
                    adapter.clear()
                    val pref = getSharedPreferences("line_name",Context.MODE_PRIVATE)
                    val value = pref.getString("trainName","")

                    adapter.addAll(it.filter { it.name.equals(value)}.map { it.name })
                }
            }, {
                Log.w(ContentValues.TAG, "")
            })


    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        trainTetsudoRepository.apiLineFetch()
            .subscribe({
                handler.post {
                    Log.d("Train","complete2")
                    adapter.clear()
                    val pref = getSharedPreferences("line_name",Context.MODE_PRIVATE)
                    val value = pref.getString("trainName","")
                    Log.d("Train",value)
                    adapter.addAll(it.filter { it.name.equals(value)}.map { it.name })
                }
            }, {
                Log.w("Train", it)
            })
    }

}

