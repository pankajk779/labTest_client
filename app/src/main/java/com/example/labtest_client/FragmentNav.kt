package com.example.labtest_client

import android.os.Bundle
import android.os.Trace
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class FragmentNav : Fragment {

    private val TAG :String = this.javaClass.name
    private lateinit var viewModel :MyViewModel
    private lateinit var nav :BottomNavigationView

    private constructor()

    companion object{
        fun newInstance() : FragmentNav {
            return FragmentNav()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().window.navigationBarColor = resources.getColor(R.color.black)
        requireActivity().window.statusBarColor = resources.getColor(R.color.black)

        val view: View = requireActivity().window.decorView
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory())[MyViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        Trace.beginSection(TAG)
//        val view :View = inflater.inflate(R.layout.layout_test, container, false)
//        Trace.endSection()

        val rootView :ViewGroup  = viewModel._freagmentTestLayout!! as ViewGroup
        val nav :BottomNavigationView = rootView.findViewById(R.id.btm_nav)
        val navParent :ViewGroup = nav.parent as ViewGroup
        val index :Int = navParent.indexOfChild(nav)

        rootView.removeView(nav)
        rootView.addView(viewModel.bottomNavView, index)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Trace.beginSection(TAG + "_nav")
        val root :ViewGroup = view as ViewGroup
        for (i :Int in 0 until root.childCount){

            val child :View = root.getChildAt(i)
            if(child is BottomNavigationView){
                nav = child
            }
        }
//        nav.inflateMenu(R.menu.bottom_nav_menu)
        Trace.endSection()

        nav.setOnItemSelectedListener(object :NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.book_test->{

                        val fragment :FragmentBookTest = FragmentBookTest.newInstance()
                        val transaction : FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        transaction.replace(R.id.container_2, fragment)
                        transaction.commit()
                        return true
                    }

                    R.id.home->{

                        val fragment :FragmentHome = FragmentHome.newInstance()
                        val transaction : FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        transaction.replace(R.id.container_2, fragment)
                        transaction.commit()
                        return true
                    }

//                    R.id.health->{
//
//                        val fragment : FragmentHealth = FragmentHealth.newInstance()
//                        val transaction : FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//                        transaction.replace(R.id.container_2, fragment)
//                        transaction.commit()
//                        return true
//                    }

                    R.id.cart->{

                        val fragment : FragmentCart = FragmentCart.newInstance()
                        val transaction : FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        transaction.replace(R.id.container_2, fragment)
                        transaction.commit()
                        return true
                    }
                }
                return false
            }
        })

        nav.selectedItemId = R.id.home

    }
}