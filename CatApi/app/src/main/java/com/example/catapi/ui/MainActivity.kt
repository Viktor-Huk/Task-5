package com.example.catapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapi.databinding.ActivityMainBinding
import com.example.catapi.db.entity.CatEntity
import com.example.catapi.model.Cat
import com.example.catapi.network.NetworkService
import com.example.catapi.repository.CatsRepository
import com.example.catapi.ui.adapter.CatAdapter
import com.example.catapi.ui.viewModel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val catAdapter = CatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolBar)

        val recyclerView = binding.mainRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = catAdapter

        viewModel.data?.observe(this, Observer {
            catAdapter.addItems(it)
        })
    }
}
