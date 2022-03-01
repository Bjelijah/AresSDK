package com.cbj.aresandroid.demo.api

import com.cbj.aresandroid.demo.api.bean.LoginReq
import com.cbj.aresandroid.demo.api.bean.LoginRes
import com.cbj.sdk.libnet.http.bean.ResultBean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
interface DemoService {
    //0101登录
    @POST("user/login")
    suspend fun login(@Body req: LoginReq): ResultBean<LoginRes>
}