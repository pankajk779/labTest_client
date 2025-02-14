package com.example.labtest_client

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.labtest_client.controller.MyApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG :String = this.javaClass.name
    private lateinit var viewModel :MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.navigationBarColor = resources.getColor(R.color.black)
        window.statusBarColor = resources.getColor(R.color.black)

        val view: View = this.window.decorView
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        viewModel = ViewModelProvider(this, ViewModelFactory())[MyViewModel::class.java]

        MyApp.getWorkerMainHandler().post{
            GlobalScope.launch {
                launch {
                    val bitmap1 : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_india_gate_9_16)
                    viewModel.bitmapList.add(bitmap1)

                    val bitmap2 :Bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_women_9_16)
                    viewModel.bitmapList.add(bitmap2)

                    val bitmap3 :Bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_report_9_16)
                    viewModel.bitmapList.add(bitmap3)

                    val bitmap4 :Bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_discount_9_16)
                    viewModel.bitmapList.add(bitmap4)

                    val bitmap5 :Bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_payment_9_16)
                    viewModel.bitmapList.add(bitmap5)

                    val bitmap6 :Bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_contact_9_16)
                    viewModel.bitmapList.add(bitmap6)
                }
            }
        }
        openWelcomeFragment()
//        openTest2Fragment()
    }

    private fun openWelcomeFragment(){

        val fragment :FragmentWelcome = FragmentWelcome.newInstance()
        val transaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun openTest2Fragment(){
        val fragment : FragmentTest2 = FragmentTest2.newInstance()
        val transaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

}