package com.a_rin.tdelay

import android.content.ContentValues
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toolbar
import com.a_rin.tdelay.model.Line

class MainActivity : AppCompatActivity() {

    val trainTetsudoRepository = TrainTetsudoRepository()
    lateinit var binding: com.a_rin.tdelay.databinding.ActivityMainBinding
    lateinit var adapter: ArrayAdapter<Line>

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
        adapter = ArrayAdapter<Line>(this,android.R.layout.simple_expandable_list_item_1)
        listView.adapter = adapter

        listView.setOnItemClickListener{parent,view, position, id->
            val intent = Intent(this,DetailActivity::class.java)
            //ここに、ListViewの何個目の要素なのかを渡す処理
            startActivity(intent)
        }

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
                adapter.clear()
                adapter.addAll(it)
            }, {
                Log.w(ContentValues.TAG, "")
            })
    }
}
