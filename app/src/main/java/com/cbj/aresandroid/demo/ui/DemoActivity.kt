package com.cbj.aresandroid.demo.ui

import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.cbj.aresandroid.databinding.ActivityDemoBinding
import com.cbj.sdk.libui.*
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
class DemoActivity:BaseActivity() {

    private val mBinding : ActivityDemoBinding by bindView()
    private val mModule: DemoViewModule by viewModels()

    override fun getView(): View = mBinding.root

    override fun initView() {
        initObserver()
        mBinding.btn.singleClick {
            mModule.login()
        }


    }

    private fun initObserver(){
        observe(mModule.res){
            toast(it)
        }
    }



    override fun deinitView() {
    }
}