package com.assignment.coolassignment.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.coolassignment.ui.viewmodel.FlickerDataViewModel
import com.assignment.coolassignment.ui.viewmodel.DataListViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val activity: Activity?): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DataListViewModel::class.java)){
            return DataListViewModel() as T
        }else if(modelClass.isAssignableFrom(FlickerDataViewModel::class.java)){
            return FlickerDataViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}