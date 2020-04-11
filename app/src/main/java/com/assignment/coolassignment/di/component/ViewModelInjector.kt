package com.assignment.coolassignment.di.component

import com.assignment.coolassignment.di.module.NetworkModule
import com.assignment.coolassignment.ui.viewmodel.FlickerDataViewModel
import com.assignment.coolassignment.ui.viewmodel.DataListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(dataListViewModel: DataListViewModel)

    fun inject(flickerDataViewModel: FlickerDataViewModel)

    @Component.Builder
    interface Builder{
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}