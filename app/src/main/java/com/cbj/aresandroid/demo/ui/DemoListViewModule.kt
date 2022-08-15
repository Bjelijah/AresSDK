package com.cbj.aresandroid.demo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.cbj.aresandroid.demo.api.ApiService
import com.cbj.aresandroid.demo.api.bean.DeviceReq
import com.cbj.aresandroid.demo.api.bean.NewsBean
import com.cbj.sdk.libbase.ext.toMap
import com.cbj.sdk.libui.asLiveData
import com.cbj.sdk.libui.bean.getPageData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
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

    private val _newsData = MutableLiveData<PagingData<NewsBean.StoriesBean>>()
    val newsData = _newsData.asLiveData()

    private val mDate = Calendar.getInstance().apply {
        add(Calendar.DATE, 1)
    }

    private val initialKey = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
        .format(mDate.time)
        .toLong()

//    suspend fun getNews() =
//        getPageData(requestData = { page,size->
//            apiService.getNewsAsync(initialKey).stories
//        })

    suspend fun getDevices() =
        getPageData(requestData = { page,size->
            apiService.getDeviceData(DeviceReq(
                page, size
            ).toMap()).data?.records!!
        })


}