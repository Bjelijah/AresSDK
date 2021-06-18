package com.cbj.sdk.libui.mvp

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity :AppCompatActivity() {

    protected var mNavigationBarHeight = 0
    protected var mStateBarHeight = 0






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ARouter.getInstance().inject(this)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
//        var v = LayoutInflater.from(this).inflate(getLayout(),null)
//        setContentView(v)
        var v = getView()

//        initStateBar(v)

        try{
            initView()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()
//        hideBottomUIMenu()
    }

    override fun onDestroy() {
        try{
            deinitView()
        }catch (e:Exception){
            e.printStackTrace()
        }
        super.onDestroy()
    }

    //    @LayoutRes
//    abstract fun getLayout():Int
    abstract fun getView():View
    abstract fun initView()
    abstract fun deinitView()



    fun hideBottomUIMenu(){
        val v = window.decorView
        if(Build.VERSION.SDK_INT in 12..18){
            v.systemUiVisibility = View.GONE
        }else if(Build.VERSION.SDK_INT>=19){
            var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE
            v.systemUiVisibility=uiOptions
        }
    }





    fun setStateBarColor(@ColorInt color:Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
        if (ColorUtils.calculateLuminance(color)>=0.5){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }



    var myToast : Toast?=null
    fun showToast(msg:String){
        runOnUiThread {
            if (msg.isNullOrEmpty()) return@runOnUiThread
            myToast?.cancel()
            myToast = Toast.makeText(this,msg, Toast.LENGTH_SHORT)
            myToast?.setGravity(Gravity.CENTER,0,0)
            myToast?.show()
        }

    }

    fun showToast(msg:String,gravity:Int){
        runOnUiThread {
            if (msg.isNullOrEmpty()) return@runOnUiThread
            myToast?.cancel()
            myToast = Toast.makeText(this,msg, Toast.LENGTH_SHORT)
            myToast?.setGravity(gravity,0,0)
            myToast?.show()
        }
    }


    fun showSnack(msg:String){
        runOnUiThread {
            Snackbar.make(getView(),msg,Snackbar.LENGTH_SHORT).show()
        }
    }



}