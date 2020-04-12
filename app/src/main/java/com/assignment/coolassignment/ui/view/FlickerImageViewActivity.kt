package com.assignment.coolassignment.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assignment.coolassignment.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_flicker_image_view.*
import java.lang.Exception

class FlickerImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flicker_image_view)
        val imgUrl = intent.getStringExtra("imgURL")
        val transitionName = intent.getStringExtra("trans_name")
        img_view.transitionName = transitionName
        Picasso.get()
            .load(imgUrl)
            .noFade()
            .into(img_view, object: Callback{
                override fun onSuccess() {

                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {

                    supportStartPostponedEnterTransition()
                }
            })
    }
}
