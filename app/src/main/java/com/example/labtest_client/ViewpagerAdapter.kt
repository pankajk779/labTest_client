package com.example.labtest_client

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerAdapter :FragmentStateAdapter{

    private val TAG :String = this.javaClass.name

    constructor(fragmentManager: FragmentManager, lifecycle : Lifecycle)
            :super(fragmentManager, lifecycle)

    override fun createFragment(position: Int): Fragment {
        return FragmentPager()
    }

    override fun getItemCount(): Int {
        return 2
    }
}