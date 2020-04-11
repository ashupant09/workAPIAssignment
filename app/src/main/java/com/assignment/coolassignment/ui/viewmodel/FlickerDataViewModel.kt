package com.assignment.coolassignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.assignment.coolassignment.base.BaseViewModel
import com.assignment.coolassignment.network.FlickerApi
import com.assignment.coolassignment.network.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FlickerDataViewModel : BaseViewModel() {
    @Inject
    lateinit var flickerApi: FlickerApi

    val developerData: MutableLiveData<State> = MutableLiveData()

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadFlickerData() {
        val data = HashMap<String, String>()
        //https://www.flickr.com/services/rest/?method=&api_key=&per_page&page&format=json&nojsoncallback=1&api_sig=82f8c83d6ecc6d72548beb1f7dd74624
        data.apply {
            put("method", "flickr.photos.getRecent")
            put("api_key", "0f00fa6ecb007e64a9c1d38076e53303")
            put("per_page", "20")
            put("page", "1")
            put("format", "json")
            put("nojsoncallback", "1")
            put("api_sig", "82f8c83d6ecc6d72548beb1f7dd74624")
        }
        subscription = flickerApi.getFlickerData(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                developerData.value = State.isLoading(true)
            }
            .doOnTerminate {
                developerData.value = State.isLoading(false)
            }
            .subscribe(
                { result ->
                    if (result.isSuccessful) {
                        when (result.code()) {
                            200 -> {
                                developerData.value = State.onSuccess(result.body())
                            }
                            else -> {
                                developerData.value = State.onFailure("some error")
                            }
                        }
                    } else {
                        developerData.value = State.onFailure("some error")
                    }
                },
                { error ->
                    developerData.value = State.onFailure(error.message)
                }
            )
    }
}