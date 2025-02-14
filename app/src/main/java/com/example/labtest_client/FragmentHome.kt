package com.example.labtest_client

import android.os.Bundle
import android.os.Trace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FragmentHome : Fragment {

    private val TAG: String = this.javaClass.name
    private var viewModel :MyViewModel? = null
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var databaseReference :DatabaseReference

    private constructor()

    companion object {
        fun newInstance(): FragmentHome {
            return FragmentHome()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory())[MyViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Trace.beginSection(TAG + "_onCreateView")
        val root :View = viewModel!!.fragmentHomeLayout!!
        Log.d(TAG, "onCreateView: ")
        Trace.endSection()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("data")

        val rv : RecyclerView = view.findViewById(R.id.rv_home)
//        val rvAdapter :RvAdapterHome = RvAdapterHome(requireContext(), ArrayList<Data>() ,viewModel!!.bitmapList)
        val rvAdapter :RvAdapterHome = RvAdapterHome(requireContext(),
            viewModel!!.holderListHome.value!!)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        databaseReference.addValueEventListener( object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "onDataChange: ${snapshot.value}" )
                val data :Data = Data(1)
                rvAdapter.updateData(data)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ${error.message}")
            }
        })

        viewModel!!.bitmapMicroscope?.prepareToDraw()
        viewModel!!.bitmapHomeCollection?.prepareToDraw()
        viewModel!!.bitmapLogin?.prepareToDraw()
        viewModel!!.bitmapFruitsTable?.prepareToDraw()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}