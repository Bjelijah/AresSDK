package com.cbj.aresandroid.demo.ui

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cbj.aresandroid.databinding.ActivityDemoBinding
import com.cbj.sdk.libui.BaseActivity
import com.cbj.sdk.libui.observe
import com.cbj.sdk.libui.singleClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@Route(path="/demo/aty")
class DemoActivity:BaseActivity<ActivityDemoBinding>() {

    private val mModule: DemoViewModule by viewModels()

    override fun initView() {
        mBinding.btn.singleClick {
            mModule.login()
        }
    }

    override fun initObserver(){
        observe(mModule.res){
            ARouter.getInstance().build("/demo/aty/list").navigation()
        }
    }


    override fun initData() {

    }
}