package com.example.labtest_client

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.animation.ValueAnimator.ofInt
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.labtest_client.controller.MyApp
import com.example.labtest_client.views.CustomWelcomeBanner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FragmentWelcome : Fragment {

    private val TAG: String = this.javaClass.name
    private lateinit var viewModel :MyViewModel
    private lateinit var transaction :FragmentTransaction

    private constructor()

    companion object {
        fun newInstance(): FragmentWelcome {
            return FragmentWelcome()
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

        return inflater.inflate(
            R.layout.layout_fragment_welcome_test,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MyApp.getWorkerMainHandler().post {

            GlobalScope.launch {

                val fragment: FragmentWelcome2 = FragmentWelcome2.newInstance()
                transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                transaction.replace(R.id.container, fragment, "TAG")

                async{
                    Log.d(TAG, "onViewCreated: init-bitmaps")
                    val bitmapMicor : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_microscope_color)
                    val bitmapHomeCollect : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.home_collection_icon)
                    val bitmapLogin : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_profile)
                    val bitmapFruitsTable : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_fruits_table)
                    Handler(requireContext().mainLooper).post {

                        viewModel.bitmapMicroscope = bitmapMicor
                        viewModel.bitmapHomeCollection = bitmapHomeCollect
                        viewModel.bitmapLogin = bitmapLogin
                        viewModel.bitmapFruitsTable = bitmapFruitsTable
                    }
                    Log.d(TAG, "onViewCreated: init-bitmaps is done")
                }
            }

        }

        val btn_features: Button = view.findViewById(R.id.btn_features)
        val btn_next: CustomWelcomeBanner = view.findViewById(R.id.cust_welcome_banner)
        btn_features.setOnClickListener(object : OnClickListener {

            override fun onClick(v: View?) {
                transaction.commit()
            }
        })

        val positionAnimatorStatic: ValueAnimator = ofInt(85, 0).apply {
            duration = 350
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 50
        }
        positionAnimatorStatic.addUpdateListener(object : AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                btn_next.positionStaticX(animation.animatedValue as Int)
                btn_next.invalidate()
            }
        })

        val positionAnimatorLogo: ValueAnimator = ofInt(85, 0).apply {
            duration = 350
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 100
        }
        positionAnimatorLogo.addUpdateListener(object : AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                btn_next.positionLogoDst(animation.animatedValue as Int)
                btn_next.invalidate()
            }
        })

        positionAnimatorLogo.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                positionAnimatorStatic.start()
            }

            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
            }

            override fun onAnimationEnd(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })


        val positionAnimatorBitmapDst: ValueAnimator = ValueAnimator.ofInt(85, 0).apply {
            duration = 350
            interpolator = AccelerateDecelerateInterpolator()
        }
        positionAnimatorBitmapDst.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                positionAnimatorLogo.start()
            }

            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
            }

            override fun onAnimationEnd(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        positionAnimatorBitmapDst.addUpdateListener(object : AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                btn_next.positionDstRect(animation.animatedValue as Int)
                btn_next.invalidate()
            }
        })

        positionAnimatorBitmapDst.start()
    }
}