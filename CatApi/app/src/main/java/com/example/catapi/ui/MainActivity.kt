package com.example.catapi.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapi.App
import com.example.catapi.R
import com.example.catapi.databinding.ActivityMainBinding
import com.example.catapi.model.Cat
import com.example.catapi.ui.ShowImageActivity.Companion.CAT_ID
import com.example.catapi.ui.ShowImageActivity.Companion.CAT_URL
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

        if (!isNetworkAvailable()) {
            binding.buttonTryConnection.visibility = View.VISIBLE
        } else {
            binding.buttonTryConnection.visibility = View.INVISIBLE
            viewModel.getCats()
        }

        val callbacks = object : Paginate.Callbacks {

            override fun onLoadMore() {
                // Load next page of data (e.g. network or database)
                loading = true
                loadedAllItems = true

                if (!isNetworkAvailable()) {
                    binding.buttonTryConnection.visibility = View.VISIBLE
                } else {
                    binding.buttonTryConnection.visibility = View.INVISIBLE
                    viewModel.loadCats()
                }
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
            .setLoadingTriggerThreshold(0)
            .addLoadingListItem(true)
            .build()

        binding.buttonTryConnection.setOnClickListener {
            callbacks.onLoadMore()
        }
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

    fun isNetworkAvailable(): Boolean {

        var result = false

        val connectivityManager =
            App.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null) {

            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    result = true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    result = true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    result = true
                }
            }
        }
        return result
    }
}
