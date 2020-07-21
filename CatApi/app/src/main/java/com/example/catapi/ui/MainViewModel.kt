package com.example.catapi.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.catapi.model.Cat
import com.example.catapi.repository.network.NetworkService
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _cats = MutableLiveData<List<Cat>>()

    init {
        refresh()
    }

    fun cats(): LiveData<List<Cat>> = _cats

    fun refresh() {
        viewModelScope.launch {
            _cats.value = NetworkService.getCatApi().getCats()
        }
    }
}
