package com.assignment.coolassignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignment.coolassignment.base.BaseViewModel
import com.assignment.coolassignment.network.ListApi
import com.assignment.coolassignment.network.Result
import com.assignment.coolassignment.network.State
import com.assignment.coolassignment.pojo.Data
import com.assignment.coolassignment.pojo.DataList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataListViewModel : BaseViewModel() {

    @Inject
    lateinit var listApi: ListApi

    val listData: MutableLiveData<State> = MutableLiveData()

    private lateinit var subscription: Disposable


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    suspend fun getLoadDataList(): Result<List<Data>>{
        return hitApi(call= { listApi.getData()})
    }

    fun loadDataList() {
        viewModelScope.launch {
            val result = getLoadDataList()
            when(result){
                is Result.isLoading -> {
                    listData.value = State.isLoading(true)
                }

                is Result.onSuccess ->{
                    listData.value = State.onSuccess(DataList(result.data))
                    listData.value = State.isLoading(false)
                }

                is Result.onFailure ->{
                    listData.value = State.onFailure(result.data)
                    listData.value = State.isLoading(false)
                }
            }
        }
    }
        /*subscription = listApi.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                listData.value = State.isLoading(true)
            }
            .doOnTerminate {
                listData.value = State.isLoading(false)
            }
            .subscribe(
                { result ->
                    if (result.isSuccessful) {
                        when (result.code()) {
                            200 -> {
                                listData.value = State.onSuccess(DataList(result.body()))
                            }
                            else -> {
                                listData.value = State.onFailure("some error")
                            }
                        }
                    } else {
                        listData.value = State.onFailure("some error")
                    }
                },
                { error ->
                    listData.value = State.onFailure(error.message)
                }
            )
    }*/
}