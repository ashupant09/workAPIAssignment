package com.assignment.coolassignment.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.coolassignment.R
import com.assignment.coolassignment.pojo.Data

class DataListAdapter(private val context: Context?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var dataList: ArrayList<ArrayList<Data>> = arrayListOf()
    var dataDetailList: ArrayList<Data> = arrayListOf()
    var ITEM_VIEW_TYPE = 0

    fun setListData(dataList: ArrayList<ArrayList<Data>>){
        this.dataList = dataList
        ITEM_VIEW_TYPE = 0
        notifyDataSetChanged()
    }

    fun setDetailListData(dataDetailList: ArrayList<Data>){
        this.dataDetailList = dataDetailList
        ITEM_VIEW_TYPE = 1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> DataListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.data_list_item, parent, false))

            1 -> DataItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.data_list_detail_item, parent, false))

            else -> DataListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.data_list_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return if((ITEM_VIEW_TYPE == 0)) { dataList.size }else { dataDetailList.size}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder.itemViewType){
            0 ->{
                val holderItem = holder as DataListItemViewHolder
                context?.run {
                    val formatText = String.format(this.getString(R.string.user_title), dataList.get(position).get(0).userId)
                    holderItem.textTitle.text = formatText
                    if(position == dataList.size-1){
                        holder.viewSeparater.visibility = View.GONE
                    }else{
                        holder.viewSeparater.visibility = View.VISIBLE
                    }

                    holder.itemView.setOnClickListener {
                        val bundle = Bundle().apply {
                            putParcelableArrayList("listData", dataList[position])
                        }
                        context?.run{
                            this.startActivity(Intent(this, ListDetailActivity::class.java).putExtras(bundle))
                        }
                    }
                }


            }

            1 -> {

                val holderItem = holder as DataItemViewHolder
                holder.textTitle.text = dataDetailList.get(position).title
                holder.textBody.text = dataDetailList.get(position).body

                if(position == dataDetailList.size-1){
                    holder.viewSeparater.visibility = View.GONE
                }else{
                    holder.viewSeparater.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE
    }

    class DataListItemViewHolder(item: View): RecyclerView.ViewHolder(item){

        var textTitle = item.findViewById<TextView>(R.id.tv_title)
        var viewSeparater = item.findViewById<View>(R.id.view_separater)

    }

    class DataItemViewHolder(item: View): RecyclerView.ViewHolder(item){

        var textTitle = item.findViewById<TextView>(R.id.tv_title)
        var textBody = item.findViewById<TextView>(R.id.tv_body)
        var viewSeparater = item.findViewById<View>(R.id.view_separater)

    }
}
