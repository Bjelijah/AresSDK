package com.cbj.sdk.libnet.http.provider

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @author : zhouzhou
 * @date   : 2019-11-02 14:56
 * @desc   :网络请求拦截器
 */

interface IRequestHandler {
    /**
     * 发起请求拦截方法
     */
    fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request

    /**
     * 请求结果拦截方法
     */
    @Throws(IOException::class)
    fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response
}