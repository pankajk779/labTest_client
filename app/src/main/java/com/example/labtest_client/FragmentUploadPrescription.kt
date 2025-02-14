package com.example.labtest_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentUploadPrescription :Fragment {

    private val TAG :String = this.javaClass.name

    private constructor()
    companion object{
        fun newInstance() :FragmentUploadPrescription{
            return FragmentUploadPrescription()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload_precp, container, false)
    }
}