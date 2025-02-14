package com.example.labtest_client

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class FragmentLogin : Fragment {

    private val TAG :String = this.javaClass.name
    private lateinit var shPref : SharedPreferences
    private lateinit var spEditor : SharedPreferences.Editor

    constructor()

    companion object{
        fun newInstance() :FragmentLogin{
            return FragmentLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        shPref = requireActivity().getSharedPreferences(Util.MyPref, Context.MODE_PRIVATE)
        spEditor = shPref.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSubmit : Button = view.findViewById(R.id.btn_submit)
        val editText :EditText = view.findViewById(R.id.et_login_name)

        btnSubmit.setOnClickListener{view ->

            spEditor.putString(Util.username, editText.text.toString())
            spEditor.commit()

            val fragment : FragmentNav = FragmentNav.newInstance()
            val transaction : FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            transaction.addToBackStack(null)
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
    }
}