package com.assignment.coolassignment.ui.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.assignment.coolassignment.R
import com.assignment.coolassignment.di.ViewModelFactory
import com.assignment.coolassignment.network.State
import com.assignment.coolassignment.pojo.Data
import com.assignment.coolassignment.pojo.DataList
import com.assignment.coolassignment.ui.viewmodel.DataListViewModel
import com.assignment.coolassignment.utils.AppUtil
import kotlinx.android.synthetic.main.fragment_data_list.*
import java.util.*
import kotlin.collections.ArrayList

class DataListFragment : Fragment() {

    private lateinit var viewModel: DataListViewModel
    private val dataListAdapter: DataListAdapter by lazy {
        DataListAdapter(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this, ViewModelFactory(activity)).get(DataListViewModel::class.java)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data_list_recycler.apply {
            adapter = dataListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.listData.observe(viewLifecycleOwner, Observer {
            state->
            when(state){
                is State.isLoading ->
                    progress_layout.visibility = if(state.isLoading) View.VISIBLE else View.GONE

                is State.onSuccess ->{
                    val data = (state.data as DataList).listData
                    val map: MutableMap<Int, ArrayList<Data>> = TreeMap()
                    data?.forEach {
                        if(map.containsKey(it.userId)){
                            map.get(it.userId)?.add(it)
                        }else{
                            val newData = mutableListOf<Data>()
                            newData.add(it)
                            map.put(it.userId!!, ArrayList(newData))
                        }
                    }
                    val finalData: MutableList<ArrayList<Data>> = arrayListOf()
                    map.toSortedMap(compareBy<Int>{ it })
                    map.forEach{
                        finalData.add(it.value)
                    }
                    dataListAdapter.setListData(ArrayList(finalData))
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
                viewModel.loadDataList()
            }else{
                viewModel.listData.value = State.onFailure("Internet not connected")
            }
        }
    }


}
