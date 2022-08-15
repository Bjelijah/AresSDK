package com.cbj.aresandroid

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cbj.aresandroid.databinding.ActivityMainBinding
import com.cbj.sdk.libbase.utils.LOG
import com.cbj.sdk.libui.BaseActivity
import com.cbj.sdk.libui.bindView

@Route(path = "/demo/main")
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initView() {
        mBinding.btn.setOnClickListener {
            LOG.I("123","onClick")
            ARouter.getInstance().build("/demo/aty").navigation()
        }
    }

    override fun initData() {

    }
}