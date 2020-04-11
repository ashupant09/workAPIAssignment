package com.assignment.coolassignment.base

import androidx.lifecycle.ViewModel
import com.assignment.coolassignment.di.component.DaggerViewModelInjector
import com.assignment.coolassignment.di.module.NetworkModule
import com.assignment.coolassignment.ui.viewmodel.FlickerDataViewModel
import com.assignment.coolassignment.ui.viewmodel.DataListViewModel

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
}