package com.assignment.coolassignment.ui.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class DataViewPagerAdapter(private val context: Context?, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    val titleArray = arrayListOf("Data", "Flicker")
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = DataListFragment()
            1 -> fragment = FlickerListFragment()
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleArray[position]
    }

    override fun getCount(): Int {
        return 2
    }
}