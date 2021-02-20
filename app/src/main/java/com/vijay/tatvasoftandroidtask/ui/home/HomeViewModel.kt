package com.vijay.tatvasoftandroidtask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijay.tatvasoftandroidtask.api.data.HomeRepo
import com.vijay.tatvasoftandroidtask.api.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepo: HomeRepo) : ViewModel() {
    data class ItemImage(val imageUrl: String, val showFull: Boolean = false)
    data class ItemUser(val imageUrl: String, val name: String)

    private val mMainList = MutableLiveData<List<Any>>()
    val mainList: LiveData<List<Any>> = mMainList

    var hasMore: Boolean = true
    var offSet = 0

    fun getUsers() = viewModelScope.launch(Dispatchers.IO) {
        val response = homeRepo.getUsers(offSet)
        updateMainList(response.data.users)
    }

    fun listScrolled(
        visibleItemCount: Int, firstVisibleItemPosition: Int,
        totalItemCount: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        if ((visibleItemCount + firstVisibleItemPosition) >=
            totalItemCount && firstVisibleItemPosition >= 0
        ) {
            if (hasMore) {
                val result = homeRepo.getUsers(offSet)
                updateMainList(result.data.users)
            }
        }
    }

    private fun updateMainList(inputData: List<User>) {
        mMainList.apply {
            if (this.value.orEmpty().isNotEmpty()) {
                val resultList = arrayListOf<Any>()
                inputData.forEach {
                    val itemUser = ItemUser(it.image, it.name)
                    resultList.add(itemUser)
                    if (it.items.size.rem(2) == 0) {
                        it.items.forEach {
                            val itemImage = ItemImage(it)
                            resultList.add(itemImage)
                        }
                    } else {
                        it.items.forEachIndexed { index, s ->
                            if (index == 0) {
                                val itemImage = ItemImage(s, true)
                                resultList.add(itemImage)
                            } else {
                                val itemImage = ItemImage(s)
                                resultList.add(itemImage)
                            }
                        }
                    }
                }
                (this.value as? ArrayList<Any>)?.addAll(resultList)
                mMainList.postValue(this.value)
            } else {
                val resultList = arrayListOf<Any>()
                inputData.forEach {
                    val itemUser = ItemUser(it.image, it.name)
                    resultList.add(itemUser)
                    if (it.items.size.rem(2) == 0) {
                        it.items.forEach {
                            val itemImage = ItemImage(it)
                            resultList.add(itemImage)
                        }
                    } else {
                        it.items.forEachIndexed { index, s ->
                            if (index == 0) {
                                val itemImage = ItemImage(s, true)
                                resultList.add(itemImage)
                            } else {
                                val itemImage = ItemImage(s)
                                resultList.add(itemImage)
                            }
                        }
                    }
                }
                this.postValue(resultList)
            }
        }
    }
}