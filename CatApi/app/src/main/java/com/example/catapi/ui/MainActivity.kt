package com.example.catapi.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catapi.R
import com.example.catapi.databinding.ActivityMainBinding
import com.example.catapi.model.Cat
import com.example.catapi.ui.adapter.CatAdapter


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val catAdapter = CatAdapter((::openImage)())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolBar)

        val recyclerView = binding.mainRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = catAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            val myLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            var loading = true
            var saveCurrentState = 1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentFirstVisible = myLayoutManager.findFirstVisibleItemPosition()
                val itemCount = myLayoutManager.itemCount
                val r = myLayoutManager.childCount

                if (loading) {

                    if ((r + currentFirstVisible) >= itemCount) {
                        loading = false
                        saveCurrentState = currentFirstVisible
                        viewModel.loadCats()
                    }

                } else if ((saveCurrentState + 1) == currentFirstVisible) {
                    loading = true
                }
            }
        })

        viewModel.data.observe(this, Observer {
            catAdapter.submitList(it)
        })
        viewModel.getCats()
    }

    private fun openImage(): (Cat) -> Unit = { cat ->
        val intent = Intent(this, ShowImageActivity::class.java)

        intent.putExtra(CAT_URL, cat.url)
        intent.putExtra(CAT_ID, cat.id)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    companion object {
        const val CAT_URL = "cat_url"
        const val CAT_ID = "cat_id"
    }
}


