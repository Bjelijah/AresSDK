package com.cbj.aresandroid.demo.ui

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibaba.android.arouter.facade.annotation.Route
import com.cbj.aresandroid.databinding.ActivityListBinding
import com.cbj.aresandroid.demo.ui.adapter.DeviceListAdapter
import com.cbj.sdk.libui.BaseActivity
import com.cbj.sdk.libui.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
/**
 * @author : CBJ
 * @date   : 2022/8/12 14:14
 * @desc   :
 */

@AndroidEntryPoint
@Route(path = "/demo/aty/list")
class DemoListActivity:BaseActivity<ActivityListBinding>() {

    private val mAdapter by lazy { DeviceListAdapter() }

    private val mViewModel : DemoListViewModule by viewModels()

    override fun initView() {
        mBinding.rv.adapter = mAdapter
        mBinding.srl.setOnRefreshListener {
            mAdapter.refresh()
        }

        mAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.NotLoading->{
                    mBinding.srl.isRefreshing = false
                }
                is LoadState.Loading -> {
                    mBinding.srl.isRefreshing = true
                }
                is LoadState.Error ->{
                    mBinding.srl.isRefreshing = false
                    val state = it.refresh as LoadState.Error
                    toast(state.error.message)
                }
            }
        }
    }

    override fun initData() {
        mBinding.srl.isRefreshing = true
        lifecycleScope.launch {
            mViewModel.getDevices().collect { data->
                mAdapter.submitData(data)
            }
        }

    }

    override fun initObserver() {

    }
}