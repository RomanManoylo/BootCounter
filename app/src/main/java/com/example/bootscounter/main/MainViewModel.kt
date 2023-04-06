package com.example.bootscounter.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bootscounter.database.AppDatabase
import com.example.bootscounter.database.model.Boot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(database: AppDatabase) : ViewModel() {

    private val bootDao = database.bootDao()

    val bootList: LiveData<List<Boot>>
        get() = bootLiveData

    private val bootLiveData = MutableLiveData<List<Boot>>()

    fun checkBootList() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = bootDao.getAll()
            bootLiveData.postValue(list)
        }
    }

}