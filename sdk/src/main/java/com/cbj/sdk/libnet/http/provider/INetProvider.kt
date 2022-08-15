package com.cbj.sdk.libnet.http.provider

import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * @author : zhouzhou
 * @date   : 2019-11-02 14:55
 * @desc   : 网络请求拦截器
 */

interface INetProvider{
    /**
     * 添加网络请求拦截器
     */
    fun configInterceptors(): Array<Interceptor>?


    /**
     * 添加cookie持久化拦截器
     */
    fun configCookie(): CookieJar?

    /**
     * 添加https请求拦截器
     */
    fun configHttps(builder: OkHttpClient.Builder)

    /**
     * 添加拦截器
     */
    fun configHandler(): IRequestHandler

    /**
     * 链接超时时间
     */
    fun configConnectTimeoutSecs(): Long

    /**
     * 读取超时时间
     */
    fun configReadTimeoutSecs(): Long

    /**
     * 写入超时时间
     */
    fun configWriteTimeoutSecs(): Long

    /**
     * 是否开启log打印日志
     */
    fun configLogEnable(): Boolean
}