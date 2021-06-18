package com.cbj.sdk.libnet.http.helper

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor :Interceptor {

    companion object {
        var TOKEN : String ?=null
        var API_VERSION = "0.0.0"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Content-Type","application/json;charset=utf-8")
       // requestBuilder.header("Content-Encoding","gzip")
        if (!TextUtils.isEmpty(TOKEN)){
            requestBuilder.header("Authorization", "Bearer $TOKEN")
        }
        requestBuilder.header("Api-Version","$API_VERSION")
        return chain.proceed(requestBuilder.build())
    }
}