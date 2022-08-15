package com.cbj.aresandroid.demo.api

import com.cbj.aresandroid.demo.api.bean.*
import com.cbj.sdk.libnet.http.bean.ListBean
import com.cbj.sdk.libnet.http.bean.ResultBean
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
interface ApiService {
    //0101登录
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("user/login")
    suspend fun login(@FieldMap req: Map<String,String>): ResultBean<LoginRes>

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("monitor/device/status")
    suspend fun getDeviceData(@FieldMap req: Map<String,String>):ResultBean<ListBean<DeviceRes>>



}