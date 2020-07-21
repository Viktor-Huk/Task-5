package com.example.catapi.ui

import androidx.paging.PositionalDataSource
import com.example.catapi.model.Cat

class CatDataSource(private val viewModel: MainViewModel): PositionalDataSource<Cat>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Cat>) {
        viewModel.refresh()
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Cat>) {
        viewModel.refresh()
    }
}