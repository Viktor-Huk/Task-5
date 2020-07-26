package com.example.catapi.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapi.R
import com.example.catapi.databinding.ActivityMainBinding
import com.example.catapi.model.Cat
import com.example.catapi.ui.adapter.CatAdapter
import com.paginate.Paginate

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val catAdapter = CatAdapter((::openImage)())
    private var loading = false
    private var loadedAllItems = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolBar)

        val recyclerView = binding.mainRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = catAdapter

        viewModel.data.observe(this, Observer {
            catAdapter.submitList(it)
            loadedAllItems = false
            loading = false
        })

        val callbacks = object : Paginate.Callbacks {
            override fun onLoadMore() {
                // Load next page of data (e.g. network or database)
                loading = true
                loadedAllItems = true
                viewModel.loadCats()
            }

            override fun isLoading(): Boolean {
                // Indicate whether new page loading is in progress or not
                return loading
            }

            override fun hasLoadedAllItems(): Boolean {
                // Indicate whether all data (pages) are loaded or not
                return loadedAllItems
            }
        }

        Paginate
            .with(recyclerView, callbacks)
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(true)
            .build()

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
