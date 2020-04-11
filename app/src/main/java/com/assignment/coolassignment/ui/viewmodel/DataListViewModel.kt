package com.assignment.coolassignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.assignment.coolassignment.base.BaseViewModel
import com.assignment.coolassignment.network.ListApi
import com.assignment.coolassignment.network.State
import com.assignment.coolassignment.pojo.DataList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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

    fun loadDataList() {
        subscription = listApi.getData()
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
    }
}