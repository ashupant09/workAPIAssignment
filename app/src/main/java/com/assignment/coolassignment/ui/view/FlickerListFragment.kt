package com.assignment.coolassignment.ui.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.assignment.coolassignment.R
import com.assignment.coolassignment.di.ViewModelFactory
import com.assignment.coolassignment.network.State
import com.assignment.coolassignment.pojo.FlickerResponse
import com.assignment.coolassignment.ui.viewmodel.FlickerDataViewModel
import com.assignment.coolassignment.utils.AppUtil
import kotlinx.android.synthetic.main.fragment_flicker_list.*

class FlickerListFragment : Fragment() {

    lateinit var viewModel: FlickerDataViewModel
    private val flickerAdapter: FlickerAdapter by lazy {
        FlickerAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this, ViewModelFactory(activity)).get(FlickerDataViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flicker_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flicker_recycler.apply {
            adapter = flickerAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        viewModel.developerData.observe(viewLifecycleOwner, Observer {
                state->
            when(state){
                is State.isLoading ->
                    progress_layout.visibility = if(state.isLoading) View.VISIBLE else View.GONE

                is State.onSuccess ->{
                    (state.data as FlickerResponse).photos?.photo?.let {
                        if(it.size > 0){
                            flickerAdapter.setFlickerListData(it)
                        }
                    }
                    progress_layout.visibility = if(state.isLoading) View.VISIBLE else View.GONE

                }

                is State.onFailure ->{
                    Toast.makeText(context, state.data as String, Toast.LENGTH_SHORT).show()
                    progress_layout.visibility = if(state.isLoading) View.VISIBLE else View.GONE
                }
            }
        })
        context?.run{
            if(AppUtil.isNetworkConnected(this)){
                viewModel.loadFlickerData()
            }else{
                viewModel.developerData.value = State.onFailure("Internet not connected")
            }
        }
    }


}
