package com.cbj.aresandroid

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.cbj.aresandroid.databinding.ActivityMainBinding
import com.cbj.sdk.libui.BaseActivity
import com.cbj.sdk.libui.bindView


class MainActivity : BaseActivity() {


    private val mBinding : ActivityMainBinding by bindView()

    override fun getView(): View = mBinding.root

    override fun initView() {
        mBinding.btn.setOnClickListener {
            ARouter.getInstance().build("/demo/aty").navigation()
        }
    }

    override fun deinitView() {
    }
}