package com.example.catapi.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapi.repository.CatsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val catsRepository = CatsRepository(application)

    var data = catsRepository.data

    init {
        refreshCats()
    }

    private fun refreshCats() {
        viewModelScope.launch {
            catsRepository.refresh()
        }
    }
}