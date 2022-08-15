package com.cbj.sdk.libnet.http.provider

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.cbj.sdk.BuildConfig
import com.cbj.sdk.libbase.exception.ApiThrowable
import com.cbj.sdk.libbase.ext.appVersion
import com.cbj.sdk.libbase.ext.fromJson
import com.cbj.sdk.libbase.ext.preference
import com.cbj.sdk.libnet.http.bean.ResultBean
import com.cbj.sdk.libnet.http.helper.SSLConnection
import com.cbj.sdk.libui.ARouterPath
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.*
import retrofit2.HttpException
import java.nio.charset.Charset

/**
 * @author : CBJ
 * @date   : 2022/8/11 13:25
 * @desc   :
 */
open class BaseNetProvider(
    private val context: Context,
    private val sslClient:String?=null,
    private val sslKey:String?=null):INetProvider {
    override fun configInterceptors(): Array<Interceptor>? = null

    override fun configCookie(): CookieJar? =  PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

    override fun configHttps(builder: OkHttpClient.Builder) {
        SSLConnection.setClientKey(sslClient, sslKey)?.supportSSL(builder)
    }

    override fun configHandler(): IRequestHandler = object:IRequestHandler{
        override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request =
            request.newBuilder()
                .header("Content-Type","application/json;charset=utf-8")
//                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization", "Bearer ${preference.token}")
                .header("version", appVersion)
                .let {
                    getReferer()?.let { referer->
                        it.header("referer",referer)
                    }?:it
                }
                .build()



        override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response  =
            when(response.isSuccessful) {
                true -> {
                    response.body?.source()?.let {
                        it.request(Long.MAX_VALUE)
                        it.buffer
                    }?.let {
                        it.clone().readString(Charset.forName("UTF-8"))
                    }?.let {
                        it.fromJson<ResultBean<Any>>()
                    }?.let {
                        when(it.code) {
                            200->true
                            305->{ //客户端请求的API版本与服务端不兼容，客户端应升级到最新版
                                throw ApiThrowable(it.code,it.msg)
                                false
                            }
                            400->{ // 请求参数错误
                                throw ApiThrowable(it.code,it.msg)
                                false
                            }
                            402,403->{ // token已失效,未携带token
                                throw ApiThrowable(it.code,it.msg)
                                false
                            }
                            500->{ //服务器错误
                                throw ApiThrowable(it.code,it.msg)
                                false
                            }
                            501->{ // 未知错误
                                throw ApiThrowable(it.code,it.msg)
                                false
                            }
                            else->{
                                false
                            }
                        }
                    }.let {
                        response
                    }
                }
                false->{
                    response
                }
            }

    }


    override fun configConnectTimeoutSecs(): Long = 30

    override fun configReadTimeoutSecs(): Long = 30

    override fun configWriteTimeoutSecs(): Long = 30

    override fun configLogEnable(): Boolean = BuildConfig.DEBUG

    open fun getReferer():String?=null

}