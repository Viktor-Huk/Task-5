package com.example.catapi.ui

import androidx.lifecycle.*
import com.example.catapi.model.Cat
import com.example.catapi.repository.CatsRepository
import com.example.catapi.repository.network.NetworkService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val catsRepository = CatsRepository()
    private var _cats = MutableLiveData<MutableList<Cat>>()

    fun cats(): LiveData<MutableList<Cat>> {
        if (_cats.value.isNullOrEmpty()) {
            loadCats()
        }
        return _cats
    }

    fun loadCats() {
        viewModelScope.launch {
            _cats.value?.addAll(catsRepository.getCats())
        }
    }
}
