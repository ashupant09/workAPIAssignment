package com.assignment.coolassignment.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.coolassignment.R
import com.assignment.coolassignment.pojo.Data
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {

    lateinit var dataItem: List<Data>
    private val dataListAdapter: DataListAdapter by lazy {
        DataListAdapter(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)
        supportActionBar?.title = "Detail"
        dataItem = intent.extras.getParcelableArrayList("listData")
        setData()
    }

    private fun setData(){
        dataItem?.let {
            val formatText = String.format(this.getString(R.string.user_title), dataItem.get(0).userId)
            tv_title?.text = formatText

            data_list_detail_recycler.apply {
                adapter = dataListAdapter
                layoutManager = LinearLayoutManager(context)
            }
            dataListAdapter.setDetailListData(ArrayList(dataItem))

        }

    }
}
