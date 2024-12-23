package com.stark.dogboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stark.dogboard.model.DogResponse
import com.stark.dogboard.repository.DogRepository

class MainViewModel: ViewModel() {
    private val repository = DogRepository()

    fun getRandomDogImage(): LiveData<DogResponse?> {
        return repository.obtRandomDogImg()
    }
}