package com.vijay.tatvasoftandroidtask.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijay.tatvasoftandroidtask.api.data.HomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepo: HomeRepo) : ViewModel() {

    fun getUsers() = viewModelScope.launch(Dispatchers.IO) {
        val response = homeRepo.getUsers(0)
        Log.d("TAG", "getUsers: ${response.toString()}")
    }
}