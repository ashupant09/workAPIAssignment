package com.assignment.coolassignment.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.coolassignment.R
import com.assignment.coolassignment.pojo.Photo
import com.squareup.picasso.Picasso

class FlickerAdapter: RecyclerView.Adapter<FlickerAdapter.FlickerViewHolder>() {

    var photoList: List<Photo> = arrayListOf()

    fun setFlickerListData(photos: List<Photo>){
        this.photoList = photos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickerViewHolder {

        return FlickerAdapter.FlickerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.flicker_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return photoList.size
    }

    override fun onBindViewHolder(holder: FlickerViewHolder, position: Int) {
        photoList[position].let {
            //"https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg"
            val url = "https://farm%s.staticflickr.com/%s/%s_%s.jpg"
            val imgUrl = String.format(url, it.farm, it.server, it.id, it.secret)
            Picasso.get()
                .load(imgUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imgAvatar);
        }
    }

    class FlickerViewHolder(item: View): RecyclerView.ViewHolder(item){

        var imgAvatar = item.findViewById<ImageView>(R.id.img_avatar)
    }
}