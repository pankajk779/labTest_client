package com.example.labtest_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentBookTest : Fragment {

    private val TAG :String = this.javaClass.name
    private constructor()

    companion object{
        fun newInstance() :FragmentBookTest{
            return FragmentBookTest()
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
        return inflater.inflate(R.layout.fragment_book_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv : RecyclerView = view.findViewById(R.id.rv_book_test)
        val rvAdapter :RvAdapterBookTest = RvAdapterBookTest(requireContext())
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }
}