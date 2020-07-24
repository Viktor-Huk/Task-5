package com.example.catapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapi.model.Cat
import com.example.catapi.repository.CatsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val catsRepository = CatsRepository()

    private val _data = MutableLiveData<List<Cat>>()

    val data: LiveData<List<Cat>> = _data

    fun getCats() {
        viewModelScope.launch {

            val cats = catsRepository.getCats()
            _data.value = cats
        }
    }

    fun loadCats() {
        viewModelScope.launch {
            catsRepository.loadCatsFromServer()
            getCats()
        }
    }
}
