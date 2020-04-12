package com.assignment.coolassignment.base

import androidx.lifecycle.ViewModel
import com.assignment.coolassignment.di.component.DaggerViewModelInjector
import com.assignment.coolassignment.di.module.NetworkModule
import com.assignment.coolassignment.network.Result
import com.assignment.coolassignment.network.State
import com.assignment.coolassignment.ui.viewmodel.FlickerDataViewModel
import com.assignment.coolassignment.ui.viewmodel.DataListViewModel
import kotlinx.coroutines.CoroutineScope
import retrofit2.Response
import java.lang.Exception

open class BaseViewModel: ViewModel() {
    private val injector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when(this){

            is DataListViewModel -> injector.inject(this)
            is FlickerDataViewModel -> injector.inject(this)
        }
    }

    suspend fun <T: Any> hitApi(call: suspend() -> Response<T>): Result<T>{
        return try{
            Result.isLoading(true)
            val resp = call.invoke()
            if(resp.isSuccessful){
                Result.onSuccess(resp.body())
            }else{
                Result.onFailure(resp.errorBody()?.string() ?: "something went wrong")
            }
        }catch (e: Exception){
            Result.onFailure(e.message ?: "something went wrong")
        }

    }
}