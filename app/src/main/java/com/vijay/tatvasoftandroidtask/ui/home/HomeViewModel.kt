package com.vijay.tatvasoftandroidtask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijay.tatvasoftandroidtask.api.Const
import com.vijay.tatvasoftandroidtask.api.Resource
import com.vijay.tatvasoftandroidtask.api.data.HomeRepo
import com.vijay.tatvasoftandroidtask.api.model.User
import com.vijay.tatvasoftandroidtask.api.model.UserListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepo: HomeRepo) : ViewModel() {
    data class ItemImage(val imageUrl: String, val showFull: Boolean = false)
    data class ItemUser(val imageUrl: String, val name: String)

    private val mMainList = MutableLiveData<List<Any>>()
    val mainList: LiveData<List<Any>> = mMainList

    private val mMainResource = MutableLiveData<Resource<UserListResponse>>()
    val mainResource: LiveData<Resource<UserListResponse>> = mMainResource

    var hasMore: Boolean = true
    var offSet = 0

    fun getUsers() = viewModelScope.launch(Dispatchers.IO) {
        try {
            mMainResource.postValue(Resource.loading())
            val response = homeRepo.getUsers(offSet)
            updateMainList(response.data.users)
            mMainResource.postValue(Resource.success(response))
        } catch (e: Exception) {
            mMainResource.postValue(Resource.error(e.message.toString(), null))
        }
    }

    fun listScrolled(
        visibleItemCount: Int, firstVisibleItemPosition: Int,
        totalItemCount: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        if ((visibleItemCount + firstVisibleItemPosition) >=
            totalItemCount && firstVisibleItemPosition >= 0
        ) {
            if (hasMore) {
                offSet = offSet.plus(Const.PAGE_LIMIT)
                try {
                    mMainResource.postValue(Resource.loadingMore())
                    val result = homeRepo.getUsers(offSet)
                    updateMainList(result.data.users)
                    mMainResource.postValue(Resource.success(result))
                } catch (e: Exception) {
                    mMainResource.postValue(Resource.error(e.message.toString(), null))
                }
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
                val arrayList = this.value as? ArrayList<Any>
                arrayList?.addAll(resultList)
                mMainList.postValue(arrayList)
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