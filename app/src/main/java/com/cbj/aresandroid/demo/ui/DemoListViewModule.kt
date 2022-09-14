package com.cbj.aresandroid.demo.ui

import androidx.lifecycle.ViewModel
import com.cbj.aresandroid.demo.api.ApiService
import com.cbj.aresandroid.demo.api.bean.DeviceReq
import com.cbj.sdk.libbase.ext.toMap
import com.cbj.sdk.libui.bean.getPageData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author : CBJ
 * @date   : 2022/8/12 14:17
 * @desc   :
 */
@HiltViewModel
class DemoListViewModule @Inject constructor(
     private val apiService:ApiService
):ViewModel() {

    suspend fun getDevices() =
        getPageData(requestData = { page,size->
            apiService.getDeviceData(DeviceReq(
                page, size
            ).toMap()).data?.records!!
        })


}