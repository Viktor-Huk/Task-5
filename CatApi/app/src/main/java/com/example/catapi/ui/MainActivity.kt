package com.example.catapi.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapi.databinding.ActivityMainBinding
import com.example.catapi.ui.adapter.CatAdapter


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

        viewModel.cats().observe(this, Observer {
            catAdapter.refresh(it)
        })

        catAdapter.setOnLoadMoreListener(object  : CatAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.refresh()
                catAdapter.endLoading()
            }
        })
    }
}
