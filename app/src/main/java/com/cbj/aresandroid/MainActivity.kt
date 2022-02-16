package com.cbj.aresandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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