package com.cbj.sdk.libui

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
import com.blankj.utilcode.util.BarUtils
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity :AppCompatActivity() {

    protected var mNavigationBarHeight = 0
    protected var mStateBarHeight = 0


    private var mCompositeDisposable: CompositeDisposable?=null

    protected open fun addDisposable(subscription: Disposable){
        if (mCompositeDisposable?.isDisposed != false){
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    protected open fun dispose() = mCompositeDisposable?.dispose()



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
        BarUtils.setNavBarVisibility(this,false)
    }

    fun setStateBarColor(@ColorInt color:Int) {
        BarUtils.setStatusBarColor(this,color)
        BarUtils.setStatusBarLightMode(this,ColorUtils.calculateLuminance(color)>=0.5)
    }



    var myToast : Toast?=null
    fun showToast(msg:String?){
        if (msg.isNullOrEmpty()) return
        runOnUiThread {
            myToast?.cancel()
            myToast = Toast.makeText(this,msg, Toast.LENGTH_SHORT)
            myToast?.setGravity(Gravity.CENTER,0,0)
            myToast?.show()
        }

    }

    fun showToast(msg:String?,gravity:Int){
        if (msg.isNullOrEmpty()) return
        runOnUiThread {
            myToast?.cancel()
            myToast = Toast.makeText(this,msg, Toast.LENGTH_SHORT)
            myToast?.setGravity(gravity,0,0)
            myToast?.show()
        }
    }


    fun showSnack(msg:String?){
        if (msg.isNullOrEmpty())return
        runOnUiThread {
            Snackbar.make(getView(),msg,Snackbar.LENGTH_SHORT).show()
        }
    }



}