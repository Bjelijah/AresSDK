package com.cbj.sdk.libnet.http.helper

import com.cbj.sdk.libnet.http.provider.IRequestHandler
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author : zhouzhou
 * @date   : 2019-11-02 15:12
 * @desc   :网络拦截器
 */
class NetInterceptor(private val handler: IRequestHandler?) : Interceptor {

    /**
     * 网络请求拦截方法
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        handler?.let {
            request = handler.onBeforeRequest(request,chain)
        }


        val response = chain.proceed(request)
        return handler?.onAfterRequest(response, chain)?:response
    }

}