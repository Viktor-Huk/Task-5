package com.example.catapi.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapi.App
import com.example.catapi.db.CatRoomDatabase
import com.example.catapi.db.DatabaseManager
import com.example.catapi.model.Cat
import com.example.catapi.network.NetworkService
import com.example.catapi.repository.CatsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val catsRepository = CatsRepository(application)

    private val _data = MutableLiveData<List<Cat>>()

    init {
        viewModelScope.launch {
            _data.value = catsRepository.getCats()
        }
    }

    fun getCats(): LiveData<List<Cat>> = _data
}