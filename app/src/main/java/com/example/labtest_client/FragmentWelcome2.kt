package com.example.labtest_client

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Trace
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest_client.controller.MyApp
import com.example.labtest_client.views.CustomLayoutOrgans
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FragmentWelcome2 : Fragment {

    private val TAG: String = this.javaClass.name
    private lateinit var viewModel: MyViewModel
    private lateinit var shPref :SharedPreferences
    private lateinit var spEditor :SharedPreferences.Editor
    private lateinit var rv :RecyclerView
    private lateinit var fragmentTransaction :FragmentTransaction

    private constructor()

    companion object {
        fun newInstance(): FragmentWelcome2 {
            return FragmentWelcome2()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory())[MyViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Trace.beginSection(TAG+"_onCreateView")

//        val decor :View = requireActivity().window.decorView
//        decor.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val window : Window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = resources.getColor(R.color.white)

        val view :View = LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_welcome_2,
            container,
            false
        )
        val rv :RecyclerView = view.findViewById(R.id.rv_welcome_2)
        rv.clipToPadding = false
        rv.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        rv.setPadding(
            Util.getPixels(50, resources.displayMetrics.density).toInt(),
            0,
            Util.getPixels(50, resources.displayMetrics.density).toInt(),
            0
        )

        Trace.endSection()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Trace.beginSection(TAG + "_onViewCreated")

        shPref = requireActivity().getSharedPreferences(Util.MyPref, Context.MODE_PRIVATE)
        spEditor = shPref.edit()

        MyApp.getWorkerMainHandler().post {
            GlobalScope.launch {

                async{
                    Log.d(TAG, "onViewCreated: init-GlobalScope")
                    val fragment :Fragment = getNextFragment()
                    fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    fragmentTransaction.replace(R.id.container, fragment)
                }

                async{
                    Log.d(TAG, "onViewCreated: init-createViewHolders()")
                    val oList :ArrayList<RvWelcome2Adapter.MyViewHolderWelcome2> = createViewHolders(requireContext())
                    val handler :Handler = Handler(requireContext().mainLooper)
                    handler.post{
                        viewModel._holderList1.value = oList
                    }
                }

                async{
                    Log.d(TAG, "onViewCreated: init-createViewHoldersHome()")
                    val oListHome :ArrayList<HolderList> = createRvHoldersHome(requireContext())
                    Handler(requireContext().mainLooper).post{
                        viewModel._holderList.value = oListHome
                    }
                }

                async{
                    Trace.beginSection(TAG)
                    val parent1 :LinearLayout = LinearLayout(requireContext())
                    val view1 :View = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_nav,parent1, false)

                    val bottomNav :BottomNavigationView = BottomNavigationView(requireContext())
                    val navParmas :LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Util.getPixels(56, resources.displayMetrics.density).toInt())
                    navParmas.topMargin = -(Util.getPixels(56, resources.displayMetrics.density).toInt())
                    bottomNav.layoutParams = navParmas
                    bottomNav.inflateMenu(R.menu.bottom_nav_menu)


                    val coordinatorL :CoordinatorLayout = CoordinatorLayout(requireContext())
                    val homeLayout :View = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_home, coordinatorL, false)

                    Handler(requireContext().mainLooper).post {
                        viewModel.fragmentHomeLayout = homeLayout
                    }
                    Handler(requireContext().mainLooper).post {
                        viewModel._freagmentTestLayout = view1
                        viewModel.bottomNavView = bottomNav
                    }

                    Trace.endSection()
                }

                Log.d(TAG, "onViewCreated: done")
            }
        }

        val btnHome : MaterialButton = view.findViewById(R.id.btn_welcome_2_home)
        btnHome.setOnClickListener{ view ->

            fragmentTransaction.commit()
        }

        val rvAdapter: RvWelcome2Adapter? =  activity?.let{RvWelcome2Adapter(it)}

        viewModel._holderList1.observe(viewLifecycleOwner,
            Observer {
                if(it != null){
                    for(i :Int in 0 until viewModel.bitmapList.size){
                        val bitmap :Bitmap = viewModel.bitmapList.get(i)
                        bitmap.prepareToDraw()
                    }
                    rvAdapter?.updateList(it, viewModel.bitmapList)
                }
        })

        rvAdapter?.let { val rv: RecyclerView = createRv(view, it) }

        Trace.endSection()
    }

    private fun createRv(parent :View, adapter: RvWelcome2Adapter): RecyclerView {

        val layoutManager: LinearLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        rv = parent.findViewById(R.id.rv_welcome_2)
        rv.adapter = adapter
        rv.layoutManager = layoutManager
        return rv
    }

    private fun createViewHolders(context : Context) :ArrayList<RvWelcome2Adapter.MyViewHolderWelcome2>{
        Trace.beginSection("RvAdapterFactory" + "_createHolders")

        val oList :ArrayList<RvWelcome2Adapter.MyViewHolderWelcome2> = ArrayList()

        val parent0 : LinearLayout = LinearLayout(context)
        val view0 : View = LayoutInflater.from(context).inflate(
            R.layout.layout_first_banner, parent0, false)
        val holder0 : RvWelcome2Adapter.FirstViewHolder = RvWelcome2Adapter.FirstViewHolder(view0)
        oList.add(holder0)

        val parent1 : LinearLayout = LinearLayout(context)
        val view1 : View = LayoutInflater.from(context).inflate(
            R.layout.layout_blank, parent1, false)
        val holder1 : RvWelcome2Adapter.MyViewHolder = RvWelcome2Adapter.MyViewHolder(view1)
        oList.add(holder1)

        val parent2 : LinearLayout = LinearLayout(context)
        val view2 : View = LayoutInflater.from(context).inflate(
            R.layout.layout_blank, parent2, false)
        val holder2 : RvWelcome2Adapter.MyViewHolder = RvWelcome2Adapter.MyViewHolder(view2)
        oList.add(holder2)

        val parent3 :LinearLayout = LinearLayout(context)
        val view3 :View = LayoutInflater.from(context).inflate(
            R.layout.layout_blank, parent3, false)
        val holder3 :RvWelcome2Adapter.MyViewHolder = RvWelcome2Adapter.MyViewHolder(view3)
        oList.add(holder3)

        val parent4 :LinearLayout = LinearLayout(context)
        val view4 :View = LayoutInflater.from(context).inflate(
            R.layout.layout_blank, parent4, false)
        val holder4 :RvWelcome2Adapter.MyViewHolder = RvWelcome2Adapter.MyViewHolder(view4)
        oList.add(holder4)

        val parent5 :LinearLayout = LinearLayout(context)
        val view5 :View = LayoutInflater.from(context).inflate(
            R.layout.layout_blank, parent5, false)
        val holder5 :RvWelcome2Adapter.MyViewHolder = RvWelcome2Adapter.MyViewHolder(view5)
        oList.add(holder5)

        val parent6 :LinearLayout = LinearLayout(context)
        val view6 :View = LayoutInflater.from(context).inflate(
            R.layout.layout_blank, parent6, false)
        val holder6 :RvWelcome2Adapter.MyViewHolder = RvWelcome2Adapter.MyViewHolder(view6)
        oList.add(holder6)

        val parent7 :LinearLayout = LinearLayout(context)
        val view7 :View = LayoutInflater.from(context).inflate(
            R.layout.layout_open_home, parent7, false)
        val holder7 :RvWelcome2Adapter.EndViewHolder = RvWelcome2Adapter.EndViewHolder(view7)
        oList.add(holder7)

        Trace.endSection()
        return oList
    }

    private fun getNextFragment() :Fragment{

        if(shPref.contains(Util.username) && shPref.getString(Util.username, "") != ""){
            return FragmentNav.newInstance()
        }
        return FragmentLogin.newInstance()
    }

    private fun createRvHoldersHome(context :Context) :ArrayList<HolderList>{
        val oList :ArrayList<HolderList> = ArrayList()

        val parent1 :CustomLayoutOrgans = CustomLayoutOrgans(context)
        val view1 :View = LayoutInflater.from(context).inflate(R.layout.layout_home_card_1_2, parent1, false)
        val holder1 :ViewHolderFirstCard = ViewHolderFirstCard(view1, viewModel.bitmapMicroscope!!, viewModel.bitmapHomeCollection!!, viewModel.bitmapLogin!!)
        oList.add(holder1)

        val parent2 :LinearLayout = LinearLayout(context)
        val view2 :View = LayoutInflater.from(context).inflate(R.layout.layout_search_bar, parent2, false)
        val holder2 :Vh = Vh(view2)
        oList.add(holder2)

        val parent3 :LinearLayout = LinearLayout(context)
        val view3 :View = LayoutInflater.from(context).inflate(R.layout.layout_custom_sale_banner, parent3, false)
        val holder3 :ViewHolderSecondCard = ViewHolderSecondCard(view3, viewModel.bitmapFruitsTable!!)
        oList.add(holder3)

        val parent4 :LinearLayout = LinearLayout(context)
        val view4 :View = LayoutInflater.from(context).inflate(R.layout.layout_rv_home, parent4, false)
        val holder4 :Vh = Vh(view4)
        oList.add(holder4)

        val parent5 :LinearLayout = LinearLayout(context)
        val view5 :View = LayoutInflater.from(context).inflate(R.layout.layout_rv_home, parent5, false)
        val holder5 :Vh = Vh(view5)
        oList.add(holder5)

        return oList
    }
}