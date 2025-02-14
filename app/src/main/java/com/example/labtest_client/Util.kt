package com.example.labtest_client

class Util {

    private val TAG :String = this.javaClass.name

    private constructor()

    companion object{

        private val TAG :String = this.javaClass.name
        private lateinit var util :Util
        private var navBarHeight :Int? = null
        private var statusBarHeight :Int? = null
        private val FRAMES_PER_SEC :Long = 60
        private var DP12 :Int = 0
        private var DP8 :Int = 0
        private var DP40 :Int = 0
        private var DP50 :Int = 0
        val FADE_MILLISECONDS :Long = 250
        val FADE_STEP :Long = 16
        val ALPHA_STEP :Long = 255 / (FADE_MILLISECONDS / FADE_STEP)
        val INVALID_INT :Int = -1
        val INVALID_FLOAT :Float = -1f

        //viewTypes
        val HOLDER_DEFAULT :Int = -1
        val HOLDER_LOGIN_BUTTONS : Int = 1
        val HOLDER_SEARCH_BAR :Int = 2
        val HOLDER_SALE_BANNER :Int = 3

        val MyPref :String = "MyPrefs"
        val username :String = "username"

        fun getInstance() :Util{
            if(!::util.isInitialized){
                util = Util()
            }
            return util
        }

        fun getPixels(dp :Int, densityMultiplier :Float) :Float{
            return dp * densityMultiplier
        }

        fun setNavBarHeight(height :Int?){
            if(navBarHeight == null){
                navBarHeight = height
            }
        }

        fun setStatusBarHeight(height :Int?){
            if(statusBarHeight == null){
                statusBarHeight = height
            }
        }

        fun getNavBarHeight() :Int?{
            return navBarHeight
        }

        fun getStatusBarHeight() :Int?{
            return statusBarHeight
        }

        fun getFramesPerSec() :Long{
            return FRAMES_PER_SEC
        }

        fun getDp12() :Int{
            return DP12
        }

        fun setDP12(pixels :Int){
            if(DP12 == 0){
                DP12 = pixels
            }
        }

        fun getDp8() :Int{
            return DP8
        }

        fun setDP8(pixels :Int){
            if(DP8 == 0){
                DP8 = pixels
            }
        }

        fun getDp40() :Int{
            return DP40
        }

        fun setDP40(pixels :Int){
            if(DP40 == 0){
                DP40 = pixels
            }
        }

        fun getDp50() :Int{
            return DP12
        }

        fun setDP50(pixels :Int){
            if(DP50 == 0){
                DP50 = pixels
            }
        }
    }
}