package com.cbj.sdk.libui

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils

abstract class BaseActivity<VB:ViewBinding> :AppCompatActivity() {

    lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        mBinding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mBinding.root)
        initView()
        initObserver()
        initData()

    }

    abstract fun initView()
    open fun initObserver(){}
    abstract fun initData()


    fun hideBottomUIMenu(){
        BarUtils.setNavBarVisibility(this,false)
    }

    fun setStateBarColor(@ColorInt color:Int) {
        BarUtils.setStatusBarColor(this,color)
        BarUtils.setStatusBarLightMode(this, ColorUtils.calculateLuminance(color)>=0.5)
    }

}