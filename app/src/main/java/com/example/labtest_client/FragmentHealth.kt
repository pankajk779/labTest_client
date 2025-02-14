package com.example.labtest_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class FragmentHealth : Fragment {

    private val TAG :String = this.javaClass.name
    private var viewModel :MyViewModel? = null
    private lateinit var viewPager : ViewPager2

    constructor()
    companion object{
        fun newInstance() :FragmentHealth{
            return FragmentHealth()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.pager_health)
        val pagerAdapter :ViewpagerAdapter = ViewpagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = pagerAdapter
    }
}